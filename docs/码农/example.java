import java.util.*;

public class Main{
    public static int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    public static int count = 0;
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
        for(int i = 0; i < N; i++){ // 遍历左侧和右侧边界
            if(arr[i][0] == 1) bfs(i, 0, arr);
            if(arr[i][M-1] == 1) bfs(i, M-1, arr);
        }

        for(int j = 0; j < M; j++){ // 遍历上侧和下侧边界
            if(arr[0][j] == 1) bfs(0, j, arr);
            if(arr[N-1][j] == 1) bfs(N-1, j, arr);
        }

        count = 0;
        
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(arr[i][j] == 1) bfs(i, j, arr);
            }
        }

        System.out.println(count);
    }
    


    public static void bfs(int x, int y, int[][] arr){
        Queue<int[]> queue = new LinkedList<>();
        count++;
        queue.add(new int[]{x,y});
        arr[x][y] = 0;


        while(!queue.isEmpty()){
            int cur_x = queue.peek()[0];
            int cur_y = queue.poll()[1];
            for(int k = 0; k < 4; k++){
                int next_x = cur_x + dir[k][0];
                int next_y = cur_y + dir[k][1];
                if(next_x < 0 || next_x >= arr.length || next_y < 0 || next_y >= arr[0].length)
                    continue;
                if(arr[next_x][next_y] == 1){
                    count++;
                    queue.add(new int[]{next_x,next_y});
                    arr[next_x][next_y] = 0;
                }
            }
        }
    }
}
