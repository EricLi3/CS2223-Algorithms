package ekli.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.DepthFirstPaths;

public class Connected {

	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();
		// make the graphs
		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		// Define the source airport as KBOS for delta
		int source = -1;
		// get the int representation
		for (int v : delta.positions.keys()) { // loop thorough iterable of ints of the vertices
			String airportCode = delta.labels.get(v);
			if (airportCode.equals("KBOS")) { // check if labels value is equal to "KBOS"
				source = v; // new value
				break;
			}
		}
		// get the vertex of the KBOS ICAO code for southwest
		int sourcesw = -1;
		for (int v : southwest.positions.keys()) { // loop thorough iterable of ints of the vertices
			String airportCode = southwest.labels.get(v);
			if (airportCode.equals("KBOS")) { // check if labels value is equal to "KBOS"
				sourcesw = v; // new value
				break;
			}
		}
		
		// use DepthFirstSearch on delta and southwest
		DepthFirstPaths dfsDelta = new DepthFirstPaths(delta.graph, source);
		DepthFirstPaths dfsSouthwest = new DepthFirstPaths(southwest.graph, sourcesw);
		
		// create an array of the airports that are unreachable starting at BOS for delta
		String deltaUnreachable[] = new String[50];
		int pos1 = 0;
		for (int deltaAirport : delta.labels.keys()) {
			if (!dfsDelta.hasPathTo(deltaAirport)) {
				deltaUnreachable[pos1++] = delta.labels.get(deltaAirport);
			}
		}
		
		// create an array of the airports that are unreachable starting at BOS for delta
		String swUnreachable[] = new String[50];
		int pos2 = 0;
		for (int southwestAirport : southwest.labels.keys()) {
			if (!dfsSouthwest.hasPathTo(southwestAirport)) {
				swUnreachable[pos2++] = southwest.labels.get(southwestAirport);
			}
		}
		// check if there are any unreachable airports starting from Boston for delta
		if (deltaUnreachable.length > 0) {
			System.out.println("The name of the airline is Delta");
			System.out.println("The airports that cannot be reached from KBOS using Delta are:");
			for (String airport : deltaUnreachable) {
				if (airport != null) {
					System.out.println(airport);
				}
			}
			return;	
		}
		// check if there are any unreachable airports starting from Boston for delta
		if (swUnreachable.length > 0) {
			System.out.println("The name of the airline is Southwest");
			System.out.println("The airports that cannot be reached from KBOS using Southwest are:");
			for (String airport : swUnreachable) {
				if (airport != null) {
					System.out.println(airport);
				}
			}
			return;
		}

	}
}




