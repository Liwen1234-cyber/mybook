class Solution {
    public int trap(int[] height) {
        if(height.length < 3) return 0;
        int res = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < height.length; i++) {
            while (!deque.isEmpty() && height[i] > height[deque.peek()]) {
                int bottom = deque.pop();
                int hold = 0;
                if (!deque.isEmpty()) {
                    int width = i - deque.peek() - 1;// 宽度
                    hold = (Math.min(height[deque.peek()], height[i]) - height[bottom]) * width;
                }
                res += hold;
            }
            deque.push(i);
        }
        return res;
    }
}