import FordFulkersonMethods.DFSlikePaths;
import FordFulkersonMethods.MaximumCapacityPath;
import FordFulkersonMethods.RandomPaths;
import FordFulkersonMethods.ShortestAugmentingPath;
import GraphPackage.Graph;
import Results.Result;
import SinkSourceGraphGenerator.SinkSourceGraphGenerator;

import static GraphPackage.Graph.loadFromCSV;
import static SinkSourceGraphGenerator.SinkSourceGraphGenerator.generateSinkSourceGraph;

public class Main {
    public static void main(String[] args) {

        // generate graph with n,r and upperCap
        int n = 100; // replace with your desired number of vertices
        double r = 0.2; // replace with your desired maximum distance
        int upperCap = 100; // replace with your desired maximum capacity

        Graph graph = generateSinkSourceGraph(n, r, upperCap);
        System.out.println(graph.bfsLength);
        graph.printGraph();

        graph.saveToCSV("src/graph.csv");

//        System.out.println("-------------------------");
        Graph loadedGraph = loadFromCSV("src/graph.csv");
        loadedGraph.printGraph();
        Result result = new ShortestAugmentingPath().run(loadedGraph);
        result.print();


        System.out.println("-------------------------");
        Graph loadedGraphDFSLike = loadFromCSV("src/graph.csv");
//        loadedGraphDFSLike.printGraph();
        Result resultDFSLike = new DFSlikePaths().run(loadedGraphDFSLike);
        resultDFSLike.print();

        System.out.println("-------------------------");
        Graph loadedMaximumCapGraph = loadFromCSV("src/graph.csv");
//        loadedGraphDFSLike.printGraph();
        Result resultMaxcap = new MaximumCapacityPath().run(loadedMaximumCapGraph);
        resultMaxcap.print();


        System.out.println("-------------------------");
        Graph loadedRandomPath = loadFromCSV("src/graph.csv");
//        loadedGraphDFSLike.printGraph();
        Result resultRandom = new RandomPaths().run(loadedRandomPath);
        resultRandom.print();



    }
}