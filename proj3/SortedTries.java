import java.util.ArrayList;
import java.util.Comparator;
/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * 
 * Throw an IllegalArgumentException whenever:
 * 1) A null or empty string is added.
 *
 * @author Aditya Iyengar
 */
public class SortedTries extends Trie {
	
	/** Default constructor for the SortedTries class. */
    public SortedTries(ArrayList<Character> alphabet) {
        pointer = new Node(alphabet);
    }

    /**
      * Given some word s of length N, should add s to the Trie in O(N) time and space.
      * (Note: or comparison, adding all prefixes of a String of length N to a hash table
      * takes expected O(N^2) time and space.)
      * @param s : word we want to check.
      * @param alphabet : holds the order of the words in the new alphabet (how to sort).
      */
    public void insert(String s, ArrayList<Character> alphabet) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        insert(pointer, s, 0, alphabet);
    }

    /**
      * Does the core functionality of insert, but also take in a Node object and the int
      * that keeps track of the character number that we are currently on within the String.
      * @param root : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param count : keeps track of the index in the word.
      * @param alphabet : holds the order of the words in the new alphabet (how to sort).
      * @return Node : returns the root node.
      */
    protected Node insert(Node root, String word, int count, ArrayList<Character> alphabet) {
        char c = word.charAt(count);
        if (root.links.containsKey(c)) {
            if (count != word.length() - 1) {
                insert(root.links.get(c), word, count + 1, alphabet);
            } else {
                root.links.get(c).exists = true;
            }
        }
        else {
            root.links.put(c, new Node(alphabet));
            if (count == word.length() - 1) {
                root.links.get(c).exists = true;
            } else {
                insert(root.links.get(c), word, count + 1, alphabet);
            }
        }
        return root;
    }
}
