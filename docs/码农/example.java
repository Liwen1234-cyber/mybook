import java.util.*;

class Main{
    public static void main(String[] args){
        List<List<Integer>> list = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // 节点
        int M = in.nextInt(); // 边
        int[][] edges = new int[M][2];
        for(int i = 0; i < M; i++){
            edges[i][0] = in.nextInt();
            edges[i][1] = in.nextInt();
        }

    }
}