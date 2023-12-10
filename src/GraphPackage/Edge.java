package GraphPackage;

public class Edge {
    public Vertex u;
    public Vertex v;
    public int capacity;

    Edge(Vertex u, Vertex v, int capacity) {
        this.u = u;
        this.v = v;
        this.capacity = capacity;
    }
}
