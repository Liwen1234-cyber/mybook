import java.util.*;

public class Main{

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        int N = scanner.nextInt();
        int E = scanner.nextInt();
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[N]; //记录每个节点的入度
        List<Integer> res = new ArrayList<>();

        for(int i = 0; i < N; i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0; i < E; i++){
            int start = scanner.nextInt();
            int end = scanner.nextInt();
            inDegree[end]++;
            graph.get(start).add(end);
        }

        Deque<Integer> deque = new LinkedList<>();
        for(int i = 0; i < N; i++){
            if(inDegree[i] == 0){
                deque.offer(i);
            }
        }

        while(!deque.isEmpty()){
            int cur = deque.poll();
            res.add(cur);

            for(int next : graph.get(cur)){
                inDegree[next]--;
                if(inDegree[next] == 0){
                    deque.offer(next);
                }
            }
        }

        if(res.size() != N){ //有环
            System.out.println(-1);
        }
        else{
            for(int i = 0; i < N; i++){
                if(i == N - 1){
                    System.out.print(res.get(i));
                }
                else System.out.print(res.get(i) + " ");

            }
        }

        scanner.close();
    }
}

