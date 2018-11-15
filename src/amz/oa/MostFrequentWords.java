import java.util.*;

/**
 * 给一个string，是一篇小说内容，比如"Jimmy has an apple, it is on the table",
 * 再给一个exclude word list，比如 "an" "a" "is" "the"，让你给出这个string里出现频率最高的单词.
 * Characters except English letter are treated as whitespaces
 */
public class MostFrequentWords {
    public List<String> mostFrequentWords(String text, List<String> exclusiveWords) {

        // Hold words having the most occurrences
        List<String> result = new ArrayList<>();

        // Step 1 Build hash map. key-value : word-frequency
        Map<String, Integer> wordFreqMap = makeWordFreq(text, exclusiveWords);

        // Step 2. Find the larges value of the frequencies
        int mostOccurs = 0;
        for (String key : wordFreqMap.keySet()) {
            mostOccurs = Math.max(mostOccurs, wordFreqMap.get(key));
        }

        // Step 3. Find all the words having the largest occurrences
        for (String key : wordFreqMap.keySet()) {
            if (wordFreqMap.get(key) == mostOccurs) {
                result.add(key); // found such a word and save to result
            }
        }

        return result;
    }

    private Map<String, Integer> makeWordFreq(String text, List<String> exclusiveWords) {

        // Step 1. Build exclusive words set
        Set<String> exclusiveWordSet = new HashSet<>(exclusiveWords);

        // Step 2. tokenize the text into words
        // Note that all non-english letters are treated as white spaces
        String regex = "[^a-zA-Z'\\-]+";
        String[] words = text.split(regex);
//
//        for (String w : words) {
//            System.out.printf("[%s]\n", w);
//        }

        // Step 3. Count valid words
        Map<String, Integer> wordFreqMap = new HashMap<>();

        for (String word : words) {
            // Only valid words and not in exclusive word list
            if (word != null && !word.isEmpty() && !exclusiveWordSet.contains(word)) {
                wordFreqMap.put(word, wordFreqMap.getOrDefault(word, 0) + 1);
            }
        }

        return wordFreqMap;
    }

    public static void main(String[] args) {
        MostFrequentWords sol = new MostFrequentWords();
        String text = "Jimmy has an apple, it is on the table! There has additional 1 apple.  There are 2 oranges!";
        List<String> ex = Arrays.asList(new String[]{"an", "a", "is", "the"});

        List<String> words = sol.mostFrequentWords(text, ex);

        System.out.print("results: " + words);

    }
}
