package org.apache.maven.meetsproject.meets;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import de.meets.assets.Location;
import de.meets.services.GeoData;

/**
 * Unit test for GeoDataTest.
 */
public class GeoDataTest {
	GeoData geoData = new GeoData();
	private static final double FUZZ_FACTOR = 0.0001; // Genauigkeit bei double-vergleich
	
	
	@Test
	public void testGetCoordinatesFromAdress() {
		Location karlsruheLocation = new Location();
		try {
			karlsruheLocation = geoData.getCoordinatesFromAdress("Karlsruhe Kaiserstraße 100");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Kaiserstraße 100, 76133 Karlsruhe, Germany", karlsruheLocation.getCity());
		assertEquals(49.0098625, karlsruheLocation.getLatitude(), FUZZ_FACTOR);
		assertEquals(8.4001106, karlsruheLocation.getLongitude(),  FUZZ_FACTOR);
	}
	

}
