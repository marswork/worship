package amz.findGcd;
/*
 find gcd in nums array
 */
public class Main {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int[] nums = {221, 14443, 728, 72358};
        System.out.println(getGCD(nums));
    }

    public static int getGCD(int[] nums){
        if (nums == null || nums.length <= 1)
            return 0;
        int res = getGCD(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++)
            res = getGCD(res, nums[i]);
        return res;
    }
    public static int getGCD(int a, int b) {
        while (b > 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
}
