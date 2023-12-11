package FordFulkersonMethods;

import GraphPackage.Edge;
import GraphPackage.Graph;
import GraphPackage.Vertex;
import Results.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DFSlikePaths {

    private int counter=0;
    private void initializeSingleSource(Graph G,Vertex source){

        for(Vertex v:G.vertices){
            v.distance=99999;
            v.parent = null;
        }

        source.distance=0;

    }

    private void Relax(Vertex u, Vertex v, int w, PriorityQueue<Vertex> pQueue){

//        System.out.println("Relax "+u.v.name+","+v.v.name);
//        System.out.println("u.distance "+u.distance+"v.distance"+v.distance);

//        System.out.println("Relax "+u.name+","+v.name);
        if(v.distance==99999){
            v.distance = this.counter--;
            v.parent = u;
            pQueue.offer(v);
        }
//        if(v.distance >u.distance +w){
//            v.distance = u.distance + 1;
//            v.parent = u;
//
//            pQueue.offer(v);
////            pQueue.add(v);
//        }
    }




    private List<Vertex> Dijkastra(Graph g, Vertex source,Vertex sink) {

        this.counter = 0;

        initializeSingleSource(g,source);


        PriorityQueue<Vertex> pQueue = new PriorityQueue<>(new VertexComparator());
        for(Vertex v:g.vertices){
            pQueue.add(v);
        }

        while (!pQueue.isEmpty()){
            Vertex u = pQueue.poll();

//            System.out.println("u.name:"+u.name);

            for(Edge e:u.edges){

                if(e.flow < e.capacity){
                    Relax(u,e.v,1,pQueue);
                }
            }
        }

//        for(Vertex v:g.vertices){
//            System.out.println(v.name+" v.parent "+(v.parent!=null ? v.parent.name:"null")+ " v.distance "+v.distance);
//        }

        List<Vertex> augmentingPath = new ArrayList<>();
        Vertex temp = sink;

        while(temp != null){
            System.out.println(temp.name+" v.parent "+(temp.parent!=null ? temp.parent.name:"null"));
            augmentingPath.add(temp);
            temp = temp.parent;
        }

//        augmentingPath.add(temp);

        Collections.reverse(augmentingPath);

        return augmentingPath;
    }




    public Result run(Graph G){


        int maxflow = 0,paths=0;
        List<Double> meanLength = new ArrayList<>();
        double meanProportionalLength=0,totalEdges=0;

        Graph residualGraph = residualGraph(G);

//        residualGraph.printGraph();

//        List<Vertex> augmentingPath = dijkastra(residualGraph, source, sink);
        List<Vertex> augmentingPath = Dijkastra(residualGraph,residualGraph.source,residualGraph.sink);



        while(augmentingPath != null && augmentingPath.size() > 1){

            paths++;


            if (augmentingPath != null) {
                System.out.print("Augmenting path: ");
                for (Vertex v : augmentingPath) {
                    System.out.print(v.name + "->");
                }
            } else {
                System.out.println("No augmenting path found.");
            }



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

            System.out.println("residualCapacity"+residualCapacity);

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

//                for(Edge e : residualGraph.get(u)){
//                    if(e.dest == v){
//                        e.capacity -= residualCapacity;
//                        break;
//                    }
//                }
//
//                for(Edge bEdge : residualGraph.get(v)){
//                    if(bEdge.dest == u){
//                        bEdge.capacity += residualCapacity;
//                        break;
//                    }
//                }
            }

            meanLength.add(numberOfEdges);

            maxflow += residualCapacity;
            System.out.println("maxFlow: "+maxflow);
            residualGraph = residualGraph(G);
//            residualGraph.printGraph();

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

//        for(Vertex u:G.vertices){
//            for(Edge originalGraphEdge:u.edges){
//
//
//            }
//        }


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
