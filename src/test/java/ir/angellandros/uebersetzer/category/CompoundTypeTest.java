package ir.angellandros.uebersetzer.category;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CompoundTypeTest
{

	TypesMother mother;

	@Before
	public void setUp() throws Exception
	{
		mother = new TypesMother();
		mother.addType("n");
		mother.addType("o");
		mother.addType("π");
		mother.addType("s");
		mother.addRelation("n", "π");
	}

	@Test
	public void testCompoundType()
	{
		CompoundType com = new CompoundType();

		assertEquals("Incorrect creation of empty compound category", "1", com.toString());
	}

	@Test
	public void testCompoundCategoryCollectionOfExponentialTypes()
	{
		List<ExponentialType> list = new ArrayList<ExponentialType>();
		ExponentialType atom1 = new ExponentialType("n", -1);
		ExponentialType atom2 = new ExponentialType("n", 1);
		list.add(atom1);
		list.add(atom2);
		CompoundType cat1 = new CompoundType(list);

		assertEquals("Incorrect cunstruction of compound type from collection of exponential types", cat1.toString(),
				atom1.toString() + "*" + atom2.toString());
	}

	@Test
	public void testCompoundTypeExponentialType()
	{
		ExponentialType atom = new ExponentialType("o");
		CompoundType cat = new CompoundType(atom);

		assertEquals("Incorrect construction of compound type from exponential type", atom.toString(), cat.toString());
	}

	@Test
	public void testToString()
	{
		List<ExponentialType> list = new ArrayList<ExponentialType>();
		ExponentialType atom1 = new ExponentialType("n", -1);
		ExponentialType atom2 = new ExponentialType("n", 1);
		list.add(atom1);
		list.add(atom2);
		CompoundType cat1 = new CompoundType(list);

		assertEquals("Incorrect representation", cat1.toString(), atom1.toString() + "*" + atom2.toString());
	}

	@Test
	public void testReduce()
	{
		// test 1: axiom 1. s*a*a^r -> s
		List<ExponentialType> list1 = new ArrayList<ExponentialType>();
		list1.add(new ExponentialType("s"));
		list1.add(new ExponentialType("n"));
		list1.add(new ExponentialType("n", 1));
		CompoundType cat1 = new CompoundType(list1);

		assertEquals("Category " + cat1 + " must reduce to sentence", true, mother.reducesToSentence(cat1));

		// test 2: axiom 2. s*a^l*a -> s
		List<ExponentialType> list2 = new ArrayList<ExponentialType>();
		list2.add(new ExponentialType("s"));
		list2.add(new ExponentialType("n", -1));
		list2.add(new ExponentialType("n"));
		CompoundType cat2 = new CompoundType(list2);

		assertEquals("Category " + cat2 + " must reduce to sentence", true, mother.reducesToSentence(cat2));
	}

	@Test
	public void testReduceWithRelation()
	{
		// test 1: s*n*π^r -> s
		List<ExponentialType> list1 = new ArrayList<ExponentialType>();
		list1.add(new ExponentialType("s"));
		list1.add(new ExponentialType("n"));
		list1.add(new ExponentialType("π", 1));
		CompoundType cat1 = new CompoundType(list1);

		assertEquals("Category " + cat1 + " must reduce to sentence", true, mother.reducesToSentence(cat1));

		// test 2: s*n^l*π -/> s
		List<ExponentialType> list2 = new ArrayList<ExponentialType>();
		list2.add(new ExponentialType("s"));
		list2.add(new ExponentialType("n", -1));
		list2.add(new ExponentialType("π"));
		CompoundType cat2 = new CompoundType(list2);

		assertEquals("Category " + cat2 + " must reduce to sentence", false, mother.reducesToSentence(cat2));
	}

}
