import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        List<List<Integer>> dir = new ArrayList<>();
        int points = scanner.nextInt();
        int edges = scanner.nextInt();
        for(int i = 0; i < points; i++){
            dir.add(new ArrayList<>());
        }
        for(int i = 0; i < edges; i++){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            dir.get(a-1).add(b-1);
        }
        boolean[] visited = new boolean[points];
        // dfs(dir, visited, 0);
        bfs(dir, visited, 0);
        for(int i = 0; i < visited.length; i++){
            if(!visited[i]){
                System.out.println(-1);
                return;
            }
        }
        System.out.println(1);

        scanner.close();
    }

    private static void dfs(List<List<Integer>> dir, boolean[] visited, int start){
        if(visited[start]) return;
        visited[start] = true;
        for(int i : dir.get(start)){
            dfs(dir, visited, i);
        }

    }

    private static void bfs(List<List<Integer>> dir, boolean[] visited, int start){
        Deque<Integer> dequeue = new LinkedList<>();
        dequeue.offer(start);
        visited[start] = true;
        while(!dequeue.isEmpty()){
            int cur = dequeue.pollLast();
            for(int i : dir.get(cur)){
                if(!visited[i]){
                    dequeue.offer(i);
                    visited[i] = true;
                }
            }
        }
    }
    
}