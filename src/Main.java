import FordFulkersonMethods.DFSlikePaths;
import FordFulkersonMethods.MaximumCapacityPath;
import FordFulkersonMethods.RandomPaths;
import FordFulkersonMethods.ShortestAugmentingPath;
import GraphPackage.Graph;
import Results.Result;

import static GraphPackage.Graph.loadFromCSV;
import static SinkSourceGraphGenerator.SinkSourceGraphGenerator.generateSinkSourceGraph;

public class Main {
    public static void main(String[] args) {

        // generate graph with n,r and upperCap
        int n = 300; // replace with your desired number of vertices
        double r = 0.2; // replace with your desired maximum distance
        int upperCap = 50; // replace with your desired maximum capacity
        String graphLocation = "GeneratedGraphs/graph"+n+"-"+r+"-"+upperCap+".csv"; // where to store graph

//        Graph graph = generateSinkSourceGraph(n, r, upperCap);
//        graph.saveToCSV(graphLocation);




        System.out.println("------------------------- Shortest Augmenting Path -------------------------");
        Graph loadedGraph = loadFromCSV(graphLocation);
        Result result = new ShortestAugmentingPath().run(loadedGraph);
        result.print();


        System.out.println("------------------------- DFS like Paths -------------------------");
        Graph loadedGraphDFSLike = loadFromCSV(graphLocation);
        Result resultDFSLike = new DFSlikePaths().run(loadedGraphDFSLike);
        resultDFSLike.print();

        System.out.println("------------------------- Maximum Capacity Path -------------------------");
        Graph loadedMaximumCapGraph = loadFromCSV(graphLocation);
        Result resultMaxcap = new MaximumCapacityPath().run(loadedMaximumCapGraph);
        resultMaxcap.print();


        System.out.println("------------------------- RandomPaths -------------------------");
        Graph loadedRandomPath = loadFromCSV(graphLocation);
        Result resultRandom = new RandomPaths().run(loadedRandomPath);
        resultRandom.print();



    }
}