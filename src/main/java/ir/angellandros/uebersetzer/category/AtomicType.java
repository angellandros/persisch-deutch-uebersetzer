package ir.angellandros.uebersetzer.category;

public class AtomicType implements Type
{

	public final String type;
	
	public AtomicType(String type)
	{
		this.type = type;
	}

	@Override
	public boolean isSentence()
	{
		return this.type == "s";
	}

	@Override
	public boolean isOne()
	{
		return this.type == "1";
	}
	
	public String toString()
	{
		return type;
	}

}
