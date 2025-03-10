class Solution {
    public int[] searchRange(int[] nums, int target) {
        if(nums.length == 0 || nums == null) return new int[]{-1, -1};

        int left = 0, right = nums.length - 1;
        while(left <= right && nums[left] != nums[right]){
            if(nums[left] < target) left++;
            if(nums[right] > target) right--;
        }

        if(nums[left] == target && nums[right] == target) return new int[]{left, right};
        else return new int[]{-1, -1};
    }
}