package lc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        System.out.println(s.areSentenceSimilar2(words1, words2, pairs));
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
    public boolean areSentenceSimilar2(String[] words1, String[] words2, String[][] pairs) {
        if (words1.length != words2.length)
            return false;
        int n = words1.length;
        Map<String, List<String>> graph = new HashMap<>();
        for (String[] pair : pairs) {
            graph.putIfAbsent(pair[0], new ArrayList<>());
            graph.putIfAbsent(pair[1], new ArrayList<>());
            graph.get(pair[0]).add(pair[1]);
            graph.get(pair[1]).add(pair[0]);
        }
        int color = 0;
        Map<String, Integer> colors = new HashMap<>();
        for (String[] pair : pairs) {
            if (!colors.containsKey(pair[0]))
                dfs(graph, pair[0], color++, colors);
        }
        for (int i = 0; i < n; i++) {
            String word1 = words1[i], word2 = words2[i];
            if (word1.equals(word2))
                continue;
            System.out.println("word1:" + word1 + " color:" + colors.get(word1));
            System.out.println("word2" + word2 + " color:" + colors.get(word2));
            if (!colors.containsKey(word1) || !colors.containsKey(word2) ||
                    !colors.get(word1).equals(colors.get(word2)))
                return false;
        }
        return true;
    }
    private void dfs(Map<String, List<String>> graph, String cur, int color, Map<String, Integer> colors) {
        colors.put(cur, color);
        for (String nei : graph.get(cur)) {
            if (!colors.containsKey(nei))
                dfs(graph, nei, color, colors);
        }
    }
}
