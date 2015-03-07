package ngordnet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collection;

public class YearlyRecord {
    
    private TreeMap<String, Integer> record;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        record = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        record = new TreeMap<String, Integer>();
        for (String x : otherCountMap.keySet()) {
            record.put(x, otherCountMap.get(x));
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        int count = 0;
        for (String s : record.keySet()) {
            if (s.equals(word)) {
                count += record.get(s);
            }
        }
        return count;
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        record.put(word, count);
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return record.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        // Collection<String> ascendingWords = record.navigableKeySet();
        return null;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        return null;
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        return 0;
    }
} 