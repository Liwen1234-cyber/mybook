class Solution {
    public boolean validMountainArray(int[] arr) {
        if (arr.length < 3) return false;
        int left = 0, right = arr.length - 1;

        while(left < right && arr[left] < arr[left+1]) left++;
        while(right > left && arr[right] < arr[right-1]) right--;

        return left == right && left != 0 && left!= arr.length - 1;
    }
}