package ir.angellandros.uebersetzer.category;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExponentialTypeTest
{

	@Test
	public void test()
	{
		ExponentialType cat1 = new ExponentialType("π1");
		ExponentialType cat2 = new ExponentialType("o", 2);
		ExponentialType cat3 = Types.parseAtomicCategory("π1^lll");
		
		assertEquals(cat1.toString(), "π1");
		assertEquals(cat2.toString(), "o^rr");
		assertEquals(cat3.toString(), "π1^lll");
		assertEquals(cat3.degree, -3);
		assertEquals(cat3.type.type, "π1");
	}

}
