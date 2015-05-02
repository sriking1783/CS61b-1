import java.util.TreeMap;
/**
  * A special version of a Trie that contains all terms along with
  * their weights. Each node also stores maximum weight of its subtries.
  * 
  * @author Aditya Iyengar
  */
public class TernaryST<V> {

    protected TSTNode<V> root;

    class TSTNode<V> {
        /** Holds the maximum weight of the subtrees. */
        public double maxWeight;
        /** Holds the weight of the current node (if it exists). This value
          * remains at 0 unless exists is true. */
        public double weight;
        /** True if this node makes a full word. */
        public boolean exists;
        /** Holds a link to the left Trie node. */
        public TSTNode<V> left;
        /** Holds a link to the middle Trie node. */
        public TSTNode<V> middle;
        /** Holds a link to the right Trie node. */
        public TSTNode<V> right;
        /** Holds the character that represents this node */
        public char character;

        /** Basic constructor for the TSTNode class. */
        public TSTNode() {
            left = new TSTNode<V>();
            middle = new TSTNode<V>();
            right = new TSTNode<V>();
            exists = false;
            weight = 0;
            maxWeight = 0;
            character = '\0';
        }

        public TSTNode(char charct) {
            this();
            character = charct;
        }
    }

    // /** Checks if the term is contained in the TST
    //   * @param term : word we want to check.
    //   * @return double : weight of the word.
    //   */
    // public boolean contains(String term) {
    //     return contains(root, term, 0);
    // }

    // /** 
    //   * Does the core functionality of contains and returns if the word exists.
    //   * @param root : Keeps track of the root node (for recursive calls).
    //   * @param word : word we want to check.
    //   * @param count : keeps track of the index in the word.
    //   * @return boolean : returns if the word exists in the tst.
    //   */
    // protected boolean contains(TSTNode root, String word, int count) {
    //     char c = word.charAt(count);
    //     if (root.character.equals(c)) {
    //         if (root.exists == true) {
    //             return true;
    //         }
    //         return contains(root.middle, word, count + 1);
    //     } else if (c < root.character) {
    //         return contains(root.left, word, count);
    //     }
    //     return contains(root.right, word, count);
    // }

    /**
      * Finds the weight of a given term in the TST.
      * @param term : word we want to check.
      * @return double : weight of the word.
      */
    public double findWeight(String term) {
        return findWeight(root, term , 0);
    }

    /** 
      * Does the core functionality of findWeight and returns the weight
      * of the given word
      * @param root : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param count : keeps track of the index in the word.
      * @return double : returns the weight of the word.
      */
    protected double findWeight(TSTNode root, String word, int count) {
        char c = word.charAt(count);
        if (root.character.equals(c)) {
            if (root.exists == true) {
                return root.weight;
            }
            return findWeight(root.middle, word, count + 1);
        } else if (c < root.character) {
            return findWeight(root.left, word, count);
        }
        return findWeight(root.right, word, count);
    }

    /**
      * Given some word of length N, adds the word into the TST in O(N).
      * @param s : word we want to check.
      * @param wordWeight : weight of the word we want to insert.
      */
    public void insert(String s, double wordWeight) {
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        insert(root, s, 0, wordWeight);
    }

    /**
      * Does the core functionality of insert, but also take in a Node object and the int
      * that keeps track of the character number that we are currently on within the String.
      * @param root : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param count : keeps track of the index in the word.
      * @param wordWeight: keeps track of the weight of the full word.
      * @return Node : returns the root node.
      */
    protected TSTNode insert(TSTNode<V> root, String word, int count, double wordWeight) {
        char c = word.charAt(count);
        // if (count == word.length() - 1) {
        //     root.exists = true;
        //     root.maxWeight = wordWeight;
        //     root.weight = wordWeight;

        // }
        if (root.character.equals(c)) {
            if (count == word.length() - 1) {
                root.exists = true;
                root.maxWeight = wordWeight;
                root.weight = wordWeight;
            } else {
                if (root.middle == null) {
                    root.middle = new TSTNode<V>();
                }
                insert(root.middle, word, count + 1, wordWeight);
            }
        } else if (root.character > c) {
            if (wordWeight > root.maxWeight) {
                root.maxWeight = wordWeight;
            }
            insert(root.left, word, count + 1, wordWeight);
        } else if (root.character > c) {
            if (wordWeight > root.maxWeight) {
                root.maxWeight = wordWeight;
            }
            insert(root.right, word, count + 1, wordWeight);
        }
        return root;
    }

}
