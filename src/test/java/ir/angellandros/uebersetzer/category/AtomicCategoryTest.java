package ir.angellandros.uebersetzer.category;

public class AtomicCategoryTest
{

	public static void main(String[] args)
	{
		Category cat1 = new AtomicCategory("π1");
		Category cat2 = new AtomicCategory("o", 2);
		Category cat3 = Categories.parseAtomicCategory("π1^lll");
		
		System.out.println(cat1);
		System.out.println(cat2);
		System.out.println(cat3);
	}

}
