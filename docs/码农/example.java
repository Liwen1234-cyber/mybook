import java.util.Scanner;

public class Main{
    public static int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[][] grid = new int[N][M];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                grid[i][j] = scanner.nextInt();
            }
        } 

        
        for (int i = 0; i < N; i++){
            if(grid[i][0] == 1) dfs(grid, i, 0);
            if(grid[i][M-1] == 1) dfs(grid, i, M-1);
        }
        
        for (int j = 0; j < M; j++){
            if(grid[0][j] == 1) dfs(grid, 0, j);
            if(grid[N-1][j] == 1) dfs(grid, N-1, j);
        }

        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                if(grid[i][j] == 1) grid[i][j] = 0;
                else if(grid[i][j] == 2) grid[i][j] = 1;
            }
        }

        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();

    }
    
    private static void dfs(int[][] grid, int x, int y){

        grid[x][y] = 2;
        
        for(int i = 0; i < 4; i++){
            int next_x = x + dir[i][0];
            int next_y = y + dir[i][1];
            if(next_x >= 0 && next_x < grid.length && next_y >= 0 && next_y < grid[0].length && grid[next_x][next_y] == 1){
                dfs(grid, next_x, next_y);
            }
        }
    }
}