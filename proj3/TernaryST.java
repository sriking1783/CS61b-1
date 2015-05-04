import java.util.TreeMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
/**
  * A special version of a Trie that contains all terms along with
  * their weights. Each node also stores maximum weight of its subtries.
  * 
  * @author Aditya Iyengar
  */
public class TernaryST {

    /** Root node for the TST */
    protected TSTNode root;

    /** Basic constructor for TernaryST */
    public TernaryST() {
        root = new TSTNode();
    }

    /**
      * Returns find the string for the k words
      * @param prefixNode : Keeps track of the root node (for recursive calls).
      * @param prefix : word we want to check.
      * @param count : keeps track of the index in the word.
      * @return Iterable : returns the node of the last character of the prefix.
      */
    protected PriorityQueue<String> prefixMatch(TSTNode prefixNode, String prefix, String middle,
            int k, PriorityQueue<String> wordAdd, TreeMap<String, Double> wordWeight) {
        
        if (prefixNode == null) {
            return wordAdd;
        }
        PriorityQueue<TSTNode> letterChecker
                = new PriorityQueue<TSTNode>(1, new Comparator<TSTNode>() {
            @Override
            public int compare(TSTNode x1, TSTNode x2) {
                if (x1.maxWeight < x2.maxWeight) {
                    return -1;
                } else if (x1.maxWeight == x2.maxWeight) {
                    return 0;
                }
                return 1;
            }
        });
        if (prefixNode.middle != null) {
            letterChecker.add(prefixNode.middle);
            if (prefixNode.middle.left != null) {
                letterChecker.add(prefixNode.middle.left);
            }
            if (prefixNode.middle.right != null) {
                letterChecker.add(prefixNode.middle.right);
            }
        }
        middle = middle.concat(prefixNode.character.toString());
        if (prefixNode.weight != 0) {
            String r;
            if (prefix != "") {
                r = prefix.concat(middle);
            } else {
                r = middle;
            }
            wordAdd.add(prefix.concat(middle));
            if (wordWeight.containsKey(prefix.concat(middle))) {
                if (wordAdd.size() > k) {
                    if (wordWeight.get(r) > wordWeight.get(wordAdd.peek())) {
                        wordAdd.remove(wordAdd.peek());
                    } else {
                        wordAdd.remove(prefix.concat(middle));
                    }
                }
            }
        }
        while (letterChecker.size() > 0) {
            TSTNode part = letterChecker.poll();
            prefixMatch(part, prefix, middle, k, wordAdd, wordWeight);
        }
        return wordAdd;
    }

    /** 
      * Finds the node where the prefix ends.
      * @param x : Keeps track of the root node (for recursive calls).
      * @param prefix : word we want to check.
      * @param count : keeps track of the index in the word.
      * @return Node : returns the node of the last character of the prefix.
      */
    protected TSTNode prefixNode(TSTNode x, String prefix, int count) {
        if (prefix.equals("") || prefix == null) {
            return x;
        }
        char c = prefix.charAt(count);
        if (x == null) {
            return x;
        }
        if (x.character == c) {
            if (count == prefix.length() - 1) {
                return x;
            }
            return prefixNode(x.middle, prefix, count + 1);
        } else if (x.character > c) {
            return prefixNode(x.left, prefix, count);
        } else {
            return prefixNode(x.right, prefix, count);
        }
    }

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
      * @param x : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param count : keeps track of the index in the word.
      * @return double : returns the weight of the word.
      */
    protected double findWeight(TSTNode x, String word, int count) {
        Character c = word.charAt(count);
        if (x.character == c) {
            if (count == word.length() - 1 && x.exists == true) {
                return x.weight;
            }
            return findWeight(x.middle, word, count + 1);
        } else if (x.character > c) {
            return findWeight(x.left, word, count);
        }
        return findWeight(x.right, word, count);
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
        if (root.character == null) {
            root.character = s.charAt(0);
        }
        insert(root, s, 0, wordWeight);
    }

    /**
      * Does the core functionality of insert, but also take in a Node object and the int
      * that keeps track of the character number that we are currently on within the String.
      * @param x : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param count : keeps track of the index in the word.
      * @param wordWeight: keeps track of the weight of the full word.
      * @return TSTNode : returns the root node.
      */
    protected TSTNode insert(TSTNode x, String word, int count, double wordWeight) {
        Character c = word.charAt(count);
        if (x == null) {
            x = new TSTNode();
            x.maxWeight = wordWeight;
            x.character = c;
        }
        if (count == word.length() - 1) {
            if (c < x.character) {
                x.left = insert(x.left, word, count, wordWeight);
            } else if (c > x.character) {
                x.right = insert(x.right, word, count, wordWeight);
            } else {
                x.exists = true;
                x.maxWeight = wordWeight;
                x.weight = wordWeight;
                return x;
            }
        } else {
            if (x.maxWeight < wordWeight) {
                x.maxWeight = wordWeight;
            }
            if (c == x.character) {
                x.middle = insert(x.middle, word, count + 1, wordWeight);
            } else if (c < x.character) {
                x.left = insert(x.left, word, count, wordWeight);
            } else {
                x.right = insert(x.right, word, count, wordWeight);
            }
        }
        return x;
    }
}
