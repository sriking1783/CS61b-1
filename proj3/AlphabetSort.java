import java.util.Scanner;
import java.util.ArrayList;
/**
 * Alphabet Sorter: takes in a given alphabetical order and then
 * sorts words in the according format. 
 *
 * Throw an IllegalArgumentException whenever:
 * 1) No words or alphabet are given.
 * 2) A letter appears multiple times in the alphabet.
 *
 * @author Aditya Iyengar
 */
public class AlphabetSort {

    /** Does the core work of the AlphabetSort class. */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        if (!input.hasNext()) {
            throw new IllegalArgumentException();
        }
        String alphabetList = input.next();
        ArrayList<Character> alphabet = new ArrayList<Character>();
        for (int i = 0; i < alphabetList.length(); i++) {
            char c = alphabetList.charAt(i);
            if (alphabetList.substring(0, i).contains(String.valueOf(c))) {
                throw new IllegalArgumentException();
            }
            alphabet.add(c);
        }
        if (!input.hasNext()) {
            throw new IllegalArgumentException();
        }
        SortedTries sortedWords = new SortedTries(alphabet);
        while (input.hasNext()) {
            sortedWords.insert(input.next(), alphabet);
        }
        input.close();
        AlphabetSort as = new AlphabetSort();
        as.output(sortedWords.pointer, sortedWords.pointer, "");
    }

    /**
      * Returns the results of going back into the Trie object and returning the sorted list.
      * @param root : Keeps track of the root node (for recursive calls).
      * @param initial : Holds a place for the initial root node.
      * @param s : Holds the prefixes of the words.
      */
    protected void output(Node root, Node initial, String s) {
        String temp = s;
        for (Character c : root.links.keySet()) {
            s = temp;
            if (root == initial) {
                s = "";
            }
            s = s.concat(c.toString());
            if (root.links.get(c).exists) {
                System.out.println(s);
            }
            output(root.links.get(c), initial, s);
        }
    }
}
