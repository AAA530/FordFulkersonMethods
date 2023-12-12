package FordFulkersonMethods;

import GraphPackage.Edge;
import GraphPackage.Graph;
import GraphPackage.Vertex;
import Results.Result;

import java.util.*;


class VertexComparator2 implements Comparator<Vertex> {
    @Override
    public int compare(Vertex o1, Vertex o2) {
        if (o1.distance < o2.distance)
            return 1;
        else if (o1.distance > o2.distance)
            return -1;
        return 0;
    }
}

public class MaximumCapacityPath {
    private void initializeSingleSource(Graph G, Vertex source){
        for(Vertex v:G.vertices){
            v.distance=Integer.MIN_VALUE;
            v.parent = null;
        }
        source.distance=Integer.MAX_VALUE;
    }





    private List<Vertex> Dijkastra(Graph g, Vertex source, Vertex sink) {

        initializeSingleSource(g,source);

        PriorityQueue<Vertex> pQueue = new PriorityQueue<>(new VertexComparator2());

        pQueue.offer(source);

        while (!pQueue.isEmpty()){
            Vertex u = pQueue.poll();
            for(Edge e:u.edges){
                if(e.flow < e.capacity){

                    // Relax edges
                    if(e.v.distance < Math.min(e.u.distance, e.capacity-e.flow)){
                        e.v.distance = Math.min(e.u.distance, e.capacity-e.flow);
                        e.v.parent = u;
                        pQueue.offer(e.v);
                    }
                }
            }
        }

        List<Vertex> augmentingPath = new ArrayList<>();
        Vertex temp = sink;

        while(temp != null){
            augmentingPath.add(temp);
            temp = temp.parent;
        }

        Collections.reverse(augmentingPath);

        return augmentingPath;
    }




    public Result run(Graph G){


        int maxflow = 0,paths=0;
        List<Double> meanLength = new ArrayList<>();

        Graph residualGraph = residualGraph(G);

        List<Vertex> augmentingPath = Dijkastra(residualGraph,residualGraph.source,residualGraph.sink);



        while(augmentingPath != null && augmentingPath.size() > 1){

            paths++;


//            if (augmentingPath != null) {
//                System.out.print("Augmenting path: ");
//                for (Vertex v : augmentingPath) {
//                    System.out.print(v.name + "->");
//                }
//            } else {
//                System.out.println("No augmenting path found.");
//            }



            int residualCapacity = Integer.MAX_VALUE;
            for(int i=0; i < augmentingPath.size()-1; i++){
                Vertex u = augmentingPath.get(i);
                Vertex v = augmentingPath.get(i+1);

                for(Edge e : u.edges){
                    if(e.v == v){
                        residualCapacity = Math.min(residualCapacity, e.capacity-e.flow);
                        break;
                    }
                }
            }

//            System.out.println("residualCapacity"+residualCapacity);

            double numberOfEdges = 0;
            // update capacities for residual graph based on residual capacity
            for(int i=0; i < augmentingPath.size()-1; i++){
                Vertex u = augmentingPath.get(i);
                Vertex v = augmentingPath.get(i+1);
                numberOfEdges++;
                for(Edge eOrig: G.edges){
                    if((eOrig.u.name == u.name) && (eOrig.v.name == v.name)){
                        eOrig.flow += residualCapacity;
                        break;
                    }
                }
            }

            meanLength.add(numberOfEdges);

            maxflow += residualCapacity;
//            System.out.println("maxFlow: "+maxflow);
            residualGraph = residualGraph(G);
            augmentingPath = Dijkastra(residualGraph,residualGraph.source,residualGraph.sink);
        }



        System.out.println("MaxFlow"+maxflow);
        Result res = new Result(paths,meanLength,G.bfsLength,G);
        return res;
    }


    private Graph residualGraph(Graph G){

        Graph residualGraph = new Graph();

        for(Vertex v:G.vertices){
            Vertex vertex = new Vertex(v.x, v.y, v.name);
            residualGraph.addVertex(vertex);


        }

        for(Edge eOriginal:G.edges){

            Vertex v = eOriginal.v;
            Vertex u = eOriginal.u;

            int capacity = eOriginal.capacity;
            int flow = eOriginal.flow;

            Vertex toAddV = null;
            Vertex toAddU = null;
            for(Vertex vResidual:residualGraph.vertices){
                if(vResidual.name == v.name){
                    toAddV = vResidual;
                }
                if(vResidual.name == u.name){
                    toAddU = vResidual;
                }
            }

            residualGraph.addEdge(toAddU,toAddV,capacity-flow,0);
            residualGraph.addEdge(toAddV,toAddU,flow,0);

        }


        Vertex source=null,sink=null;
        for(Vertex v:residualGraph.vertices){
            if(G.source.name==v.name){
                source = v;
            }
            if(G.sink.name==v.name){
                sink = v;
            }
        }

        residualGraph.setSourceSink(source,sink);

        return residualGraph;
    }

}
