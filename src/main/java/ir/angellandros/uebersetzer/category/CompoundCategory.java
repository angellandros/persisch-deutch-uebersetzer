package ir.angellandros.uebersetzer.category;

import java.util.Collection;

import ir.angellandros.kollektion.*;

public class CompoundCategory implements Category
{
	private LinkedList<AtomicCategory> list;
	
	public CompoundCategory()
	{
		list = new LinkedList<AtomicCategory>();
	}
	
	public CompoundCategory(Collection<AtomicCategory> c)
	{
		list = new LinkedList<AtomicCategory>(c);
	}
	
	public CompoundCategory(AtomicCategory first)
	{
		this();
		list.insert(first);
	}
	
	public String toString()
	{
		list.restart();
		if(!list.hasNext())
		{
			return "1";
		}
		
		String toReturn = "";
		while(list.hasNext())
		{
			if(list.hasCurrent())
			{
				toReturn += "·";
			}
			toReturn += list.next();
		}
		
		return toReturn;
	}
	
	public void reduce()
	{
		boolean irreducible = false;
		
		while(!irreducible)
		{
			irreducible = true;
			list.restart();
			try
			{
				list.next();
			}
			catch(NullPointerException e)
			{
				// no next? so empty list
				break;
			}
			while(list.hasCurrent() /*&& list.hasPrevious()*/ && list.hasNext())
			{
				AtomicCategory reduced = list.current().reduce(list.next());
				if(reduced != null)
				{
					irreducible = false;
					list.remove();
					if(reduced.isOne())
					{
						list.remove();
					}
					else
					{
						list.replace(reduced);
					}
				}
			}
		}
	}
	
	@Override
	public boolean isOne()
	{
		reduce();
		return toString() == "1";
	}
}
