/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int rob(TreeNode root) {
        if (root == null)  return 0;
        int[] res = robHelper(root);
        return Math.max(res[0], res[1]);
    }
    private int[] robHelper(TreeNode root){
        int[] res = new int[2];
        if(root == null)  return res;
        int mid = root.val;
        int[] left_res = robHelper(root.left);
        int[] right_res = robHelper(root.right);
        res[0] = Math.max(left_res[0] ,left_res[1]) + Math.max(right_res[0], right_res[1]); // 不偷当前节点
        res[1] = mid + left_res[0] + right_res[0]; // 偷当前节点
        return res;
    }
}