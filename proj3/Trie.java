import java.util.Map;
import java.util.TreeMap;
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
public class Trie {

    /** Keeps track of the root note in the Trie. */
    private Node pointer = new Node();

    /** Represents each node within the Trie. (Implementation taken from lecture slides).
      * Note: Using a TreeMap implementation because it was recommended to save memory, even
      * though it may be slightly less efficient. */
    private class Node {
        boolean exists;
        Map<Character, Node> links;

        public Node() {
            links = new TreeMap<Character, Node>();
            exists = false;
        }
    }

    /** Given some query s of length N, find whether or not the string is in the trie in
      * O(N) time. If isFullWord is false, then partial prefix matches should return true;
      * if isFullWord is true, then only full word matches should return true. */
    public boolean find(String s, boolean isFullWord) {
        return true;
    }

    /** Given some word s of length N, should add s to the Trie in O(N) time and space.
      * (Note: or comparison, adding all prefixes of a String of length N to a hash table
      * takes expected O(N^2) time and space.) */
    public void insert(String s) {
        if (s == null || s == "") {
            throw new IllegalArgumentException();
        }
        insert(pointer, s, 0);
    }

    /** Does the core functionality of insert, but also take in a Node object and the int
      * that keeps track of the character number that we are currently on within the String. */
    private Node insert(Node root, String word, int count) {
        char c = word.charAt(count);
        // if (root == null) {
        //     root = new Node();
        // }
        if (root.links.containsKey(c)) {
            return insert(root.links.get(c), word, count + 1);
        }
        root.links.put(c, new Node());
        if (count == word.length() - 1) {
            // root.links.put(c, new Node());
            root.links.get(c).exists = true;
            return root;
        }
        // root.links.put(c, new Node());
        insert(root.links.get(c), word, count + 1);
        // root.links.put(c, insert(new Node(), word, count + 1));
        return root;
    }
}
