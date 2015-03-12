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
    private TreeMap<String, Integer> ranks;
    /** Holds the words sorted by the counts (for words) */
    private ArrayList<String> sortedWords = new ArrayList<String>();
    /** Holds the counts sorted by the counts (for counts) */
    private ArrayList<Integer> sortedCounts = new ArrayList<Integer>();
    private boolean cached = true;
    // private boolean countsRank = true;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        record = new TreeMap<String, Integer>();
        ranks = new TreeMap<String, Integer>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        record = new TreeMap<String, Integer>();
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
        sortedCounts.add(count);
        sortedWords.add(word);
        cached = false;
        // countsRank = false;
        // record.put(word, count);
        // cached = false;
    }

    /** Updates the rankings after put has been called.
      * I decided to use the comparators after trying many different strategies
      * for finding / sorting the ranks. I took a lot of the ideas from my hw5,
      * but figured out the strategy when I looked into the entrySet method of 
      * the treemap class. 
      *
      * Note: I also took some level of inspiration for my code from the website
      * below. However, I did not actually proceed to find this website until
      * after I had written most of my code for this method. Also, the only part
      * of the code that I explicitly looked at and manipulated from this website
      * was the part about creating the Comparator object, because I was unsure of
      * how to add this without implementing the interface. All of the other code
      * (including using the iterator object) were written by me after looking over
      * homework 5 and the lecture slides.
      *
      * Citation: http://www.mkyong.com/java/how-to-sort-a-map-in-java/
      */
    private void updateRanks() {
        // Set<Map.Entry<String, Integer>> mapEntries = record.entrySet();
        // List<Map.Entry<String, Integer>> rankEntries
        //     = new ArrayList<Map.Entry<String, Integer>>(mapEntries);
        // // sort them with highest counts first
        // // I used a comparator object, because I was unable to use the sort
        // // method built-in to the file.  
        // Comparator<Map.Entry<String, Integer>> comparor
        //     = new Comparator<Map.Entry<String, Integer>>() {
        //         public int compare(Map.Entry<String, Integer> entry1,
        //                             Map.Entry<String, Integer> entry2) {
        //             return (entry1.getValue().compareTo(entry2.getValue()));
        //         }
        //     };
        // Collections.sort(rankEntries, comparor);
        // Collections.reverse(rankEntries);
        // ranks = new TreeMap<String, Integer>();
        // int x = 1;
        // Iterator<Map.Entry<String, Integer>> listItems = rankEntries.iterator();
        // while (listItems.hasNext()) {
        //     Map.Entry<String, Integer> current = listItems.next();
        //     ranks.put(current.getKey(), x);
        //     x += 1;
        // }
        // cached = true;

        Set<Map.Entry<String, Integer>> mapEntries = record.entrySet();
        List<Map.Entry<String, Integer>> rankEntries
            = new ArrayList<Map.Entry<String, Integer>>(mapEntries);
        // sort them with highest counts first
        // I used a comparator object, because I was unable to use the sort
        // method built-in to the file.  
        Comparator<Map.Entry<String, Integer>> comparor
            = new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Map.Entry<String, Integer> entry1,
                                    Map.Entry<String, Integer> entry2) {
                    return (entry1.getValue().compareTo(entry2.getValue()));
                }
            };
        Collections.sort(rankEntries, comparor);
        Collections.reverse(rankEntries);

        // Rewrote code from my original words() to improve efficiency
        sortedCounts = new ArrayList<Integer>(record.values());
        Collections.sort(sortedCounts);
        sortedWords = new ArrayList<String>();
        for (Integer i : sortedCounts) {
            for (String s : record.keySet()) {
                if (record.get(s) == i) {
                    sortedWords.add(s);
                }
            }
        }
        System.out.println(sortedCounts);
        System.out.println(sortedWords);

        // sortedCounts = new ArrayList<Integer>();
        // sortedWords = new ArrayList<String>();
        
        // Sorts the counts in ascending order
        // Collections.sort(sortedCounts);
        // sortedWords = new ArrayList<String>();
        // for (Integer i : sortedCounts) {
        //     System.out.println(i);
        //     for (String s : record.keySet()) {
        //         System.out.println(record.get(s));
        //         if (record.get(s) == i) {
        //             System.out.println(s);
        //             sortedWords.add(s);
        //             break;
        //         }
        //     }
        // }

        ranks = new TreeMap<String, Integer>();
        int x = 1;
        Iterator<Map.Entry<String, Integer>> listItems = rankEntries.iterator();
        while (listItems.hasNext()) {
            Map.Entry<String, Integer> current = listItems.next();
            ranks.put(current.getKey(), x);
            x += 1;
        }
        cached = true;
    }


    /** Returns the number of words recorded this year. */
    public int size() {
        return record.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        // ArrayList<Integer> ascendingCount = new ArrayList<Integer>(record.values());
        // Collections.sort(ascendingCount);
        // ArrayList<String> ascendingWords = new ArrayList<String>();
        // for (Integer i : ascendingCount) {
        //     for (String s : record.keySet()) {
        //         if (record.get(s) == i) {
        //             ascendingWords.add(s);
        //         }
        //     }
        // }
        // return ascendingWords;
        return sortedWords;
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        // ArrayList<Integer> ascendingCount = new ArrayList<Integer>(record.values());
        // Collections.sort(ascendingCount);
        // ArrayList<Number> ascendingNumbers = new ArrayList<Number>();
        // for (Integer i : ascendingCount) {
        //     ascendingNumbers.add(i);
        // }
        // return ascendingNumbers;
        ArrayList<Number> sorts = new ArrayList<Number>(sortedCounts);
        return sorts;
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
