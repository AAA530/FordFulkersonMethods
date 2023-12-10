package FordFulkersonMethods;

import GraphPackage.Edge;
import GraphPackage.Graph;
import GraphPackage.Vertex;
import Results.Result;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

class VertexWrapper{
    Vertex v;
    int distance;

    Vertex parent;



    public VertexWrapper(Vertex v,int distance){
        this.v = v;
        this.distance = distance;
        this.parent = null;
    }
    public void setParent(Vertex p){
        this.parent = p;
    }
}

class VertexWrapperComparator implements Comparator<VertexWrapper> {


//    public int compare(Student s1, Student s2) {
//        if (s1.cgpa < s2.cgpa)
//            return 1;
//        else if (s1.cgpa > s2.cgpa)
//            return -1;
//        return 0;
//    }

    @Override
    public int compare(VertexWrapper o1, VertexWrapper o2) {
        if (o1.distance < o2.distance)
            return -1;
        else if (o1.distance > o2.distance)
            return 1;
        return 0;
    }
}


public class ShortestAugmentingPath {


    private void initializeSingleSource(Graph G,Vertex source,HashMap<Integer, Integer> distance){

        for(Vertex v:G.vertices){
            distance.put(v.name,9999999);
        }

        distance.put(source.name,0);

    }

    public void Relax(VertexWrapper u,VertexWrapper v,int w,PriorityQueue<VertexWrapper> pQueue){

//        System.out.println("Relax "+u.v.name+","+v.v.name);
//        System.out.println("u.distance "+u.distance+"v.distance"+v.distance);

        if(v.distance >u.distance +w){



            v.distance = u.distance + 1;
            v.setParent(u.v);

            pQueue.remove(v);
            pQueue.add(v);
        }
    }



    private HashMap<Vertex, VertexWrapper> dfs(Graph g, Vertex source, Vertex sink, HashMap<Integer, Integer> parent, HashMap<String, Integer> flow) {

        HashMap<Integer, Integer> distance = new HashMap<>();
        HashMap<Vertex, VertexWrapper> ver = new HashMap<>();

        initializeSingleSource(g,source,distance);

        PriorityQueue<VertexWrapper> pQueue = new PriorityQueue<VertexWrapper>(new VertexWrapperComparator());
        for(Vertex v:g.vertices){
            VertexWrapper temp = new VertexWrapper(v, distance.get(v.name) );
            ver.put(v,temp);
            pQueue.add(temp);
        }

        while (!pQueue.isEmpty()){
            VertexWrapper uWrap = pQueue.poll();
            Vertex u = uWrap.v;

//            System.out.println("pQueue.poll(): "+u.name);





            for(Edge e:u.edges){
                if( e.capacity - flow.get(Integer.toString(u.name)+Integer.toString(e.v.name)) >0){
                    Relax(uWrap,ver.get(e.v),1,pQueue);
                }
            }

        }

//        for (VertexWrapper vWrap: ver.values()){
//            System.out.println("name:"+vWrap.v.name+" v.parent "+(vWrap.parent!=null ? vWrap.parent.name : "null" )+" v.distance"+vWrap.distance);
//        }



        return ver;
    }


    public Result run(Graph G){


        HashMap<String,Integer> flow = new HashMap<>();
        for (Edge edge : G.edges) {
            flow.put(Integer.toString(edge.u.name)+Integer.toString(edge.v.name),0);
        }

        HashMap<Integer,Integer> parent = new HashMap<>();
        int maxflow = 0,paths=0,meanLength=0,meanProportionalLength=0,totalEdges=0;

        HashMap<Vertex, VertexWrapper> verWrap = dfs(G,G.source,G.sink,parent,flow);


        for (VertexWrapper vWrap: verWrap.values()){
            System.out.println("name:"+vWrap.v.name+" v.parent "+(vWrap.parent!=null ? vWrap.parent.name : "null" )+" v.distance"+vWrap.distance);
        }

        System.out.println("----------"+verWrap.get(G.sink));
        while(verWrap.get(G.sink).parent!=null){

            int minFlowOnPath = 999999;
            VertexWrapper endNodeWrap = verWrap.get(G.sink);
            while (endNodeWrap.parent!=null){
                for(Edge e :endNodeWrap.v.edges){
                    if(e.u.equals(endNodeWrap.parent) && e.v.equals(endNodeWrap.v)){
                        minFlowOnPath = Math.min(minFlowOnPath, e.capacity - flow.get(Integer.toString(endNodeWrap.parent.name)+Integer.toString(endNodeWrap.v.name)));
                    }
                }
            }

            endNodeWrap = verWrap.get(G.sink);
            while (endNodeWrap.parent!=null){
                for(Edge e :endNodeWrap.v.edges){
                    if(e.u.equals(endNodeWrap.parent) && e.v.equals(endNodeWrap.v)){
                        flow.put(Integer.toString(endNodeWrap.parent.name)+Integer.toString(endNodeWrap.v.name),flow.get(Integer.toString(endNodeWrap.parent.name)+Integer.toString(endNodeWrap.v.name))+minFlowOnPath);
                    }
                }
            }

            maxflow = maxflow+minFlowOnPath;

            verWrap = dfs(G,G.source,G.sink,parent,flow);
        }



        System.out.println("MaxFlow"+maxflow);
        Result res = new Result(paths,meanLength,meanProportionalLength,totalEdges);
        return res;
    }


}
