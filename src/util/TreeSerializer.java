package util;

import ds.tree.TreeNode;

public class TreeSerializer {
    public static final String NULL_STR = "#";
    public static final String DELIM = ",";
    private static int index = 0;
    public static TreeNode deserialize(String s) {
        index = 0;
        String[] strs = s.split(DELIM);
        return deserializeHelper(strs);
    }
    public static TreeNode deserializeHelper(String[] strs) {
        String s = strs[index++];
        if (NULL_STR.equals(s)) {
            return null;
        }
        TreeNode n = new TreeNode(Integer.parseInt(s));
        n.left = deserializeHelper(strs);
        n.right = deserializeHelper(strs);
        return n;
    }
    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    private static void serializeHelper(TreeNode cur, StringBuilder sb) {
        if (cur == null) {
            sb.append(NULL_STR).append(",");
            return;
        }
        sb.append(cur.val).append(DELIM);
        serializeHelper(cur.left, sb);
        serializeHelper(cur.right, sb);
    }
    public static void main(String[] args) {
        String s = "1,2,4,#,#,5,#,#,3,#,#";
        TreeNode root = TreeSerializer.deserialize(s);
        System.out.println(root);
    }
}
