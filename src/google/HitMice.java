package google;

/*
https://www.1point3acres.com/bbs/thread-450286-1-1.html
“玩过打地鼠么”
“一维数组表示一排地洞，1表示有地鼠，0表示没地鼠，有一个锤子宽度
是N, 最多能打几个地鼠。”
-算法+时间复杂度+能优化么
“现在有2个锤子，能打几个地鼠”
-算法+时间复杂度+写代码+跑一个用例

两个锤子可以做到O(N)，用一维memoization，左往右扫一遍记下所有
0~i 范围能打的最多地鼠，右往左扫一遍记下所有 i~n范围能打的最多
地鼠，在扫一遍求max，max = max(currentMax, 这个锤子+左锤子，
 这个锤子+右锤子)，把“这个锤子”在数组里扫一遍就可以.

补充内容 (2018-10-30 21:53):
我的做法还是偏复杂了，其实这题的主要思路就是Buy and Sell Stock
 Twice
 */
public class HitMice {
    public int maxHitMice(int[] ground, int hammerSize) {
        if (ground == null || ground.length == 0)
            return 0;
        int cnt = 0;
        for (int i = 0; i < hammerSize; i++) {
            if (ground[i] == 1)
                cnt++;
        }
        int max = 0;
        for (int l = 0, r = hammerSize - 1; r < ground.length; l++, r++) {
            //current window
            if (max < cnt) max = cnt;
            //next window
            if (ground[l] == 1)
                cnt -= 1;
            if (r+1 < ground.length && ground[r+1] == 1)
                cnt += 1;
        }
        return max;
    }
    /*
    follow up:
    | 0, 0, 0, 1, 1, 1, 1 |
      0  0  0  1  1  2  3 3
               3  3  2  1 0
     */
}
