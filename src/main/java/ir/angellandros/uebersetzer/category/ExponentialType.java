package ir.angellandros.uebersetzer.category;

public class ExponentialType implements Type
{
	public final int degree;
	public final AtomicType type;
	
	public ExponentialType(AtomicType type, int degree)
	{
		this.type = type;
		this.degree = degree;
	}
	
	public ExponentialType(String type, int degree)
	{
		this(new AtomicType(type), degree);
	}
	
	public ExponentialType(AtomicType type)
	{
		this(type, 0);
	}
	
	public ExponentialType(String type)
	{
		this(type, 0);
	}
	
	@Override
	public boolean isSentence()
	{
		return type.isSentence();
	}

	@Override
	public boolean isOne()
	{
		return type.isOne();
	}
	
	/**
	 * convert the atomic category into its string representation
	 */
	public String toString()
	{
		if(degree == 0)
			return type.toString();
		
		String toReturn = type.toString() + "^";
		
		String toAdd = (degree < 0? "l": "r");
		for (int i = 0; i < Math.abs(degree); i++)
		{
			toReturn += toAdd;
		}
		
		return toReturn;
	}

}
