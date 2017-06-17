package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import de.meets.services.GeoData;
import de.meets.services.MaxValueValidator;


public class MaxValueValidatorTest {
	MaxValueValidator validator = new MaxValueValidator("error");
	
	@Test
	public void testValue1() {
		boolean test1 = validator.isValidValue("20");
		assertEquals(test1, true);
	}	
	
	@Test
	public void testValue2() {
		boolean test2 = validator.isValidValue("250000");
		assertEquals(test2, true);
	}
}
