package ngordnet;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;

public class YearlyRecord {
    
    private TreeMap<String, Integer> record;
    private TreeMap<String, Integer> wordRanks;
    private TreeMap<String, Integer> ranks;
    private boolean cached = true;
    private int accountForMultiple = 0;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        record = new TreeMap<String, Integer>();
        wordRanks = new TreeMap<String, Integer>();
        ranks = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        record = new TreeMap<String, Integer>();
        wordRanks = new TreeMap<String, Integer>();
        ranks = new TreeMap<String, Integer>();
        for (String x : otherCountMap.keySet()) {
            this.put(x, otherCountMap.get(x));
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return record.get(word);
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        record.put(word, count);
        if (wordRanks.values().contains(count + accountForMultiple)) {
            accountForMultiple += 1;
        }
        wordRanks.put(word, count + accountForMultiple);
        // wordRanks.put(word, count);
        cached = false;
    }

    private void updateRanks() {
        // TreeMap<Integer, String> rankFirst = invert(record);
        TreeMap<Integer, String> rankFirst = invert(wordRanks);
        TreeMap<Integer, String> actualRanks = new TreeMap<Integer, String>();
        Integer i = 1;
        for (Integer count : rankFirst.keySet()) {
            actualRanks.put(i, rankFirst.get(count));
            i = i + 1;
            // System.out.println(actualRanks.size());
        }
        ranks = invert(actualRanks);
        cached = true;
    }

    /** Inverts the tree and also places all of the elements in the reverse
      * of the natural order of elements
      * (Note: Taken from my homework #5) */
    private <V, K> TreeMap<V, K> invert(Map<K, V> m) {
        TreeMap<V, K> returnMap = new TreeMap<V, K>(Collections.reverseOrder());
        for (K key : m.keySet()) {
            if (!returnMap.containsKey(m.get(key))) {
                returnMap.put(m.get(key), key);
            }
        }
        return returnMap;
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return record.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        TreeMap<Integer, String> ascending = new TreeMap<Integer, String>();
        for (String s : record.keySet()) {
            ascending.put(record.get(s), s);
        }
        Collection<String> sortedWords = ascending.values();
        return sortedWords;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        TreeMap<Integer, Number> ascendingCount = new TreeMap<Integer, Number>();
        for (Integer i : record.values()) {
            ascendingCount.put(i, i);
        }
        Collection<Number> sorted = ascendingCount.values();
        return sorted;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        if (!cached) {
            this.updateRanks();
        }
        return ranks.get(word);
    }
}
