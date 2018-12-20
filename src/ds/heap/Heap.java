package ds.heap;

import java.util.ArrayList;
import java.util.List;

public class Heap {
    public static void main(String[] args) {
        Heap h = new Heap();
        h.offer(1);
        h.offer(3);
        h.offer(4);
        h.offer(5);
        h.poll();
        h.poll();
        h.poll();
        h.poll();
        int[] nums = new int[]{3,1,5,4};
        Heap h2 = new Heap(nums);
        h2.offer(7);
        h2.poll();
        h2.poll();
        h2.poll();
        h2.poll();
        h2.poll();
    }
    private List<Integer> tree = new ArrayList<>();
    public Heap() {
    }
    public Heap(int[] nums) {
        for (int num : nums) {
            tree.add(num);
            siftDown();
        }
    }
    public void offer(int val) {
        tree.add(val);
        siftUp();
        System.out.println(tree);
    }
    public Integer peek() {
        if (tree.isEmpty())
            return null;
        return tree.get(0);
    }
    public Integer poll() {
        if (tree.isEmpty())
            return null;
        if (tree.size() == 1) {
            int ret = tree.get(0);
            tree.remove(0);
            return ret;
        }
        Integer ret = tree.get(0);
        swap(tree.size() - 1, 0);
        tree.remove(tree.size() - 1);
        siftDown();
        System.out.println(tree);
        return ret;
    }
    private void siftDown() {
        int idx = 0;
        int l = idx * 2 + 1;
        while (l < tree.size()) {
            int r = l + 1;
            int maxChild = l;
            if (r < tree.size() && tree.get(r) > tree.get(l)) {
                maxChild = r;
            }
            if (tree.get(idx) < tree.get(maxChild)) {
                swap(idx, maxChild);
                idx = maxChild;
                l = idx * 2 + 1;
            } else {
                break;
            }
        }
    }
    private void swap(int i, int j) {
        int temp = tree.get(i);
        tree.set(i, tree.get(j));
        tree.set(j, temp);
    }

    private void siftUp() {
        int idx = tree.size() - 1;
        while (idx > 0) {
            int p = (idx - 1) / 2;
            if (tree.get(idx) > tree.get(p)) {
                swap(idx, p);
                idx = p;
            } else {
                break;
            }
        }
    }
}
