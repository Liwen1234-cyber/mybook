class Solution {
    public int largestRectangleArea(int[] heights) {
        int res = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < heights.length; i++) {
            while (!deque.isEmpty() && heights[deque.peek()] > heights[i]) {
                int cur = deque.pop();
                if(!deque.isEmpty()){
                    int max_hight = Math.max(heights[deque.peek()], heights[i]);
                    int min_hight = Math.max(heights[deque.peek()], heights[i]);
                }
                int left = deque.isEmpty() ? -1 : deque.peek();
                res = Math.max(res, (i - left - 1) * heights[cur]);
            }
            deque.push(i);
        }
        return res;
    }
}