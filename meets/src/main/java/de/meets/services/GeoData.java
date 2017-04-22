package de.meets.services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class GeoData {
	
	public GeocodingResult getCoordinatesFromAdress(String adress) throws Exception{
		
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
		
		return results[0];
		
	}
	
//	public static void main(String[] args) {
//		GeoData geoData = new GeoData();
//		try {
//			geoData.getCoordinatesFromAdress("Buchenstraße 8, 82293 Mittelstetten");
//			geoData.getCoordinatesFromAdress("Mammendorf");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
