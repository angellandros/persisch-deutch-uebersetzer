package ir.angellandros.uebersetzer.category;

import java.util.ArrayList;
import java.util.List;

public class AtomicCategory implements Category
{
	private int degree;
	private String type;
	private List<String> parents;
	
	public AtomicCategory(String type, int degree)
	{
		this.type = type;
		this.degree = degree;
		this.parents = new ArrayList<String>();
		this.parents.add(type);
	}
	
	public AtomicCategory(String type)
	{
		this(type, 0);
	}
	
	public void addParentType(String type)
	{
		parents.add(type);
	}
	
	/**
	 * is the type one?
	 */
	@Override
	public boolean isOne()
	{
		return type == "1";
	}
	
	/**
	 * convert the atomic category into its string representation
	 */
	public String toString()
	{
		if(degree == 0)
			return type;
		
		String toReturn = type + "^";
		
		String toAdd = (degree < 0? "l": "r");
		for (int i = 0; i < Math.abs(degree); i++)
		{
			toReturn += toAdd;
		}
		
		return toReturn;
	}
	
	/**
	 * returns youngest parent of this atomic category that is also a parent of the other
	 * @param other the other atomic category
	 * @return the youngest common parent if any, null otherwise
	 */
	public String youngestCommonParent(AtomicCategory other)
	{
		for (int i = 0; i < parents.size(); i++)
		{
			if(other.parents.contains(this.parents.get(i)))
			{
				return this.parents.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * checks whether two atomic types reduce by multiplying, if so, returns the result of product, else returns null.
	 * @param other the other atomic type
	 * @return reduced type or null
	 */
	public AtomicCategory reduce(AtomicCategory other)
	{
		String ycp = this.youngestCommonParent(other);
		
		// if no common parents
		if(ycp == null)
		{
			return null;
		}
		
		// if both categories are adjacent
		if(this.degree != 0 && other.degree != 0)
		{
			// in this case you can sum up the degrees
			return new AtomicCategory(ycp, this.degree + other.degree);
		}
		// if this category is not adjacent, and the other is right adjacent 
		else if(this.degree == 0 && other.degree > 0)
		{
			if(other.degree == 1)
			{
				// complete reduction
				return new AtomicCategory("1");
			}
			else
			{
				// partial reduction
				return new AtomicCategory(ycp, other.degree - 1);
			}
		}
		// if this category is left adjacent, and the other is not adjacent
		else if(this.degree < 0 && other.degree == 0)
		{
			if(this.degree == -1)
			{
				// complete reduction
				return new AtomicCategory("1");
			}
			else
			{
				// partial reduction
				return new AtomicCategory(ycp, this.degree + 1);
			}
		}
		else
		{
			// no reduction
			return null;
		}
			
	}

}
