import FordFulkersonMethods.ShortestAugmentingPath;
import GraphPackage.Graph;
import SinkSourceGraphGenerator.SinkSourceGraphGenerator;

import static GraphPackage.Graph.loadFromCSV;
import static SinkSourceGraphGenerator.SinkSourceGraphGenerator.generateSinkSourceGraph;

public class Main {
    public static void main(String[] args) {

        // generate graph with n,r and upperCap
        int n = 10; // replace with your desired number of vertices
        double r = 0.3; // replace with your desired maximum distance
        int upperCap = 100; // replace with your desired maximum capacity

//        Graph graph = generateSinkSourceGraph(n, r, upperCap);
//        graph.printGraph();
//
//        graph.saveToCSV("src/graph.csv");

        System.out.println("-------------------------");
        Graph loadedGraph = loadFromCSV("src/graph.csv");
        loadedGraph.printGraph();

        System.out.println("-------------------------");

        new ShortestAugmentingPath().run(loadedGraph);

    }
}