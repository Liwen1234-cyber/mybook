import java.util.*;

class Disjoint{
    private int[] father;

    public Disjoint(int n){
        father = new int[n];
        for(int i = 0; i < n; i++){
            father[i] = i;
        }
    }
    public int find(int x){
        return father[x] == x ? x : find(father[x]);
    }

    public void join(int x, int y){ // x -> y
        int fx = find(x);
        int fy = find(y);
        father[fx] = fy;
    }

    public boolean isSame(int x, int y){
        return find(x) == find(y);
    }
}

class Edge{
    int start;
    int end;
    
    public Edge(int start, int end){
        this.start = start; 
        this.end = end;
    }
}

class Node{
    int val;
    int in;
    int out;

}


public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Disjoint disjoint = new Disjoint(n+1);
        List<Edge> edges = new ArrayList<>();
        Node[] nodes = new Node[n+1];
        for(int i = 1; i <= n; i++){
            nodes[i] = new Node();
        }

        Integer doubleIn = null;
        for(int i = 0; i < n; i++){
            int s = scanner.nextInt();
            int t = scanner.nextInt();

            edges.add(new Edge(s, t));
            nodes[t].in++;
            if(nodes[t].in >= 2) doubleIn = t;

        }

        Edge res = null;
        if(doubleIn != null){ // 入为2
            List<Edge> doubleInEdges = new ArrayList<>();
            for(Edge edge : edges){
                if(edge.end == doubleIn){
                    doubleInEdges.add(edge);
                }

                if(doubleInEdges.size() == 2) break;
            }
            
            Edge edge = doubleInEdges.get(1); //先写后一条入为2的边
            if(isTreeWithExclude(edges, edge, nodes)) res = edge;
            else res = doubleInEdges.get(0);

        }
        else{ // 入不为2则就除去环
            res = RemoveEdge(edges, nodes);
        }


        System.out.println(res.start + " " + res.end);

        scanner.close();
    }
    private static boolean isTreeWithExclude(List<Edge> edges, Edge excludedEdge, Node[] nodes){ //判断是否带环
        Disjoint disjoint = new Disjoint(nodes.length);
        for(Edge edge : edges){
            if(edge == excludedEdge) continue;

            if(disjoint.isSame(edge.start, edge.end)){
                return false;
            }
            disjoint.join(edge.start, edge.end);
        }

        return true;
    }

    private static Edge RemoveEdge(List<Edge> edges, Node[] nodes){ //找到最后的一条造成环的边
        Disjoint disjoint = new Disjoint(nodes.length);
        for(Edge edge : edges){
            if(disjoint.isSame(edge.start, edge.end)) return edge;
            
            disjoint.join(edge.start, edge.end);
        }
        return null;
    }
}

