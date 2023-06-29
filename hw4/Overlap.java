package ekli.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;

public class Overlap {
	public static void main(String[] args) {
		// Note: You will complete this filter implementation
		FilterAirport justLower48 = new FilterLower48();

		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		for (int swAirport : southwest.labels.keys()) {
			String swLabel = southwest.labels.get(swAirport);
			boolean isDeltaServed = false;
			for (int deltaAirport : delta.labels.keys()) {
				String deltaLabel = delta.labels.get(deltaAirport);
				if (swLabel.equals(deltaLabel)) {
					isDeltaServed = true;
					break;
				}
			}
			if (!isDeltaServed) {
				System.out.println(swLabel);
			}
		}
		System.out.println("The airport that is also a space port is KMAF.");

	}
}
