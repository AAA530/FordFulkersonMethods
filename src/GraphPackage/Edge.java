package GraphPackage;

public class Edge {
    public Vertex u;
    public Vertex v;
    public int capacity;
    public int flow=0;

    Edge(Vertex u, Vertex v, int capacity,int flow) {
        this.u = u;
        this.v = v;
        this.capacity = capacity;
        this.flow = flow;
    }


}
