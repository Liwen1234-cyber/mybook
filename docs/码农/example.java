import java.util.*;

public class Main{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int M = in.nextInt();
        int[][] arr = new int[N+2][M+2];
        for(int i = 1; i <= N; i++){
            for(int j = 1; j <= M; j++){
                arr[i][j] = in.nextInt();
            }
        }

    }


    private static void dfs(int[][] arr){
        
    }
}