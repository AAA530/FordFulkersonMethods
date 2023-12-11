package Results;

import GraphPackage.Edge;
import GraphPackage.Graph;

import java.util.List;

public class Result {
    public int paths;
    public List<Double> meanLength;
    public int bfsLength;
    public int totalEdges;

    public Result(int paths, List<Double> meanLength, int bfsLength, Graph g) {
        this.paths = paths;
        this.meanLength = meanLength;
        this.bfsLength = bfsLength;

        this.totalEdges=0;

        for (Edge e:g.edges){
            this.totalEdges++;
        }

    }

    public void print(){
        System.out.println("paths = "+this.paths);
        double meanLengthans=0;
        for(Double d:meanLength){
            meanLengthans +=d;
        }
        meanLengthans=meanLengthans/paths;
        System.out.println("meanLength = "+meanLengthans);
        System.out.println("meanProportionalLength = "+meanLengthans/bfsLength);
        System.out.println("totalEdges = "+this.totalEdges);
    }
}
