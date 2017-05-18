package de.meets.services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import de.meets.assets.Location;

public class GeoData {
	
	public Location getCoordinatesFromAdress(String adress) throws Exception{
		
		String apiKey = "";
		
		ClassLoader classLoader = getClass().getClassLoader();
		File geoApiKey = new File(classLoader.getResource("geoApiKey.txt").getFile());
		try(BufferedReader br = new BufferedReader(new FileReader(geoApiKey))) {
			apiKey = br.readLine();
		} catch (Exception e) {
			System.out.println("FEHLER BEIM AUSLESEN DER PROPERTIES");
		}
		
		GeoApiContext geoContext = new GeoApiContext();
		geoContext.setApiKey(apiKey);
		
		GeocodingResult[] results = GeocodingApi.newRequest(geoContext).address(adress).await();
		
		for (GeocodingResult result : results) {
			double lat = result.geometry.location.lat;
			double lng = result.geometry.location.lng;
			System.out.println(result.formattedAddress + "(Latitude:" + lat + ", Longitude:" + lng + ")");
		}
		
		Location location = new Location(results[0].formattedAddress, results[0].geometry.location.lng, results[0].geometry.location.lat);
		
		
		return location;
		
	}
	
//	public static void main(String[] args) {
//		GeoData geoData = new GeoData();
//		try {
//			Location location = geoData.getCoordinatesFromAdress("Kuh");
//			System.out.println(location.getCity() + "( Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude() + ")");
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
}

