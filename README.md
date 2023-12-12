
# Dhairya Patel
# 40231545

# Project Description 

This project aims to implement and compare different augmenting path algorithms for the Ford-Fulkerson maximum flow algorithm. The study focuses on four variations: Shortest Augmenting Path (SAP), DFS-like, Maximum Capacity (MaxCap), and Randomized Augmenting Path algorithms. These algorithms will be tested on randomly generated source-sink graphs



## Augmenting Path Algorithms 
1. Shortest Augmenting Path (SAP) :For this a modified version of Dijkstra algorithm is used which implements the algorithm using unit capacity for each edge, effectively the same as running BFS.
2. DFS-like : In this algorithm a depth-first search version of Dijkstra algorithm is used which explores the nodes in depth-first manner in order to find the augmenting path.
3. Maximum Capacity (MaxCap): Augmenting paths are calculated with the maximum capacity for this version of Dijkstra. The capacity of critical edge (cf(p) of augmenting path p) will be the value t.d when this algorithm converges.
4. Random : This modification is similar to the one for DFS-like except that a random value is used in order to relax any edge associated with a given vertex. 


## Folder Structure

### src

* [FordFulkersonMethods/](./src/FordFulkersonMethods)
  * [DFSlikePaths.java](./src/FordFulkersonMethods/DFSlikePaths.java)
  * [MaximumCapacityPath.java](./src/FordFulkersonMethods/MaximumCapacityPath.java)
  * [RandomPaths.java](./src/FordFulkersonMethods/RandomPaths.java)
  * [ShortestAugmentingPath.java](./src/FordFulkersonMethods/ShortestAugmentingPath.java)
* [GraphPackage/](./src/GraphPackage)
  * [Edge.java](./src/GraphPackage/Edge.java)
  * [Graph.java](./src/GraphPackage/Graph.java)
  * [Vertex.java](./src/GraphPackage/Vertex.java)
* [Results/](./src/Results)
  * [Result.java](./src/Results/Result.java)
* [SinkSourceGraphGenerator/](./src/SinkSourceGraphGenerator)
  * [SinkSourceGraphGenerator.java](./src/SinkSourceGraphGenerator/SinkSourceGraphGenerator.java)
* [graph.csv](./src/graph.csv)
* [graph_dfs_like_answer.csv](./src/graph_dfs_like_answer.csv)
* [Main.java](./src/Main.java)



## Compile & Run

1. To run this program we need to execute the Main.java file, this will start the command line execution of the program. The first step is to choose whether you want to generate a graph or run the Ford-Fulkerson algorithm on a already generated graph.
2. The code will run by itself for the input values of n = 200, r = 0.3, upperCap = 50.s
3. The Graph is already generated in the files [GeneratedGraphs](./GeneratedGraphs).
4. The Output will be printed on the console.
5. The output will be an object of the Result class which contains the following 4 parameters:-
   * Paths: the number of augmenting paths required until Ford-Fulkerson completes.
   * Mean length (ML): average length (i.e., number of edges) of the augmenting paths.
   * Mean proportional length (MPL): the average length of the augmenting path as a fraction of the longest acyclic path from source to sink.
   * Total edges: the total number of edges in the graph.


## How to change the input values 

1. To change the values of (n,r,upperCap) open file [Main.java](./src/Main.java) and change the variables n,r and upperCap.
2. And uncomment line 20,21,22.
```
        Graph graph = generateSinkSourceGraph(n, r, upperCap);
        graph.saveToCSV(graphLocation);
```
4. And complie and run.
5. The generated Graph will be in [GeneratedGraphs](./GeneratedGraphs).
6. The Output will be printed on the console.
7. The output will be an object of the Result class which contains the following 4 parameters:-
   * Paths: the number of augmenting paths required until Ford-Fulkerson completes.
   * Mean length (ML): average length (i.e., number of edges) of the augmenting paths.
   * Mean proportional length (MPL): the average length of the augmenting path as a fraction of the longest acyclic path from source to sink.
   * Total edges: the total number of edges in the graph.


## Sample Output

<img width="617" alt="outputSS" src="https://github.com/AAA530/FordFulkersonMethods/assets/45384186/a892a2c1-1e79-41c9-8895-75422b18be1f">

