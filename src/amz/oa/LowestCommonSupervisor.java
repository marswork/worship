
import java.util.ArrayList;
import java.util.List;

/**
 * 寻找公司员工的最低共同上司
 * <p>
 * 变化1:不是binary tree
 * 变化2:ceo不是manager 如果共同上司是ceo return null
 * 变化3:员工可能不在这个公司 return null
 *
 */
public class LowestCommonSupervisor {
    public static TreeNode lowestCommonSupervisor(TreeNode root,
                                                  Employee e1, Employee e2) {
        if (root == null || e1 == null || e2 == null) return null;
        TreeNode p = search(root, e1);
        if (p == null) return null;

        TreeNode q = search(root, e2);
        if (q == null) return null;

        TreeNode lca = lowestCommonSupervisor(root, p, q);
        if (lca == null) return lca;

        return lca.getVal().getTitle().equals("ceo") ? null : lca;
    }


    private static TreeNode lowestCommonSupervisor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) return null;
        if (root.getVal().equals(p.getVal()) || root.getVal().equals(q.getVal())) {
            return  root;
        }

        TreeNode lca = null;
        for (TreeNode child : root.getChildren()) {
            TreeNode temp = lowestCommonSupervisor(child, p, q);
            if (temp != null) {
                if (lca != null) {
                    return root;
                }
                lca = temp;
            }
        }
        return lca;
    }

    private static TreeNode search(TreeNode node, Employee e) {
        if (node == null || node.getVal().equals(e)) return node;

        for (TreeNode child : node.getChildren()) {
            TreeNode temp = search(child, e);
            if (temp != null && temp.getVal().equals(e)) {
                return temp;
            }
        }

        return null;
    }

    static class Employee {
        String name;
        String title;

        public Employee(String name, String title) {
            this.name = name;
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Employee)) return false;

            Employee employee = (Employee) o;

            if (name != null ? !name.equals(employee.name) : employee.name != null)
                return false;
            return title != null ? title.equals(employee.title) : employee.title == null;

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (title != null ? title.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "{\"Employee\":{"
                    + "\"name\":\"" + name + "\""
                    + ",\"title\":\"" + title + "\""
                    + "}}";
        }
    }

    static class TreeNode {
        Employee val;
        List<TreeNode> children;

        public TreeNode(Employee employee) {
            this.val = employee;
            children = new ArrayList<>();
        }

        public Employee getVal() {
            return val;
        }

        public List<TreeNode> getChildren() {
            return children;
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(new Employee("E1", "ceo"));

        root.children.add(new TreeNode(new Employee("E2", "manager")));
        root.children.add(new TreeNode(new Employee("E3", "engineer")));
        root.children.add(new TreeNode(new Employee("E4", "engineer")));

        TreeNode node0 = root.children.get(0);
        node0.children.add(new TreeNode(new Employee("E5", "engineer")));
        TreeNode p = node0.children.get(0);
        node0.children.add(new TreeNode(new Employee("E6", "engineer")));
        TreeNode q = node0.children.get(1);

        TreeNode node1 = root.children.get(1);
        node1.children.add(new TreeNode(new Employee("E7", "engineer")));

        TreeNode node2 = root.children.get(2);
        node2.children.add(new TreeNode(new Employee("E8", "engineer")));
        node2.children.add(new TreeNode(new Employee("E9", "engineer")));

        TreeNode supervisor = lowestCommonSupervisor(root, p.getVal(), q.getVal());
        if (supervisor != null) {
            System.out.println(supervisor.getVal());
        } else {
            System.out.println(supervisor);

        }

        supervisor = lowestCommonSupervisor(root, p.getVal(), new Employee("E88", "Engineer"));
        if (supervisor != null) {
            System.out.println(supervisor.getVal());
        } else {
            System.out.println(supervisor);
        }

        supervisor = lowestCommonSupervisor(root, p.getVal(), new Employee("E7", "engineer"));
        if (supervisor != null) {
            System.out.println( supervisor.getVal());
        } else {
            System.out.println(supervisor);
        }

        supervisor = lowestCommonSupervisor(root,
                new Employee("E8", "engineer"), new Employee("E9", "engineer"));
        if (supervisor != null) {
            System.out.println( supervisor.getVal());
        } else {
            System.out.println(supervisor);
        }
    }
}
