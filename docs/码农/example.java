class Solution {

    public int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    public int[][] highestPeak(int[][] isWater) {
        if(isWater == null) return null;
        int m = isWater.length, n = isWater[0].length;
        int[][] res = new int[m][n];

        Deque<int[]> deque = new LinkedList<>();

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(isWater[i][j] == 1){
                    deque.offer(new int[]{i,j});
                }
            }
        }

        while(!deque.isEmpty()){
            int[] cur = deque.poll();
            int x = cur[0], y = cur[1];

            for(int[] d : dir){
                int next_x = x + d[0], next_y = y + d[1];
                if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && isWater[next_x][next_y] == 0){
                    isWater[next_x][next_y] = 1;
                    res[next_x][next_y] = res[x][y] + 1;
                    deque.offer(new int[]{next_x, next_y});
                }
            }
        }

        return res;
    }
}
