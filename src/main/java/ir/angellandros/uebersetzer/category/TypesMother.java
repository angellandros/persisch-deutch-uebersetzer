package ir.angellandros.uebersetzer.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ir.angellandros.kollektion.*;

public class TypesMother
{

	private Map<String, AtomicType> types;
	private Set<OrderedPair<String, String>> relations;

	public TypesMother()
	{
		types = new HashMap<String, AtomicType>();
		relations = new HashSet<OrderedPair<String, String>>();
	}

	public void addType(AtomicType type)
	{
		types.put(type.toString(), type);
	}

	public void addType(String type)
	{
		types.put(type, new AtomicType(type));
	}
	
	public boolean addRelation(String type1, String type2)
	{
		if(!types.keySet().contains(type1) || !types.keySet().contains(type2))
		{
			return false;
		}
		
		relations.add(new OrderedPair<String, String>(type1, type2));
		return true;
	}
	
	public Set<AtomicType> getChilds(String type)
	{
		Set<AtomicType> childs = new HashSet<AtomicType>();
		childs.add(types.get(type));
		for(OrderedPair<String, String> pair : relations)
		{
			if(pair._1 == type)
			{
				childs.add(types.get(pair._2));
				childs.addAll(getChilds(pair._2));
			}
		}
		return childs;
	}
	
	public Set<AtomicType> getChilds(AtomicType type)
	{
		return getChilds(type.type);
	}
	
	public Set<AtomicType> getParents(String type)
	{
		Set<AtomicType> parents = new HashSet<AtomicType>();
		parents.add(types.get(type));
		for(OrderedPair<String, String> pair : relations)
		{
			if(pair._2 == type)
			{
				parents.add(types.get(pair._1));
				parents.addAll(getParents(pair._1));
			}
		}
		return parents;
	}
	
	public Set<AtomicType> getParents(AtomicType type)
	{
		return getParents(type.type);
	}
	
	public Set<AtomicType> getChilds(ExponentialType type)
	{
		if(type.degree % 2 == 0)
		{
			return getChilds(type.type);
		}
		else
		{
			return getParents(type.type);
		}
	}
	
	public Set<AtomicType> getCommonChilds(ExponentialType type1, ExponentialType type2)
	{
		Set<AtomicType> childs1 = getChilds(type1);
		Set<AtomicType> childs2 = getChilds(type2);
		Set<AtomicType> commonChilds = new HashSet<AtomicType>();
		
		for(AtomicType child : childs1)
		{
			if(childs2.contains(child))
			{
				commonChilds.add(child);
			}
		}
		
		return commonChilds;
	}
	
	public Set<ExponentialType> reduceLocally(ExponentialType type1, ExponentialType type2) 
			throws NotReducibleException
	{
		Set<ExponentialType> reduces = new HashSet<ExponentialType>();
		
		if(type1.isOne())
		{
			reduces.add(type2);
			return reduces;
		}
		
		if(type2.isOne())
		{
			reduces.add(type1);
			return reduces;
		}
		
		Set<AtomicType> commonChilds = getCommonChilds(type1, type2);
		
		if(commonChilds.isEmpty() || (type1.degree == 0 && type2.degree == 0))
		{
			throw new NotReducibleException();
		}
		
		ExponentialType one = new ExponentialType("1");
		
		for(AtomicType child : commonChilds)
		{
			if(type1.degree == 0)
			{
				if(type2.degree == 1)
				{
					reduces.add(one);
				}
				else if(type2.degree > 0)
				{
					reduces.add(new ExponentialType(child, type2.degree-1));
				}
				else
				{
					throw new NotReducibleException();
				}
			}			
			else if(type2.degree == 0)
			{
				if(type1.degree == -1)
				{
					reduces.add(one);
				}
				else if(type1.degree < 0)
				{
					reduces.add(new ExponentialType(child, type1.degree+1));
				}
				else
				{
					throw new NotReducibleException();
				}
			}
			else
			{
				reduces.add(new ExponentialType(child, type1.degree+type2.degree));
			}
		}
		
		return reduces;
	}
	
	public boolean reducesToSentence(CompoundType sequence)
	{
		if(sequence.isSentence())
		{
			return true;
		}
		
		// this will prevent empty list to pass
		if(sequence.isOne())
		{
			return false;
		}
		
		sequence.list.restart();
		LinkedList<ExponentialType> list = new LinkedList<ExponentialType>(sequence.list);
		
		list.next();
		
		while(list.hasNext() && list.hasCurrent())
		{
			try
			{
				Set<ExponentialType> reduces = reduceLocally(list.current(), list.next());
				list.remove();
				for(ExponentialType reduce : reduces)
				{
					list.replace(reduce);
					if(reducesToSentence(new CompoundType(list)))
					{
						return true;
					}
				}
				
				return false;
			}
			catch(NotReducibleException e)
			{
				
			}
		}
		
		return false;
	}
	
	public static void main(String[] args)
	{
		TypesMother mother = new TypesMother();
		mother.addType("n");
		mother.addType("pi");
		mother.addType("pi1");
		mother.addType("pi2");
		mother.addType("s");
		mother.addType("o");
		mother.addRelation("n", "pi");
		mother.addRelation("pi", "pi1");
		mother.addRelation("pi", "pi2");
		ArrayList<ExponentialType> sequence = new ArrayList<ExponentialType>();
		AtomicType pi = new AtomicType("pi");
		AtomicType s = new AtomicType("s");
		sequence.add(new ExponentialType(s, 0));
		sequence.add(new ExponentialType(pi, 0));
		sequence.add(new ExponentialType(pi, -2));
		sequence.add(new ExponentialType(pi, 3));
		CompoundType type = new CompoundType(sequence);
		System.out.println(mother.reducesToSentence(type));
	}

}
