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
    int d;

    Vertex parent;



    public VertexWrapper(Vertex v,int d){
        this.v = v;
        this.d = d;
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
        if (o1.d < o2.d)
            return -1;
        else if (o1.d > o2.d)
            return 1;
        return 0;
    }
}


public class ShortestAugmentingPath {


    private void initializeSingleSource(Graph G,Vertex source,HashMap d){

        for(Vertex v:G.vertices){
            d.put(v.name,Integer.MAX_VALUE);
        }

        d.put(source.name,0);

    }

    public void Relax(VertexWrapper u,VertexWrapper v,int w,PriorityQueue<VertexWrapper> pQueue){
        if(v.d>u.d+w){
            v.d = u.d + 1;
            v.setParent(u.v);

            pQueue.remove(v);
            pQueue.add(v);
        }
    }



    private boolean dfs(Graph g, Vertex source, Vertex sink, HashMap<Integer, Integer> parent, HashMap<String, Integer> flow) {

        HashMap<Integer, Integer> d = new HashMap<>();
        HashMap<Vertex, VertexWrapper> ver = new HashMap<>();

        initializeSingleSource(g,source,d);

        PriorityQueue<VertexWrapper> pQueue = new PriorityQueue<VertexWrapper>(new VertexWrapperComparator());
        for(Vertex v:g.vertices){
            VertexWrapper temp = new VertexWrapper(v, d.get(v.name) );
            ver.put(v,temp);
            pQueue.add(temp);
        }

        while (!pQueue.isEmpty()){
            VertexWrapper uWrap = pQueue.poll();
            Vertex u = uWrap.v;



            for(Edge e:u.edges){
//                if()
//                Relax(uWrap,ver.get(v),1,pQueue);
            }

        }

        return false;
    }


    public Result run(Graph G){


        HashMap<String,Integer> flow = new HashMap<>();
        for (Edge edge : G.edges) {
            flow.put(Integer.toString(edge.u.name)+Integer.toString(edge.v.name),0);
        }

        HashMap<Integer,Integer> parent = new HashMap<>();
        int maxflow = 0,paths=0,meanLength=0,meanProportionalLength=0,totalEdges=0;

        while(dfs(G,G.source,G.sink,parent,flow)){

        }


        Result res = new Result(paths,meanLength,meanProportionalLength,totalEdges);
        return res;
    }


}
