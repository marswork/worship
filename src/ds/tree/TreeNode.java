package ds.tree;

import util.TreeSerializer;

public class TreeNode {
    public int val;
    public TreeNode left = null;
    public TreeNode right = null;
    public TreeNode(int val) {
        this.val = val;
    }
    public String toString() {
        return TreeSerializer.serialize(this);
    }
}
