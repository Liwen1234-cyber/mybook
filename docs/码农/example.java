import java.util.*;

public class Main{
    public static int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        int[][] arr = new int[N][M];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                arr[i][j] = in.nextInt();
            }
        }

        in.close();
        int count = 0;
        boolean[][] visited = new boolean[N][M];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(!visited[i][j] && arr[i][j] == 1){
                    count++;
                    visited[i][j] = true;
                    dfs(visited, i, j, arr);
                }
            }
        }

        System.out.println(count);
    }


    public static void dfs(boolean[][] visited, int x, int y, int[][] arr){
        for(int k = 0; k < 4; k++){
            int next_x = x + dir[k][0];
            int next_y = y + dir[k][1];
            if(next_x < 0 || next_x >= arr.length || next_y < 0 || next_y >= arr[0].length)
                continue;
            if(!visited[next_x][next_y] && arr[next_x][next_y] == 1){
                visited[next_x][next_y] = true;
                dfs(visited, next_x, next_y, arr);
            }

        }
    }
}
