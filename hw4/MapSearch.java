package ekli.hw4;

import algs.hw4.map.GPS;
import algs.hw4.map.HighwayMap;
import algs.hw4.map.Information;
import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import algs.days.day20.DepthFirstSearchNonRecursive;

/**
 * Copy this class into USERID.hw4 and make changes.
 */
public class MapSearch {

	/**
	 * This helper method returns the western-most vertex id in the Information,
	 * given its latitude and longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int westernMostVertex(Information info) {
		int westmostvertex = 0;
		for (int i = 1; i < info.positions.size(); i++) {
			double pos = info.positions.get(i).longitude;
			if (pos <= info.positions.get(westmostvertex).longitude) {
				westmostvertex = i;
			}
		}
		return westmostvertex;
	}

	/**
	 * This helper method returns the eastern-most vertex id in the Information,
	 * given its latitude and longitude.
	 * 
	 * https://en.wikipedia.org/wiki/Latitude
	 * https://en.wikipedia.org/wiki/Longitude
	 * 
	 */
	public static int easternMostVertex(Information info) {
		int eastmostvertex = 0;
		for (int i = 1; i < info.positions.size(); i++) {
			double pos = info.positions.get(i).longitude;
			if (pos >= info.positions.get(eastmostvertex).longitude) {
				eastmostvertex = i;
			}
		}
		return eastmostvertex;
	}

	public static void main(String[] args) {
		Information info = HighwayMap.undirectedGraphFromResources("USA-lower48-natl-traveled.tmg");
		int west = westernMostVertex(info);
		int east = easternMostVertex(info);

		// creating the EdgeWeightedGraph
		EdgeWeightedGraph infog = new EdgeWeightedGraph(info.labels.size()); // create new EdgeWeighted graph of weight
																				// distance
		for (int i : info.labels.keys()) {
			GPS source = info.positions.get(i);
			for (int j : info.graph.adj(i)) {
				GPS dest = info.positions.get(j);
				double distance = source.distance(dest);
				Edge edgev = new Edge(i, j, distance);
				infog.addEdge(edgev);
			}
		}

		String westName = info.labels.get(west);
		String eastName = info.labels.get(east);

		// BFS search from west to east
		BreadthFirstPaths bfs = new BreadthFirstPaths(info.graph, west);

		int numEdges = 0;
		double totalDistance = 0;

		Iterable<Integer> pathBFS = bfs.pathTo(east); // path from BFS
		int prevVertex = -1;
		for (int vertex : pathBFS) {
			if (prevVertex != -1) {
				double distance = info.positions.get(prevVertex).distance(info.positions.get(vertex));
				numEdges++;
				totalDistance += distance;
			}
			prevVertex = vertex;
		}

		System.out.println("BreadthFirst Search from " + westName + " to " + eastName + " has " + numEdges + " edges.");

		System.out.println("Shortest Distance BFS: " + totalDistance + " miles.");
		System.out.println("");

		// DFS
		DepthFirstSearchNonRecursive DFS = new DepthFirstSearchNonRecursive(info.graph, west);
		int numEdgesdfs = 0;
		double totalDistancedfs = 0;

		Iterable<Integer> pathDFS = DFS.pathTo(east); // path from BFS
		int prevVertexdfs = -1;
		for (int vertex : pathDFS) {
			if (prevVertexdfs != -1) {
				double distance = info.positions.get(prevVertexdfs).distance(info.positions.get(vertex));
				numEdgesdfs++;
				totalDistancedfs += distance;
			}
			prevVertexdfs = vertex;
		}
		System.out
				.println("DepthFirst Search from " + westName + " to " + eastName + " has " + numEdgesdfs + " edges.");

		System.out.println("Distance DFS: " + totalDistancedfs + " miles.");
		System.out.println("");

		// Dijkstra's Single Source Shortest Path Algorithm

		DijkstraUndirectedSP dsp = new DijkstraUndirectedSP(infog, west);

		int numEdgesd = 0;
		double totalDistanced = 0;

		Iterable<Edge> pathDijkstra = dsp.pathTo(east); // path from Dijkstra's algorithm
		for (Edge edge : pathDijkstra) {
			numEdgesd++;
			totalDistanced += edge.weight();
		}

		System.out
				.println("Dijkstra's algorithm from " + westName + " to " + eastName + " has " + numEdgesd + " edges.");
		System.out.println("Shortest Distance Dijkstra: " + totalDistanced + " miles.");

	}
}
