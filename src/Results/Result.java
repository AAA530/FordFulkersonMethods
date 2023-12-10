package Results;

public class Result {
    public int paths;
    public int meanLength;
    public int meanProportionalLength;
    public int totalEdges;

    public Result(int paths, int meanLength, int meanProportionalLength, int totalEdges) {
        this.paths = paths;
        this.meanLength = meanLength;
        this.meanProportionalLength = meanProportionalLength;
        this.totalEdges = totalEdges;
    }
}
