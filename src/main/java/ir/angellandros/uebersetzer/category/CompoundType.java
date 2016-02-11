package ir.angellandros.uebersetzer.category;

import java.util.Collection;
import java.util.Iterator;

import ir.angellandros.kollektion.*;

public class CompoundType implements Type
{
	public final LinkedList<ExponentialType> list;
	
	public CompoundType()
	{
		list = new LinkedList<ExponentialType>();
	}
	
	public CompoundType(LinkedList<ExponentialType> list)
	{
		this.list = list;
	}
	
	public CompoundType(Collection<ExponentialType> c)
	{
		list = new LinkedList<ExponentialType>(c);
	}
	
	public CompoundType(Iterator<ExponentialType> c)
	{
		list = new LinkedList<ExponentialType>(c);
	}
	
	public CompoundType(ExponentialType first)
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
				toReturn += "*";
			}
			toReturn += list.next();
		}
		
		return toReturn;
	}
	
	@Override
	public boolean isSentence()
	{
		list.restart();
		if(!list.hasNext())
		{
			return false;
		}
		ExponentialType first = list.next();
		return first.isSentence() && !list.hasNext();
	}
	
	@Override
	public boolean isOne()
	{
		return toString() == "1";
	}
}
