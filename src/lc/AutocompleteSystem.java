package lc;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import javafx.util.Pair;

/**
 * LC 642 Design Search Autocomplete System
 *
 * 在 TrieNode 上紀錄這個點為終點的 sentence times 是多少
 *
 * insert:
 * 就照一般方式來做，不同在於結尾是塞 times
 *
 * input:
 * 使用一個 TrieNode cur 來記錄目前走到哪個 TrieNode
 * 遇到 "#" 就在當前 cur.times++
 * 其他則做 dfs，找這個 node 以下所有的 path，
 * 只要 times > 0 的 node 就代表有被搜尋過，這些會使用 Top K 算法 (min heap O(NlogK))
 *
 * 跟官方詳解比較起來：
 * 優化的部分在於 input 時
 * 1. 用 TrieNode cur，就不用每次都重新再從開頭重新搜一遍
 * 2. 用 StringBuilder 不用建一堆 String
 *
 * 如何再優化?
 * 1. 把每個 node 的 top K string 存進 node，但這會很耗 space
 * 2. 把每個 node 的 top K 最後一個 TrieNode 存進 node，每個 TrieNode 要多加一個 prev 已便還原原本的 sentence
 *
 * 優化方法如何 update 新的 times 到所有關聯的 node?
 * 每個 node 做 postorder traversal，每條路都回傳一個 List<Pair<TrieNode, Integer>>
 * 比較當前這個 node 所記錄的 top 跟回傳的 List 看看
 * 1. 有沒有人原本就在 top3 而 times 值需要更新
 * 2. 有沒有人原本沒在 top3，但打敗前面的人成為新的 top3
 *
 * 時間複雜度：
 * 原本官方的解法是
 * constructor: O(k * l) l sentences each of avg length k
 * input: O(p + q + mlogm)
 * p: length of sentence formed till now
 * q: number of nodes in the trie considering the sentence formed till now (DFS)
 * m: need to sort the list of length m sentences(假設 DFS 中有 m 個 sentences) to find top3
 *
 * my version:
 * constructor: O(k * l)
 * input: O(1 + q + mlog3)
 * 1: 因為 sentence formed till now 是跟著 StringBuilder 跑的，不用再從頭走一遍
 * q: same
 * mlog3: 使用 top K 算法
 */

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
        cur = root;
    }
    private TrieNode cur;
    private StringBuilder sb = new StringBuilder();
    public List<String> input(char c) {
        System.out.println("input: " + c);
        List<String> res = new LinkedList<>();
        if (c == '#') {
            cur.times++;
            sb.setLength(0);
            cur = root;
        } else {
            sb.append(c);
            int idx = c == ' ' ? 26 : c - 'a';
            if (cur.next[idx] == null) {
                cur.next[idx] = new TrieNode();
                cur = cur.next[idx];
                return Collections.emptyList();
            }
            cur = cur.next[idx];
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
