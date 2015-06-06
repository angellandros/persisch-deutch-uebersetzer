package ir.angellandros.uebersetzer.category;

import java.util.ArrayList;
import java.util.List;

public class AtomicCategoryReduceTest
{

	public static void main(String[] args)
	{

		List<AtomicCategory> list = new ArrayList<AtomicCategory>();
		list.add(new AtomicCategory("n"));
		AtomicCategory t = new AtomicCategory("n1", 2);
		t.addParentType("n");
		list.add(t);
		list.add(new AtomicCategory("n", -1));
		
		CompoundCategory cc = new CompoundCategory(list);
		System.out.println(cc);
		cc.reduce();
		System.out.println(cc);
	}

}
