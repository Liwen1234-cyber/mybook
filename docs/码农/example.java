import java.util.*;

class Edge{
    int a1, a2, b1, b2;

    Edge(int a1, int a2, int b1, int b2){
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
    }
}

class Knight{
    int x, y, g, h; // g: 当前步数, h: 估计剩余步数
    Knight(int x, int y, int g, int h){
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
    }
}

class MyComparator implements Comparator<Knight>{
    @Override
    public int compare(Knight o1, Knight o2) {
        return ((o1.g + o1.h) - (o2.g + o2.h));
    }

}

public class Main{
    public static int[][] visited = new int[1001][1001];
    public static int[][] dir = {{-2,-1},{-1,-2},{1,-2},{2,-1},{-2,1},{-1,2},{1,2},{2,1}};
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        
        int N = scanner.nextInt();

        List<Edge> graph = new ArrayList<>();

        for(int i = 1; i <= N; i++){
            int a1 = scanner.nextInt();
            int a2 = scanner.nextInt();
            int b1 = scanner.nextInt();
            int b2 = scanner.nextInt();
            graph.add(new Edge(a1, a2, b1, b2));
        }

        for(Edge e : graph){
            astar(e);
        }
        
        scanner.close();
    }

    private static void astar(Edge e){
        PriorityQueue<Knight> pq = new PriorityQueue<>(new MyComparator());
        pq.offer(new Knight(e.a1, e.a2, 0, heuristic(e.a1, e.a2, e.b1, e.b2)));
        for(int[] arr : visited) Arrays.fill(arr, 0);
        visited[e.a1][e.a2] = 1;

        while(!pq.isEmpty()){
            Knight cur = pq.poll();
            int curX = cur.x, curY = cur.y, curG = cur.g;

            if(curX == e.b1 && curY == e.b2){
                System.out.println(visited[curX][curY]-1);
                return;
            }

            for(int i = 0; i < 8; i++){
                int nextX = curX + dir[i][0];
                int nextY = curY + dir[i][1];
                if(nextX >= 1 && nextX <= 1000 && nextY >= 1 && nextY <= 1000 && visited[nextX][nextY] == 0){
                    pq.offer(new Knight(nextX, nextY, curG + 5, heuristic(nextX, nextY, e.b1, e.b2)));
                    visited[nextX][nextY] = visited[curX][curY] + 1;
                }

            }
        }
    }

    private static int heuristic(int x1, int y1, int x2, int y2) {
        return (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2);
    }
}
