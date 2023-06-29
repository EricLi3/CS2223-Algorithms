package ekli.hw4;

import algs.hw4.map.FilterAirport;
import algs.hw4.map.FlightMap;
import algs.hw4.map.GPS;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.AdjMatrixEdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.FloydWarshall;
import edu.princeton.cs.algs4.StdOut;

public class LongestOfShortest {

	public static void main(String[] args) {
		FilterAirport justLower48 = new FilterLower48();
		Information delta = FlightMap.undirectedGraphFromResources("delta.json", justLower48);
		Information southwest = FlightMap.undirectedGraphFromResources("southwest.json", justLower48);

		Information test = FlightMap.undirectedGraphFromResources("test.json", justLower48);

		// make empty weighted graphs
		AdjMatrixEdgeWeightedDigraph deltaGraph = new AdjMatrixEdgeWeightedDigraph(delta.labels.size());
		AdjMatrixEdgeWeightedDigraph southwestGraph = new AdjMatrixEdgeWeightedDigraph(southwest.labels.size());

		AdjMatrixEdgeWeightedDigraph testGraph = new AdjMatrixEdgeWeightedDigraph(test.labels.size());

		// delta
		for (int airport : delta.labels.keys()) { // iterate thorough airports vertices
			GPS source = delta.positions.get(airport); // get the source point
			for (int neighbor : delta.graph.adj(airport)) { // iterate over a collection of adjacent neighbors
				GPS destPos = delta.positions.get(neighbor); // get the neighboring point
				double distance = source.distance(destPos); // compute the distance between two pts

				// add two edges for the two points going in opposite directions to be
				// undirected
				DirectedEdge edgev1 = new DirectedEdge(airport, neighbor, distance);
				deltaGraph.addEdge(edgev1);
				DirectedEdge edgev2 = new DirectedEdge(neighbor, airport, distance);
				deltaGraph.addEdge(edgev2);
			}
		}

		// southwest
		for (int airport : southwest.labels.keys()) { // iterate thorough airports vertices
			GPS source = southwest.positions.get(airport); // get the source point
			for (int neighbor : southwest.graph.adj(airport)) { // iterate over a collection of adjacent neighbors
				GPS destPos = southwest.positions.get(neighbor); // get the neighboring point
				double distance = source.distance(destPos); // compute the distance between two pts

				// add two edges for the two points going in opposite directions to be
				// undirected
				DirectedEdge edgev1 = new DirectedEdge(airport, neighbor, distance);
				southwestGraph.addEdge(edgev1);
				DirectedEdge edgev2 = new DirectedEdge(neighbor, airport, distance);
				southwestGraph.addEdge(edgev2);
			}
		}

		// test
		for (int airport : test.labels.keys()) { // iterate thorough airports vertices
			GPS source = test.positions.get(airport); // get the source point
			for (int neighbor : test.graph.adj(airport)) { // iterate over a collection of adjacent neighbors
				GPS destPos = test.positions.get(neighbor); // get the neighboring point
				double distance = source.distance(destPos); // compute the distance between two pts

				// add two edges for the two points going in opposite directions to be
				// undirected
				DirectedEdge edgev1 = new DirectedEdge(airport, neighbor, distance);
				testGraph.addEdge(edgev1);
				DirectedEdge edgev2 = new DirectedEdge(neighbor, airport, distance);
				testGraph.addEdge(edgev2);
			}
		}

		///////////////////////////////////////// Algorithm for Delta /////////////////////////////////////////////
		
		FloydWarshall algodelta = new FloydWarshall(deltaGraph); 

		double maxShortestDistance = 0;
		int airport1 = -1;
		int airport2 = -1;

		// iterate over all pairs of airports and find the longest of the shortest paths
		for (int i = 0; i < deltaGraph.V(); i++) {
			for (int j = i + 1; j < deltaGraph.V(); j++) {
				double shortestDistance = algodelta.dist(i, j);
				if (shortestDistance != Double.POSITIVE_INFINITY && shortestDistance > maxShortestDistance) {
					maxShortestDistance = shortestDistance;
					airport1 = i;
					airport2 = j;
				}
			}
		}
		GPS pos1 = delta.positions.get(airport1);
		GPS pos2 = delta.positions.get(airport2);

		double actdistance = pos1.distance(pos2);

		System.out.println("Delta : Total Flight Distance is " + maxShortestDistance + " but airports are only "
				+ actdistance + " miles apart");

		// get the path for the longest of shortest path
		Iterable<DirectedEdge> path = algodelta.path(airport1, airport2);

		// iterate over the edges in the path and print the trace
		for (DirectedEdge e : path) {
			int src = e.from();
			int dest = e.to();
			System.out.printf("%s -> %s for %.13f ", delta.labels.get(src), delta.labels.get(dest), e.weight());
			if (e == path.iterator().next()) {
				System.out.println("");
			} else {
				System.out.println();
			}
		}

		double totalEfficiency = 0;
		int numEfficiencies = 0;

		// iterate over all pairs of airports and compute the efficiency of each
		// shortest path
		for (int i = 0; i < deltaGraph.V(); i++) {
			for (int j = i + 1; j < deltaGraph.V(); j++) {
				double shortestDistance = algodelta.dist(i, j);
				if (shortestDistance != Double.POSITIVE_INFINITY) {
					GPS src = delta.positions.get(i);
					GPS dest = delta.positions.get(j);
					double actual = src.distance(dest);
					double efficency = shortestDistance / actual;
					totalEfficiency += efficency;
					numEfficiencies++;
				}
			}
		}
		double averageEfficiency = totalEfficiency / numEfficiencies;

		System.out.println("Average Efficiency: " + averageEfficiency);
		System.out.println("");

		///////////////////////////////////////// Algorithm for Southwest/////////////////////////////////////////////

		FloydWarshall algosouthwest = new FloydWarshall(southwestGraph);

		double maxShortestDistance1 = 0;
		int airport1sw = -1;
		int airport2sw = -1;

		// iterate over all pairs of airports and find the longest of the shortest paths
		for (int i = 0; i < southwestGraph.V(); i++) {
			for (int j = i + 1; j < southwestGraph.V(); j++) {
				double shortestDistance = algosouthwest.dist(i, j);
				if (shortestDistance != Double.POSITIVE_INFINITY && shortestDistance > maxShortestDistance1) {
					maxShortestDistance1 = shortestDistance;
					airport1sw = i;
					airport2sw = j;
				}
			}
		}

		GPS pos1sw = southwest.positions.get(airport1sw);
		GPS pos2sw = southwest.positions.get(airport2sw);

		double actdistancesw = pos1sw.distance(pos2sw);

		System.out.println("Southwest : Total Flight Distance is " + maxShortestDistance1 + " but airports are only "
				+ actdistancesw + " miles apart");

		// get the path for the longest of shortest path
		Iterable<DirectedEdge> pathsw = algosouthwest.path(airport1sw, airport2sw);

		// iterate over the edges in the path and print the trace
		for (DirectedEdge e : pathsw) {
			int src = e.from();
			int dest = e.to();
			System.out.printf("%s -> %s for %.13f ", southwest.labels.get(src), southwest.labels.get(dest), e.weight());
			if (e == pathsw.iterator().next()) {
				System.out.println("");
			} else {
				System.out.println();
			}
		}

		double totalEfficiencysw = 0;
		int numEfficienciessw = 0;

		// iterate over all pairs of airports and compute the efficiency of each
		// shortest path
		for (int i = 0; i < southwestGraph.V(); i++) {
			for (int j = i + 1; j < southwestGraph.V(); j++) {
				double shortestDistance = algosouthwest.dist(i, j);
				if (shortestDistance != Double.POSITIVE_INFINITY) {
					GPS src = southwest.positions.get(i);
					GPS dest = southwest.positions.get(j);
					double actual = src.distance(dest);
					double efficency = shortestDistance / actual;
					totalEfficiencysw += efficency;
					numEfficienciessw++;
				}
			}
		}

		double averageEfficiencysw = totalEfficiencysw / numEfficienciessw;

		System.out.println("Average Efficiency: " + averageEfficiencysw);

	}

}


