package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import de.meets.services.TimeValidator;


public class TimeValidatorTest {

	TimeValidator validator = new TimeValidator("error");
	
	@Test
	public void test1() {
		assertEquals(validator.isValid("23:59"), true);
	}
	
	@Test
	public void test2() {
		assertEquals(validator.isValid("24:59"), false);
	}

}
