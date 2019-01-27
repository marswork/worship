package amz.oa;

import java.util.*;

/**
 * Find substring with K distinct characters (http://www.cnblogs.com/pegasus923/p/8444653.html)
 *
 * Given a string and number K, find the substrings of size K with K distinct characters. If no, output empty list.
 * Remember to remove the duplicate substrings, i.e. if the substring repeated twice, only output once.
 *
 * 注意這題，K <= 0 的話答案為 []
 */
public class SubstringKDistinctCharacters {
    public List<String> mySol(String s, int k) {
        if (s == null || s.length() == 0 || k < 1)
            return Collections.emptyList();
        int cnt = 0; //distinct character count
        int[] map = new int[256];
        final int n = s.length();
        Set<String> set = new HashSet<>();
        List<String> res = new ArrayList<>();
        for (int l = 0, r = 0; r < n; r++) {
            char rc = s.charAt(r);
            if (map[rc]++ == 0)
                cnt++;
            if (r - l + 1 == k) {
                if (cnt == k) {
                    String w = s.substring(l, r + 1);
                    if (set.add(w))
                        res.add(w);
                }
                if (--map[s.charAt(l++)] == 0)
                    cnt--;
            }
        }
        return res;
    }
    public Collection<String> findSubstringKDistinct(String s, int k) {
        if (s == null || k < 1) {
            return Collections.emptyList();
        }

        return find(s, k);
    }
    private Set<String> find(String s, int k) {
        Set<String> result = new HashSet<>();

        Map<Character, Integer> charCountMap = new HashMap<>();
        int remaining = k, left = 0;
        final int n = s.length();

        for (int right = 0; right < n; ++right) {
            char c = s.charAt(right);

            // Found a new distinct character
            if (!charCountMap.containsKey(c)) {
                remaining--;
            }

            // Increase count of this character
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);

            // Reach the window size
            if (right - left + 1 == k) {

                // Found a substring of size of k with k distinct chars
                if (remaining == 0) {
                    result.add(s.substring(left, left + k));
                }

                // Remove the left from the window
                // Decrease left char count
                c = s.charAt(left++);
                charCountMap.put(c, charCountMap.get(c) -1);

                // Remove left char from the map and adjust remaining if necessary
                if (charCountMap.get(c) == 0) {
                    remaining++;
                    charCountMap.remove(c);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        SubstringKDistinctCharacters sol = new SubstringKDistinctCharacters();
        System.out.println(sol.findSubstringKDistinct("abccdef", 2));
        System.out.println(sol.mySol("abccdef", 2));
        System.out.println(sol.findSubstringKDistinct("awaglknagawunagwkwagl", 4));
        System.out.println(sol.mySol("awaglknagawunagwkwagl", 4));
        System.out.println(sol.findSubstringKDistinct("", 1));
        System.out.println(sol.mySol("", 1));
        System.out.println(sol.findSubstringKDistinct("aaaaaaa", 2));
        System.out.println(sol.mySol("aaaaaaa", 2));
        System.out.println(sol.findSubstringKDistinct("aaaaaaa", 1));
        System.out.println(sol.mySol("aaaaaaa", 1));
        System.out.println(sol.findSubstringKDistinct(null, 1));
        System.out.println(sol.mySol(null, 1));
        System.out.println(sol.findSubstringKDistinct(null, -1));
        System.out.println(sol.mySol(null, -1));
        System.out.println(sol.findSubstringKDistinct("aaaabbb", 0));
        System.out.println(sol.mySol("aaaabbb", 0));
    }
}
