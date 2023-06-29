package ekli.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.GPS;

/**
 * Given a country (such as "United States") and a GPS location of an airport,
 * return TRUE if this airport should be included.
 * 
 * COPY this file into your USERID.hw4 package and modify it accordingly.
 */
public class FilterLower48 implements FilterAirport {

	@Override
	public boolean accept(String country, GPS gps) {
		if (country.equals("United States") && (-124.41 <= gps.longitude) && (gps.longitude <= -66.9499)
				&& (24.5231 <= gps.latitude) && (gps.latitude <= 46.29204)) {
			return true;
		}
		return false;
	}
}

// Longitude min -124.41 Longitude  max is -66.9499
// Latitude min 24.5231 Latitude max is 46.29204 