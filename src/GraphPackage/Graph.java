package GraphPackage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
    public List<Vertex> vertices;
    public List<Edge> edges;

    public Vertex source;
    public Vertex sink;

    public Graph() {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Vertex u, Vertex v, int capacity) {
        Edge edge = new Edge(u, v, capacity);
        edges.add(edge);
        u.edges.add(edge);
//        v.edges.add(edge);
    }

    public void setSourceSink(Vertex src, Vertex sink) {
        this.source = src;
        this.sink = sink;
    }

    public void printGraph() {
        for (Vertex vertex : this.vertices) {
            System.out.print("Vertex (" + vertex.name + ") -> Edges: ");
            for (Edge edge : vertex.edges) {
                System.out.print("(" + edge.u.name + ") -[" + edge.capacity + "]-> ");
                System.out.print("(" + edge.v.name + ")  ");
            }
            System.out.println();
        }
    }

    public void saveToCSV(String filePath) {
        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            StringBuilder sb = new StringBuilder();
            sb.append("VertexName,X,Y\n");
            for (Vertex vertex : vertices) {
                sb.append(vertex.name).append(",").append(vertex.x).append(",").append(vertex.y).append("\n");
            }
            sb.append("\nEdge,Vertex1,Vertex2,Capacity\n");
            for (Edge edge : edges) {
                sb.append(edge.u.name).append(",").append(edge.v.name).append(",").append(edge.capacity).append("\n");
            }
            sb.append("\nSource,X,Y\n");
            sb.append(this.source.name).append(",").append(this.source.x).append(",").append(this.source.y).append("\n");
            sb.append("\nSink,X,Y\n");
            sb.append(this.sink.name).append(",").append(this.sink.x).append(",").append(this.sink.y).append("\n");

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Graph loadFromCSV(String filePath) {
        Graph graph = new Graph();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // skip the header line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    break;
                }
                String[] vertexData = line.split(",");
                int vertexName = Integer.parseInt(vertexData[0]);
                double x = Double.parseDouble(vertexData[1]);
                double y = Double.parseDouble(vertexData[2]);
                Vertex vertex = new Vertex(x, y, vertexName);
                graph.addVertex(vertex);
            }

            scanner.nextLine(); // skip the header line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    break;
                }
                String[] edgeData = line.split(",");
                int vertex1Name = Integer.parseInt(edgeData[0]);
                int vertex2Name = Integer.parseInt(edgeData[1]);
                int capacity = Integer.parseInt(edgeData[2]);
                Vertex vertex1 = graph.vertices.stream().filter(v -> v.name == vertex1Name).findFirst().orElse(null);
                Vertex vertex2 = graph.vertices.stream().filter(v -> v.name == vertex2Name).findFirst().orElse(null);
                if (vertex1 != null && vertex2 != null) {
                    graph.addEdge(vertex1, vertex2, capacity);
                }
            }

            scanner.nextLine(); // skip the header line
            String line = scanner.nextLine();
            String[] vertexData = line.split(",");
            int vertexName = Integer.parseInt(vertexData[0]);
            double x = Double.parseDouble(vertexData[1]);
            double y = Double.parseDouble(vertexData[2]);
            Vertex source = new Vertex(x, y, vertexName);

            scanner.nextLine();
            scanner.nextLine(); // skip the header line
            line = scanner.nextLine();
            vertexData = line.split(",");
            vertexName = Integer.parseInt(vertexData[0]);
            x = Double.parseDouble(vertexData[1]);
            y = Double.parseDouble(vertexData[2]);
            Vertex sink = new Vertex(x, y, vertexName);

            graph.setSourceSink(source,sink);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return graph;
    }

}

