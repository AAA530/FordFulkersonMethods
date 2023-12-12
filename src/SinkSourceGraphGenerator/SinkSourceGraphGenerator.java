package SinkSourceGraphGenerator;

import GraphPackage.Edge;
import GraphPackage.Graph;
import GraphPackage.Vertex;

import java.util.*;

public class SinkSourceGraphGenerator {

    public static void main(String[] args) {
        int n = 10; // replace with your desired number of vertices
        double r = 0.2; // replace with your desired maximum distance
        int upperCap = 100; // replace with your desired maximum capacity

        Graph graph = generateSinkSourceGraph(n, r, upperCap);

        // Print or save the graph in the desired format (e.g., adjacency list, CSV, XML)
        printGraph(graph);
    }

    public static Graph generateSinkSourceGraph(int n, double r, int upperCap) {
        Graph graph = new Graph();

        // Create vertices and assign random coordinates
        for (int i = 0; i < n; i++) {
            double x = Math.random();
            double y = Math.random();
            Vertex vertex = new Vertex(x, y, i);
            graph.addVertex(vertex);
        }

        // Assign edges based on random distance and capacity
        for (Vertex u : graph.vertices) {
            for (Vertex v : graph.vertices) {
                if (u != v && Math.pow(u.x - v.x, 2) + Math.pow(u.y - v.y, 2) <= r * r) {
                    double rand = Math.random();
                    if (rand < 0.5) {
                        if (!hasEdge(graph.edges, u, v)) {
                            graph.addEdge(u, v, (int) (Math.random() * (upperCap - 1) + 1),0);
                        }
                    } else {
                        if (!hasEdge(graph.edges, v, u)) {
                            graph.addEdge(v, u, (int) (Math.random() * (upperCap - 1) + 1),0);
                        }
                    }
                }
            }
        }

        // Randomly select source and find the longest acyclic path
        Vertex source = graph.vertices.get((int) (Math.random() * n));
        Vertex sink = findLongestAcyclicPath(graph, source);

        graph.setSourceSink(source,sink);

        return graph;
    }

    private static Vertex findLongestAcyclicPath(Graph graph, Vertex source) {
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Vertex> parentMap = new HashMap<>();
        Queue<Vertex> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);
        parentMap.put(source, null);

        Vertex lastVisited = null;

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
//            System.out.println(current.name);

            for (Edge edge : current.edges) {
                Vertex neighbor = (edge.u == current) ? edge.v : edge.u;

                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                    lastVisited = neighbor;
                }
            }
        }

        // Reconstruct the longest path
        Vertex endNode = lastVisited;
        Vertex returnNode = lastVisited;


        int length = 0;

        while (endNode != null && parentMap.get(endNode) != null) {
//            System.out.println(endNode.name);
            endNode = parentMap.get(endNode);
            length++;
        }

//        System.out.println("length"+length);
        graph.setBFSLength(length);
        return returnNode;
    }

    private static boolean hasEdge(List<Edge> edges, Vertex u, Vertex v) {
        for (Edge edge : edges) {
            if ((edge.u == u && edge.v == v) || (edge.u == v && edge.v == u)) {
                return true;
            }
        }
        return false;
    }

    private static void printGraph(Graph graph) {
        for (Vertex vertex : graph.vertices) {
            System.out.print("Vertex (" + vertex.name + ") -> Edges: ");
            for (Edge edge : vertex.edges) {
                System.out.print("(" + edge.u.name + ") -[" + edge.capacity + "]-> ");
                System.out.print("(" + edge.v.name + ")  ");
            }
            System.out.println();
        }
    }
}
