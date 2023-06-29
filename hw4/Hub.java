package ekli.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.BreadthFirstPaths;

public class Hub {

	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();
		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		int index = 0;
		String deltaHub[] = new String[300];
		int deltaHubSize[] = new int[300];
		for (int airport : delta.labels.keys()) {
			BreadthFirstPaths bfsdelta = new BreadthFirstPaths(delta.graph, airport);
			int connections1 = 0;
			for (int otherAirport : delta.labels.keys()) {
				if (airport != otherAirport && bfsdelta.hasPathTo(otherAirport) && bfsdelta.distTo(otherAirport) == 1) {
					connections1++;
				}
			}
			if (connections1 > 75) {
				deltaHub[index] = delta.labels.get(airport);
				deltaHubSize[index] = connections1;
				index++;
			}
		}

		int index2 = 0;
		String swHub[] = new String[100];
		int swHubSize[] = new int[100];
		for (int airport : southwest.labels.keys()) {
			BreadthFirstPaths bfssw = new BreadthFirstPaths(southwest.graph, airport);
			int connections1 = 0;
			for (int otherAirport : southwest.labels.keys()) {
				if (airport != otherAirport && bfssw.hasPathTo(otherAirport) && bfssw.distTo(otherAirport) == 1) {
					connections1++;
				}
			}
			if (connections1 > 75) {
				swHub[index2] = southwest.labels.get(airport);
				swHubSize[index2] = connections1;
				index2++;
			}
		}
		System.out.println("Delta");
		for (int i = 0; i < deltaHub.length; i++) {
			if (deltaHub[i] != null) {
				System.out.println(deltaHub[i] + " (" + deltaHubSize[i] + " airports)");
			}
		}
		System.out.println("Southwest");
		for (int i = 0; i < swHub.length; i++) {
			if (swHub[i] != null) {
				System.out.println(swHub[i] + " (" + swHubSize[i] + " airports)");
			}
		}
	}
}
