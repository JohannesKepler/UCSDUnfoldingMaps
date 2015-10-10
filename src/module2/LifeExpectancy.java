package module2;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.awt.List;
import java.util.HashMap;


public class LifeExpectancy extends PApplet
{
	UnfoldingMap map;
	HashMap<String, Float> lifeExpByCountry;
	List<Feature> countries;
	List<Markers> countryMarkers;
	
	public void setup() {
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		
		lifeExpByCountry = loadLifeExpectancyFromCSV("data/LifeExpectancyWorldBank.csv");
		
		countries = GeoJSONReader.loadData(this, "data/countries.geo.json");
		countryMarkers = MapUtils.createSimpleMarker(countries);
		
		map.addMarker(countryMarkers);
		shadeCountries();
	}
	
	private void shadeCountries() {
		// TODO Auto-generated method stub
		for (Marker marker : countryMarkers) {
			String countryId = marker.getId();
			
			if (lifeExpMap.containsKey(countryId)) {
				float lifeExp = lifeExpMap.get(countryId);
				int colorLevel = (int) map(lifeExp, 40 90, 10, 255);
				marker.setColor(color(255-colorLevel), 100, colorLevel);
			}
			else {
				
			}
		}
	}

	private HashMap<String, Float> loadLifeExpectancyFromCSV(String fileName) {
		// TODO Auto-generated method stub
		HashMap<String, Float> lifeExpMap = new HashMap<String, Float>();
		String[] rows = loadStrings(fileName);
		
		for (String row : rows) {
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
				float value = Float.parseFloat(columns[5]);
				lifeExpMap.put(columns[4], value);
			}
		}
		
		return lifeExpMap;
	}

	public void draw() {
		map.draw();
	}
}