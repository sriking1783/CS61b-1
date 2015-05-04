import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.LinkedList;
/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 * Throw an IllegalArgumentException whenever:
 * 1) The length of the terms and weights arrays are different.
 * 2) There are duplicate input terms.
 * 3) There are negative weights.
 * 4) Trying to find the k top matches for non-positive k.
 * 
 * Let's Do This!!!
 * @author Aditya Iyengar
 */
public class Autocomplete {

    /** Acts are the core trie structure for this class. */
    protected TernaryST tst;
    /** Keeps track of the weight of added words */
    protected TreeMap<String, Double> wordWeight;
    /** Holds the strings in order of weights */
    protected TreeMap<Double, String> ranks;

    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        tst = new TernaryST();
        if (terms.length != weights.length) {
            throw new IllegalArgumentException();
        }
        wordWeight = new TreeMap<String, Double>();
        ranks = new TreeMap<Double, String>(Collections.reverseOrder());
        for (int i = 0; i < terms.length; i++) {
            if (wordWeight.containsKey(terms[i]) || weights[i] < 0) {
                throw new IllegalArgumentException();
            }
            wordWeight.put(terms[i], weights[i]);
            ranks.put(weights[i], terms[i]);
            tst.insert(terms[i], weights[i]);
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term : the term we are trying to find the weight of.
     * @return double : weight of the term.
     */
    public double weightOf(String term) {
        double weight = wordWeight.get(term);
        if (weight < 0) {
            throw new IllegalArgumentException(); 
        }
        return weight;
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        return topMatches(prefix, 1).iterator().next();
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param prefix : prefix word.
     * @param k : count of k matches
     * @return Iterable : iterable object.
     */
    public Iterable<String> topMatches(String prefix, int k) {
        if (k < 0) {
            throw new IllegalArgumentException();
        }
        PriorityQueue<String> wordAdd = new PriorityQueue<String>(1, new Comparator<String>() {
            @Override
            public int compare(String x1, String x2) {
                if (wordWeight.get(x1) < wordWeight.get(x2)) {
                    return -1;
                } else if (wordWeight.get(x1) == wordWeight.get(x2)) {
                    return 0;
                }
                return 1;
            }
        });
        if (prefix.equals("")) {
            ArrayList<String> kWords = new ArrayList<String>();
            int count = 0;
            for (Double i : ranks.keySet()) {
                if (count == k) {
                    break;
                }
                kWords.add(ranks.get(i));
                count += 1;
            }
            return kWords;
        }
        PriorityQueue<String> returned;
        returned = tst.prefixMatch(tst.prefixNode(tst.root, prefix, 0),
            prefix.substring(0, prefix.length() - 1), "", k, wordAdd, wordWeight);
        TreeSet<String> returning = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String x1, String x2) {
                if (wordWeight.get(x1) < wordWeight.get(x2)) {
                    return 1;
                } else if (wordWeight.get(x1) == wordWeight.get(x2)) {
                    return 0;
                }
                return -1;
            }
        });
        returning.addAll(returned);
        return returning;
    }

    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * @param word The word to spell-check
     * @param dist Maximum edit distance to search
     * @param k    Number of results to return 
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();  
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        return results;
    }
    /**
     * Test client. Reads the data from the file, then repeatedly reads autocomplete queries
     * from standard input and prints out the top k matching terms.
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);

        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}
