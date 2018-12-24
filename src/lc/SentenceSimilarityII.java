package lc;

import java.util.HashMap;
import java.util.Map;

public class SentenceSimilarityII {
    public static void main(String[] args) {
        String[] words1 = {"great", "acting", "skills"};
        String[] words2 = {"fine", "drama", "talents"};
        String[][] pairs = {
                {"great", "good"},
                {"fine", "good"},
                {"acting", "drama"},
                {"skills", "talents"}
        };
        SentenceSimilarityII s = new SentenceSimilarityII();
        System.out.println(s.areSentenceSimilar(words1, words2, pairs));
    }
    public boolean areSentenceSimilar(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length)
            return false;
        Map<String, String> parents = new HashMap<>();
        int n = words1.length;
        for (String[] pair : pairs) {
            String a = find(pair[0], parents);
            String b = find(pair[1], parents);
            if (!a.equals(b)) parents.put(a, b);
        }
        for (int i = 0; i < n; i++) {
            String word1 = words1[i], word2 = words2[i];
            if (word1.equals(word2))
                continue;
            if (!find(word1, parents).equals(find(word2, parents)))
                return false;
        }
        return true;
    }
    private String find(String s, Map<String, String> parents) {
        if (!parents.containsKey(s))
            parents.put(s, s);
        else if (!parents.get(s).equals(s)) {
            parents.put(s, find(parents.get(s), parents));
        }
        return parents.get(s);
    }
}
