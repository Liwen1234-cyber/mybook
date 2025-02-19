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

        // 从左边界和右边界向中间遍历
        for(int i = 0; i < N; i++){
            if(arr[i][0] == 1) dfs(i, 0, arr);
            if(arr[i][M-1] == 1) dfs(i, M-1, arr);
        }

        // 从上边界和下边界向中间遍历
        for(int j = 0; j < M; j++){ // 遍历上侧和下侧边界
            if(arr[0][j] == 1) dfs(0, j, arr);
            if(arr[N-1][j] == 1) dfs(N-1, j, arr);
        }

        count = 0;
        
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(arr[i][j] == 1) dfs(i, j, arr);
            }
        }

        System.out.println(count);
    }
    

    public static void dfs(int x, int y, int[][] arr){
        arr[x][y] = 0;
        count++;

        for(int i = 0; i < 4; i++){
            int newX = x + dir[i][0];
            int newY = y + dir[i][1]; 

            if(newX < 0 || newX >= arr.length || newY < 0 || newY >= arr[0].length)
                continue;

            if(arr[newX][newY] == 1){
                dfs(newX, newY, arr);
            }
        }
    }
}
