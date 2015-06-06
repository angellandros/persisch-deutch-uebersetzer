package ir.angellandros.uebersetzer.category;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CompoundCategoryTest
{

	@Test
	public void testCompoundCategory()
	{
		CompoundCategory com = new CompoundCategory();

		assertEquals("Incorrect creation of empty compound category", "1",
				com.toString());
	}

	@Test
	public void testCompoundCategoryCollectionOfAtomicCategory()
	{
		List<AtomicCategory> list = new ArrayList<AtomicCategory>();
		AtomicCategory atom1 = new AtomicCategory("n", -1);
		AtomicCategory atom2 = new AtomicCategory("n", 1);
		list.add(atom1);
		list.add(atom2);
		CompoundCategory cat1 = new CompoundCategory(list);

		assertEquals(
				"Incorrect cunstruction of compound category from collection of atomic categories",
				cat1.toString(), atom1.toString() + "·" + atom2.toString());
	}

	@Test
	public void testCompoundCategoryAtomicCategory()
	{
		AtomicCategory atom = new AtomicCategory("o");
		CompoundCategory cat = new CompoundCategory(atom);

		assertEquals(
				"Incorrect construction of compound category from atomic category",
				atom.toString(), cat.toString());
	}

	@Test
	public void testToString()
	{
		List<AtomicCategory> list = new ArrayList<AtomicCategory>();
		AtomicCategory atom1 = new AtomicCategory("n", -1);
		AtomicCategory atom2 = new AtomicCategory("n", 1);
		list.add(atom1);
		list.add(atom2);
		CompoundCategory cat1 = new CompoundCategory(list);

		assertEquals("Incorrect representation", cat1.toString(),
				atom1.toString() + "·" + atom2.toString());
	}

	@Test
	public void testReduce()
	{
		// test 1: axiom 1. a·a^r -> 1
		List<AtomicCategory> list1 = new ArrayList<AtomicCategory>();
		list1.add(new AtomicCategory("n"));
		list1.add(new AtomicCategory("n", 1));
		CompoundCategory cat1 = new CompoundCategory(list1);

		assertEquals("Category " + cat1 + " must reduce to one", true,
				cat1.isOne());

		// test 2: axiom 2. a^l·a -> 1
		List<AtomicCategory> list2 = new ArrayList<AtomicCategory>();
		list2.add(new AtomicCategory("n", -1));
		list2.add(new AtomicCategory("n"));
		CompoundCategory cat2 = new CompoundCategory(list2);

		assertEquals("Category " + cat2 + " must reduce to one", true,
				cat1.isOne());
	}

	@Test
	public void testIsOne()
	{
		// build a list reducible to one
		List<AtomicCategory> list1 = new ArrayList<AtomicCategory>();
		list1.add(new AtomicCategory("n"));
		list1.add(new AtomicCategory("n", 1));
		CompoundCategory cat1 = new CompoundCategory(list1);

		assertEquals("Category " + cat1 + " must reduce to one", true,
				cat1.isOne());

		// build a list not reducible to one
		List<AtomicCategory> list2 = new ArrayList<AtomicCategory>();
		list2.add(new AtomicCategory("n", 1));
		list2.add(new AtomicCategory("n"));
		CompoundCategory cat2 = new CompoundCategory(list2);

		assertEquals("Category " + cat2 + " must not reduce to one", false,
				cat2.isOne());
	}

}
