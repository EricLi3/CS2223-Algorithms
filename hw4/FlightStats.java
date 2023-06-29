package ekli.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.GPS;
import algs.hw4.map.Information;
import ekli.hw4.Histogram;

public class FlightStats {

	public static void main(String[] args) {

		FilterAirport justLower48 = new FilterLower48();
		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		Histogram Hdelta = new Histogram();
		Histogram Hsouthwest = new Histogram();

		// Delta
		// set initial values to be overwritten
		int deltaShortestDist = Integer.MAX_VALUE;
		String deltaShortestICAO = "";
		int deltaLongestDist = Integer.MIN_VALUE;
		String deltaLongestICAO = "";
		double deltaTotalDist = 0;
		int deltaNumFlights = 0;

		for (int airport : delta.labels.keys()) { // iterate thorough airports vertices
			GPS sourcePos = delta.positions.get(airport); // get the source point

			for (int neighbor : delta.graph.adj(airport)) { // iterate over a collection of adjacent neighbors

				if (neighbor > airport) { // since histogram was exactly double, only count one direction one distance once

					GPS destinationPos = delta.positions.get(neighbor);
					int dist = (int) sourcePos.distance(destinationPos); // compute distance between two adjacent
																			// vertices
					// do comparisons for each direct flight from the sourcePos in terms of distance
					if (dist < deltaShortestDist) {
						deltaShortestDist = dist; // update
						deltaShortestICAO = delta.labels.get(airport) + " to " + delta.labels.get(neighbor);
					}
					if (dist > deltaLongestDist) {
						deltaLongestDist = dist; // update
						deltaLongestICAO = delta.labels.get(airport) + " to " + delta.labels.get(neighbor);
					}
					// do accumulators for the average flight distance
					deltaTotalDist += dist;
					deltaNumFlights++;

					Hdelta.record(dist);
				}
			}
		}

		double deltaAvgDist = deltaTotalDist / deltaNumFlights; // calculate average distance for delta

		System.out.println("Shortest flight for Delta: " + deltaShortestICAO + " for " + deltaShortestDist + " miles");
		System.out.println("Longest flight for Delta: " + deltaLongestICAO + " for " + deltaLongestDist + " miles");
		System.out.println("Average flight distance for Delta: " + deltaAvgDist + " miles");
		System.out.println("");

		// Southwest
		int swShortestDist = Integer.MAX_VALUE;
		String swShortestICAO = "";
		int swLongestDist = Integer.MIN_VALUE;
		String swLongestICAO = "";
		double swTotalDist = 0;
		int swNumFlights = 0;
		for (int airport : southwest.labels.keys()) { // get the source point
			GPS sourcePos = southwest.positions.get(airport);

			for (int neighbor : southwest.graph.adj(airport)) { // iterate over a collection of adjacent neighbors

				if (neighbor > airport) { // since histogram was double, only count one direction one distance once
					GPS destinationPt = southwest.positions.get(neighbor);
					int dist = (int) sourcePos.distance(destinationPt); // compute distance between two adjacent
																		// vertices
					// do comparisons for each direct flight from the sourcePos in terms of distance
					if (dist < swShortestDist) {
						swShortestDist = dist; // update
						swShortestICAO = southwest.labels.get(airport) + " to " + southwest.labels.get(neighbor);
					}
					if (dist > swLongestDist) {
						swLongestDist = dist; // update
						swLongestICAO = southwest.labels.get(airport) + " to " + southwest.labels.get(neighbor);
					}
					// accumulators to compute the average flight distance.
					swTotalDist += dist;
					swNumFlights++;

					Hsouthwest.record(dist);
				}
			}
		}
		double swAverageDist = swTotalDist / swNumFlights; // calculate average distance for delta

		System.out.println("Shortest flight for Southwest: " + swShortestICAO + " for " + swShortestDist + " miles");
		System.out.println("Longest flight for Southwest: " + swLongestICAO + " for " + swLongestDist + " miles");
		System.out.println("Average flight distance for Southwest: " + swAverageDist + " miles");
		System.out.println("");
		System.out.println("Delta Airlines");
		Hdelta.report(500);
		System.out.println("");
		System.out.println("Southwest Airlines");
		Hsouthwest.report(500);

	}

}
