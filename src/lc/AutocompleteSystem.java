package lc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import javafx.util.Pair;

public class AutocompleteSystem {
    public static void main(String[] args) {
        String[] sentences = {"i love you", "island", "ironman", "i love leetcode"};
        int[] times = {1, 1, 1, 1};
        AutocompleteSystem as = new AutocompleteSystem(sentences, times);
        System.out.println(as.input('i'));
        System.out.println(as.input(' '));
        System.out.println(as.input('a'));
        System.out.println(as.input('#'));
        System.out.println(as.input('i'));
        System.out.println(as.input(' '));
        System.out.println(as.input('a'));
        System.out.println(as.input('#'));
        System.out.println(as.input('i'));
        System.out.println(as.input(' '));
        System.out.println(as.input('l'));
        System.out.println(as.input('o'));
        System.out.println(as.input('v'));
        System.out.println(as.input('e'));
        System.out.println(as.input(' '));
        System.out.println(as.input('l'));
        System.out.println(as.input('x'));
    }
    public AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            insert(root, sentences[i], times[i]);
        }
    }
    private StringBuilder sb = new StringBuilder();
    public List<String> input(char c) {
        System.out.println("input: " + c);
        List<String> res = new LinkedList<>();
        if (c == '#') {
            insert(root, sb.toString(), 1);
            sb.setLength(0);
        } else {
            TrieNode cur = root;
            sb.append(c);
            for (int i = 0; i < sb.length(); i++) {
                char sbc =  sb.charAt(i);
                int idx = sbc == ' ' ? 26 : sbc - 'a';
                if (cur.next[idx] == null)
                    return Collections.emptyList();
                cur = cur.next[idx];
            }
            PriorityQueue<Pair<String, Integer>> pq = new PriorityQueue<>(
                    (s1, s2) -> s1.getValue() - s2.getValue()
            );
            dfs(cur, pq);
            while (!pq.isEmpty()) {
                res.add(0, pq.poll().getKey());
            }
        }
        return res;
    }
    private void dfs(TrieNode cur, PriorityQueue<Pair<String, Integer>> pq) {
        if (cur.times > 0) {
            if (pq.size() == 3 && pq.peek().getValue() < cur.times) {
                pq.poll();
                pq.offer(new Pair<>(sb.toString(), cur.times));
            } else if (pq.size() < 3)
                pq.offer(new Pair<>(sb.toString(), cur.times));
        }
        for (char c = 'a'; c <= 'z'; c++) {
            int idx = c - 'a';
            if (cur.next[idx] != null) {
                sb.append(c);
                dfs(cur.next[idx], pq);
                sb.setLength(sb.length() - 1);
            }
        }
        if (cur.next[26] != null) {
            sb.append(' ');
            dfs(cur.next[26], pq);
            sb.setLength(sb.length() - 1);
        }
    }
    private TrieNode root = new TrieNode();
    public class TrieNode {
        public int times = 0;
        public TrieNode[] next = new TrieNode[27];
    }
    private void insert(TrieNode cur, String sentence, int times) {
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            int idx = c == ' ' ? 26 : c - 'a';
            if (cur.next[idx] == null)
                cur.next[idx] = new TrieNode();
            cur = cur.next[idx];
        }
        cur.times += times;
    }
}
