package lc;

import ds.tree.TreeNode;
import util.TreeSerializer;

/*
Given a binary tree, find the largest subtree which is a
Binary Search Tree (BST), where largest means subtree with
largest number of nodes in it.
Note:
A subtree must include all of its descendants.
Here's an example:

   10
   / \
  5  15        =>     5
 / \   \             / \
1   8   7           1   8
The return value is the subtree's size, which is 3.*/
public class LargestBSTSubtree {
    public static void main(String[] args) {
        TreeNode root = TreeSerializer.deserialize(
                "10,5,1,#,#,8,#,#,15,#,7,#,#"
        );
        System.out.println(root);
        LargestBSTSubtree sol = new LargestBSTSubtree();
        System.out.println("largest BST size:" + sol.largestBST(root));
        root = TreeSerializer.deserialize(
                "50,30,20,#,#,100,#,#,60,55,#,#,#"
        );
        System.out.println(root);
        System.out.println("largest BST size:" + sol.largestBST(root));
    }
    public int largestBST(TreeNode root) {
        return helper(root)[1];
    }
    private int[] helper(TreeNode cur) {
        if (cur == null) {
            return new int[]{1, 0, Integer.MIN_VALUE, Integer.MAX_VALUE};
        }
        int[] left = helper(cur.left);
        int[] right = helper(cur.right);
        int min = cur.left == null ? cur.val : left[2];
        int max = cur.right == null ? cur.val : right[3];
        if (left[0] == 1 && right[0] == 1) {
            if ((cur.left == null || cur.val > left[3])
                    && (cur.right == null || cur.val < right[2])) {
                return new int[]{1, left[1]+right[1]+1, min, max};
            }
        }
        return new int[]{0, Math.max(left[1], right[1]), min, max};
    }
}
