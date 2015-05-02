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
    protected Node pointer;

    /** Default constructor for the Trie class. */
    public Trie() {
        pointer = new Node();
    }

    /** 
      * Given some query s of length N, find whether or not the string is in the trie in
      * O(N) time. If isFullWord is false, then partial prefix matches should return true;
      * if isFullWord is true, then only full word matches should return true.
      * @param s : word we want to check.
      * @param isFullWord : determines whether we should check for the whole or part of the word.
      * @return boolean : returns whether or not the word exists.
      */
    public boolean find(String s, boolean isFullWord) {
        return find(pointer, s, isFullWord, 0);
    }

    /**
      * Does the core functionality of find, but it takes into account the root node (so we
      * can write the function recursively).
      * @param root : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param isFullWord : determines whether we should check for the whole or part of the word.
      * @param count : keeps track of the index in the word.
      * @return boolean : returns whether or not the word exists.
      */
    protected boolean find(Node root, String word, boolean isFullWord, int count) {
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

    /**
      * Given some word s of length N, should add s to the Trie in O(N) time and space.
      * (Note: or comparison, adding all prefixes of a String of length N to a hash table
      * takes expected O(N^2) time and space.)
      * @param s : word we want to check.
      */
    public void insert(String s) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        insert(pointer, s, 0);
    }

    /**
      * Does the core functionality of insert, but also take in a Node object and the int
      * that keeps track of the character number that we are currently on within the String.
      * @param root : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param count : keeps track of the index in the word.
      * @return Node : returns the root node.
      */
    protected Node insert(Node root, String word, int count) {
        char c = word.charAt(count);
        if (root.links.containsKey(c)) {
            if (count != word.length() - 1) {
                insert(root.links.get(c), word, count + 1);
            } else {
                root.links.get(c).exists = true;
            }
        } else {
            root.links.put(c, new Node());
            if (count == word.length() - 1) {
                root.links.get(c).exists = true;
            } else {
                insert(root.links.get(c), word, count + 1);
            }
        }
        return root;
    }
}
