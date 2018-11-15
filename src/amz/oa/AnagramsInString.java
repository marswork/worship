
import java.util.*;

/**
 * Find All Anagrams in a String
 * <p>
 * Given a string s and a non-empty string p, find all the start indices of p's
 * anagrams in s.
 * <p>
 * Strings consists of lowercase English letters only and the length of both
 * strings s and p will not be larger than 20,100.
 * <p>
 * The order of output does not matter.
 * <p>
 * Example 1:
 * <p>
 * Input: s: "cbaebabacd" p: "abc"
 * <p>
 * Output: [0, 6]
 * <p>
 * Explanation: The substring with start index = 0 is "cba", which is an anagram
 * of "abc". The substring with start index = 6 is "bac", which is an anagram of
 * "abc". Example 2:
 * <p>
 * Input: s: "abab" p: "ab"
 * <p>
 * Output: [0, 1, 2]
 * <p>
 * Explanation: The substring with start index = 0 is "ab", which is an anagram
 * of "ab". The substring with start index = 1 is "ba", which is an anagram of
 * "ab". The substring with start index = 2 is "ab", which is an anagram of
 * "ab".
 */
public class AnagramsInString {

    public static List<Integer> findAnagrams(String s, String p) {
        if (s == null || p == null || s.length() < p.length()) {
            return Collections.emptyList();
        }

        List<Integer> result = new LinkedList<>();

        final int lenS = s.length(), lenP = p.length();
        Map<Character, Integer> countP = new HashMap<>();
        Map<Character, Integer> countWin = new HashMap<>();

        for (char c : p.toCharArray()) {
            countP.put(c, countP.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < lenS; ++i) {
            char c = s.charAt(i);
            countWin.put(c, countWin.getOrDefault(c, 0) + 1);
            if (i >= lenP) {
                c = s.charAt(i - lenP);
                int count = countWin.get(c);
                if (count <= 1) {
                    countWin.remove(c);
                } else {
                    countWin.put(c, count - 1);
                }
            }

            if (countP.equals(countWin)) {
                result.add(i - lenP + 1);
            }

        }

        return result;
    }

    public static void main(String[] args) {

        System.out.println(findAnagrams("cbaebabacd", "abc"));
        System.out.println(findAnagrams("abab", "ab"));
    }
}
