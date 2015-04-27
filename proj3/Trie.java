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
    public Node pointer = new Node();

    /** Represents each node within the Trie. (Implementation taken from lecture slides).
      * Note: Using a TreeMap implementation because it was recommended to save memory, even
      * though it may be slightly less efficient. */
    public class Node {
        boolean exists;
        TreeMap<Character, Node> links;

        public Node() {
            links = new TreeMap<Character, Node>();
            exists = false;
        }
    }

    /** Given some query s of length N, find whether or not the string is in the trie in
      * O(N) time. If isFullWord is false, then partial prefix matches should return true;
      * if isFullWord is true, then only full word matches should return true. */
    public boolean find(String s, boolean isFullWord) {
        return find(pointer, s, isFullWord, 0);
    }

    /** Does the core functionality of find, but it takes into account the root node (so we
      * can write the function recursively). */
    private boolean find(Node root, String word, boolean isFullWord, int count) {
        Character c = word.charAt(count);
        if (!root.links.containsKey(c)) {
            return false;
        }
        if (count == word.length() - 1) {
            if (isFullWord) {
                return root.links.get(c).exists;
            }
            return true;
        }
        return find(root.links.get(c), word, isFullWord, count + 1);
        
    }

    /** Given some word s of length N, should add s to the Trie in O(N) time and space.
      * (Note: or comparison, adding all prefixes of a String of length N to a hash table
      * takes expected O(N^2) time and space.) */
    public void insert(String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        insert(pointer, s, 0);
        // insertIter(pointer, s, 0);
    }

    /** Does the core functionality of insert, but also take in a Node object and the int
      * that keeps track of the character number that we are currently on within the String. */
    private Node insert(Node root, String word, int count) {
        // char c = word.charAt(count);
        // if (root.links.containsKey(c)) {
        //     if (count != word.length() - 1) {
        //         insert(root.links.get(c), word, count + 1);
        //     } else {
        //         root.links.get(c).exists = true;
        //         return root;
        //     }
        // }
        // root.links.put(c, new Node());
        // if (count == word.length() - 1) {
        //     root.links.get(c).exists = true;
        //     return root;
        // }
        // insert(root.links.get(c), word, count + 1);
        // return root;
        Character c = word.charAt(count);
        if (count == word.length() - 1) {
            if (root.links.containsKey(c)) {
                root.links.get(c).exists = true;
            }
            else {
                root.links.put(c, new Node());
                root.links.get(c).exists = true;
            }
        } else if (root.links.containsKey(c)) { 
            insert(root.links.get(c), word, count + 1);
        } else {
            root.links.put(c, new Node());
            insert(root.links.get(c), word, count + 1);
        }
        return root;
    }

    /** Does the core functionality of insert, through an iterative process */
    private void insertIter(Node root, String word, int count) {
        char c = word.charAt(count);
        while (count != word.length()) {
            // if (root.links.containsKey(c)) {
            //     if (count == word.length() - 1) {
            //         root.links.get(c).exists = true;
            //         return;
            //     } else {
            //         root = root.links.get(c);
            //         count += 1;
            //         c = word.charAt(count);
            //         continue;
            //     }
            // }
            if (!root.links.containsKey(c)) {
                root.links.put(c, new Node());
            }

            if (word.length() - 1 == count) {
                root.links.get(c).exists = true;
                return;
            }
            root = root.links.get(c);
            count += 1;
            c = word.charAt(count);
        }
    }
}
