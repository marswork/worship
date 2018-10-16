package amz.kNearestPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/*
 find k nearest point
 */
public class Main {
    public static void main(String[] args) {
        List<List<Integer>> points = new ArrayList<>();
        points.add(Arrays.asList(3, 4));
        points.add(Arrays.asList(-3, 4));
        points.add(Arrays.asList(2, 1));
        points.add(Arrays.asList(-1, 5));
        points.add(Arrays.asList(7, 1));
        points.add(Arrays.asList(7, 13));
        points.add(Arrays.asList(4, 4));
        points.add(Arrays.asList(-4, -2));
        printDistance(points);
        System.out.println(find(points,5));
    }
    public static void printDistance(List<List<Integer>> points) {
        for (List<Integer> point : points) {
            int x = point.get(0);
            int y = point.get(1);
            System.out.println(point + " : " + (x*x + y*y));
        }
    }

    public static List<List<Integer>> find(List<List<Integer>> points, int k) {
        if (points == null || points.size() == 0)
            return Collections.emptyList();
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>(
            (List<Integer> p1, List<Integer> p2) ->
             p1.get(0)*p1.get(0) + p1.get(1)*p1.get(1) - p2.get(0)*p2.get(0) - p2.get(1)*p2.get(1)
        );
        for (List<Integer> point : points) {
            pq.offer(point);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            res.add(pq.poll());
        }
        return res;
    }
}

