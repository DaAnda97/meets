package tests;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import de.meets.services.GeoData;
import de.meets.services.SHAEncription;

/**
 * Unit test 
 */
public class SHAEncriptionTest {
	
	
	@Test
	public void test() {
		SHAEncription encr = new SHAEncription();
		String hash = "";
		try {
			hash = encr.SHAHash("verysecure123");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		assertEquals("f4da94c583e8ad6a3e6206d170a264cf80a24078a16da17c7cb2bbc9bb8db582", hash);
	}
	

}
