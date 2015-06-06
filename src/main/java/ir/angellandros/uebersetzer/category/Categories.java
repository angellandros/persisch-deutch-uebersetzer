package ir.angellandros.uebersetzer.category;

public class Categories
{
	public static AtomicCategory parseAtomicCategory(String string)
	{
		if(string.contains("^"))
		{
			String[] splited = string.split("\\^");
			String type = splited[0];
			String tail = splited[1];
			int degree = tail.length() * (tail.charAt(0) == 'r'? 1: -1);
			return new AtomicCategory(type, degree);
		}
		else
		{
			return new AtomicCategory(string);
		}
	}
}
