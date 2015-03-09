package ngordnet;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collection;
import java.util.Collections;

public class YearlyRecord {
    
    private TreeMap<String, Integer> record;
    // private TreeMap<String, Integer> wordRanks;
    private TreeMap<String, Integer> ranks;
    private boolean cached = true;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        record = new TreeMap<String, Integer>();
        // wordRanks = new TreeMap<String, Integer>();
        ranks = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        record = new TreeMap<String, Integer>();
        // wordRanks = new TreeMap<String, Integer>();
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
        // wordRanks.put(word, count);
        cached = false;
    }

    /** Updates the rankings after put has been called. */
    private void updateRanks() {
        // ArrayList<String> words = new ArrayList<String>(record.keySet());

        

        // Can't use triple for loop

        // ArrayList<String> ranked = new ArrayList<String>(ranks.keySet());
        // for (String word : record.keySet()) {
        //     int currentCount = record.get(word);
        //     if (!ranked.contains(word)) {
        //         for (Integer i : record.values()) {
        //             if (i < currentCount) {
        //                 ranked.add(i, word);
        //             }
        //         }
        //     }
        // }


        // ArrayList<String> words = new ArrayList<String>(record.keySet());
        // for (String s : words) {
        //     if (record.get(s) > )
        // }

        // ArrayList<String> words = new ArrayList<String>(record.keySet());
        // ArrayList<String> sortedWords = new ArrayList<String>();
        // for (String s : words) {
        //     for (Integer i : record.values()) {
        //         if (record.get(s) > i) {
        //             sortedWords.add(i, s);
        //         }
        //     }
        // }
        // String pastCount = words.get(0);
        // // Collection<Number> = this.counts();

        // for (String eachWord : words) {
        //     Integer currentCount = record.get(eachWord);
        //     for (Integer i : record.values()) {
        //         if (currentCount > i) {

        //         }
        //     }
        //     Integer currentCount = record.get(eachWord);
        //     if ()
        //     Integer currentRank = ranks.get(eachWord);
        // }

        Set<Map.Entry<String, Integer>> mapEntries = record.entrySet();
        List<Map.Entry<String, Integer>> rankEntries
            = new ArrayList<Map.Entry<String, Integer>>(mapEntries);
        // sort them with highest counts first

        // Collections.sort(rankEntries);
        Comparator<Map.Entry<String, Integer>> comparor
            = new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> entry1,
                                    Map.Entry<String, Integer> entry2) {
                    return (entry1.getValue().compareTo(entry2.getValue()));
                }
            };
        Collections.sort(rankEntries, comparor);
        Collections.reverse(rankEntries);
        ranks = new TreeMap<String, Integer>();
        int x = 1;
        Iterator<Map.Entry<String, Integer>> listItems = rankEntries.iterator();
        while (listItems.hasNext()) {
            Map.Entry<String, Integer> current = listItems.next();
            // ranks.put(current.getKey(), current.getValue());
            ranks.put(current.getKey(), x);
            x += 1;
        }

        // for (Integer i : record.)

        // // Collection<Integer> x = record.values();
        // ArrayList<Integer> orderedCounts = new ArrayList<Integer>();
        // orderedCounts.addAll(record.values());
        // Collections.reverse(Collections.sort(orderedCounts));
        // int x = 1;
        // int addedCount = 0;
        // for (int i = 0; i < orderedCounts.size(); i++) {
        //     int current = orderedCounts.get(i);
        //     if ()
        //     if (orderedCounts.contains)

        //     x += 1;
        //     // if (orderedCounts.contains(i)) {
        //     //     // addedCount += 1;
        //     //     for (int j = i; j < record.values().size(); j++) {

        //     //     }
        //     // }
        //     // ranks.put()
        //     // x += 1;
        // }

        // ArrayList<Integer> counts = new ArrayList<Integer>();
        // int addedCount = 0;
        // for (Integer i : record.values()) {
        //     if (counts.contains(i)) {
        //         addedCount += 1;
        //     }
        //     counts.add(i + addedCount);
        // }


        // TreeMap<Integer, String> rankFirst = invert(record);
        // TreeMap<Integer, String> actualRanks = new TreeMap<Integer, String>();
        // Integer i = 1;
        // for (Integer count : rankFirst.keySet()) {
        //     actualRanks.put(i, rankFirst.get(count));
        //     i = i + 1;
        // }
        // ranks = invert(actualRanks);
        cached = true;
    }

    /** Inverts the tree and also places all of the elements in the reverse
      * of the natural order of elements
      * (Note: Taken from my homework #5) */
    private <V, K> TreeMap<V, K> invert(TreeMap<K, V> m) {
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
