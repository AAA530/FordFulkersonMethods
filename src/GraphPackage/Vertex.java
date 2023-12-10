package GraphPackage;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    public double x;
    public double y;
    public int name;
    public List<Edge> edges;

    public int distance=0;
    public Vertex parent=null;

    public Vertex(double x, double y,int name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.edges = new ArrayList<>();
    }
}
