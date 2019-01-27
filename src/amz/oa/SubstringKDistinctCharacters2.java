package amz.oa;

/**
 Given a string of lowercase alphabets,
 count all possible substrings
 (not necessarily distinct) 注意重複也沒差
 that has exactly k distinct characters.
 Examples:

 Input: abc, k = 2
 Output: 2
 Possible substrings are {"ab", "bc"}

 Input: aba, k = 2
 Output: 3
 Possible substrings are {"ab", "ba", "aba"}

 Input: aa, k = 1
 Output: 3
 Possible substrings are {"a", "a", "aa"}
 */
public class SubstringKDistinctCharacters2 {
    public int sol(String s, int k) {
        if (s == null || s.length() == 0 || k < 1)
            return 0;
        int[] map = new int[128];
        int cnt = 0;
        final int n = s.length();
        int res = 0;
        for (int l = 0, r = 0; r < n; r++) {
            char rc = s.charAt(r);
            if (map[rc]++ == 0)
                cnt++;
            while (cnt == k) {
                res++;
                System.out.println("found ans:" + s.substring(l, r+1));
                if (--map[s.charAt(l++)] == 0)
                    cnt--;
            }
        }
        return res;
    }
    public static void main(String[] args) {
        SubstringKDistinctCharacters2 sol = new SubstringKDistinctCharacters2();
        System.out.println(sol.sol("abc", 2));
        System.out.println(sol.sol("aba", 2));
        System.out.println(sol.sol("aa", 1));
        System.out.println(sol.sol("abcabcabc", 3));
    }

}
