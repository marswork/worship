package amz;

/*
Missing Numbers in Interval
给一个无序 int array和一个interval，eg array: [5, 3, 1, 6, 8], interval: [2, 7],
 找出array里所有在interval里面但是却missing的int，对于input应该返回[2, 4, 7]
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissingNumberInInterval {
    public static void main(String[] args) {
        System.out.println(findMissing(new int[]{5,3,1,6,8}, new int[]{2,7}));
    }
    public static List<Integer> findMissing(int[] nums, int[] interval) {
        Arrays.sort(nums);
        int n = nums.length;
        int left = findLeft(nums, interval[0], 0, n-1);
        List<Integer> res = new ArrayList<>();
        for (int i = interval[0]; i <= interval[1]; i++) {
            if (nums[left] != i) {
                res.add(i);
            } else {
                left++;
            }
        }
        return res;
    }
    public static int findLeft(int[] nums, int target, int l, int r) {
        while (l < r) {
            int m = l + (r - l) / 2;
            if (nums[m] >= target) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }
}
