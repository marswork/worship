
import java.util.ArrayList;
import java.util.List;

/**
 * 昨天做了Amazon的OA2，和面经里都不同。。。感觉是新的.
 * 第二题，就是树的结构，每个点可以有好多个儿子，用列表表示。除了叶子节点，求每个节点以及其所有后代的value平均值，返回最大的平均值.
 *
 */
public class MaxAverageOfTree {

    public double maxAverage(TreeNode root) {
        Context context = findMaxAverage(root);
        return context.getMaxAverage();
    }

    private Context findMaxAverage(TreeNode root) {
        if (root == null) return new Context(0, 0, Double.MIN_VALUE);

        // Set leaf node max average to min value to exclude it from calculating of max average
        if (root.leaf()) {
            return new Context(root.getVal(), 1, Double.MIN_VALUE);
        }

        int count = 1;
        int sum = root.getVal();
        double maxAverage = Double.MIN_VALUE;

        for (TreeNode child : root.children) {
            Context context = findMaxAverage(child);
            count += context.getCount();
            sum += context.getSum();
            maxAverage = Math.max(maxAverage, context.getMaxAverage());
        }

        double average = sum / ((double) count);
        System.out.printf("sum = %d, count = %d, avg = %.2f\n", sum, count, average);
        maxAverage = Math.max(maxAverage, average);

        return new Context(sum, count, maxAverage);
    }


    static class TreeNode {
        int val;
         List<TreeNode> children;

        public TreeNode(int val) {
            this.val = val;
            children = new ArrayList<>();
        }

        public int getVal() {
            return val;
        }

        public int getCount() {
            return children.size();
        }

        public boolean leaf() {
            return children.isEmpty();
        }
    }

    class Context {
        int sum;
        int count;
        double maxAverage;


        public Context(int sum, int count, double maxAverage) {
            this.sum = sum;
            this.count = count;
            this.maxAverage = maxAverage;
        }

        public double getMaxAverage() {
            return maxAverage;
        }

        public int getSum() {
            return sum;
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) {
        MaxAverageOfTree sol = new MaxAverageOfTree();

        TreeNode root = new TreeNode(10);
        System.out.println(sol.maxAverage(root));

        root.children.add(new TreeNode(20));
        root.children.add(new TreeNode(30)); // root.child[1]
        root.children.add(new TreeNode(25)); // child 2
        root.children.add(new TreeNode(-30));
        System.out.println(sol.maxAverage(root));

        TreeNode child1 = root.children.get(1); // // root.child[1]: 30
        child1.children.add(new TreeNode(30));
        child1.children.add(new TreeNode(20));
        child1.children.add(new TreeNode(10));
        child1.children.add(new TreeNode(-10));
        System.out.println(sol.maxAverage(root));


        TreeNode child2 = root.children.get(2); // // root.child[1]: 25
        child2.children.add(new TreeNode(30));
        child2.children.add(new TreeNode(50));
        System.out.println(sol.maxAverage(root));

    }
}
