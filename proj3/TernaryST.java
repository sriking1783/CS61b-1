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
        // root.left = new TSTNode();
        // root.middle = new TSTNode();
        // root.right = new TSTNode();
    }

    /**
      * Returns find the string for the k words
      * @param prefixNode : Keeps track of the root node (for recursive calls).
      * @param prefix : word we want to check.
      * @param count : keeps track of the index in the word.
      * @return Iterable : returns the node of the last character of the prefix.
      */
    protected Iterable<String> prefixMatch(TSTNode prefixNode, String prefix,
                                        ArrayList<String> matches, String middle,
                                        ArrayList<TSTNode> lastNode, int k) {
        PriorityQueue<TSTNode> letterChecker
                = new PriorityQueue<TSTNode>(1, new Comparator<TSTNode>() {
            @Override
            public int compare(TSTNode x1, TSTNode x2) {
                return (int) x2.maxWeight - (int) x1.maxWeight;
            }
        });
                System.out.println("aaaaaaaa");
                System.out.println(prefix);
                System.out.println(prefixNode.character);
                System.out.println("bbbbbbbb");
        if (prefixNode.middle != null) {
            System.out.println(1);
            letterChecker.add(prefixNode.middle);
        }
        if (prefixNode.left != null) {
            System.out.println(2);
            letterChecker.add(prefixNode.left);
        }
        if (prefixNode.right != null) {
            System.out.println(3);
            letterChecker.add(prefixNode.right);
        }
        
        middle = middle.concat(prefixNode.character.toString());
        System.out.println(middle);
        if (prefixNode.exists) {
            System.out.println("XXXXXXXXX");
            TSTNode min = prefixNode;
            boolean ran = false;
            for (TSTNode last : lastNode) {
                ran = true;
                System.out.println("QQQQQQQQ");
                if (min.weight > last.weight) {
                    System.out.println("WWWWWWWW");
                    min = last;
                }
            }
            System.out.println("EEEEEEEE");
            if (min != prefixNode) {
                System.out.println("RRRRRRRR");
                lastNode.remove(min);
                lastNode.add(prefixNode);
                matches.add(prefix.concat(middle));
                if (matches.size() == k) {
                    System.out.println("TTTTTTTT");
                    return matches;
                }
            } else if (!ran) {
                System.out.println("UUUUUUUU");
                lastNode.add(prefixNode);
                matches.add(prefix.concat(middle));
                if (matches.size() == k) {
                    System.out.println("YYYYYYYY");
                    for (String s : matches) {
                        System.out.println(s);
                    }
                    return matches;
                }
            }
        }

        Iterator<TSTNode> character = letterChecker.iterator();
        while (character.hasNext()) {
            TSTNode part = character.next();
            System.out.println("low");
            if (part.character == null) {
                continue;
            }
            return prefixMatch(part, prefix, matches, middle, lastNode, k);
        }
        System.out.println("hi");
        System.out.println();
        for (String s : matches) {
            System.out.println(s);
        }
        System.out.println();
        return matches;
    }

    // /**
    //   * Adds left, middle, and right characters in order to the PriorityQueue.
    //   * @param prefixNode : Keeps track of the root node (for recursive calls).
    //   * @param letterChecker : the priority queue to be added to.
    //   */
    // protected void findMax(TSTNode prefixNode, PriorityQueue<TSTNode> letterChecker) {
    //     double middle = root.middle.maxWeight;
    //     double left = root.left.maxWeight;
    //     double right = root.right.maxWeight;
    //     if ((middle > left) && (middle > right)) {
    //         letterChecker.add(root.middle);
    //         if (left > right) {
    //             letterChecker.add(root.left);
    //         } else {
    //             letterChecker.add(root.right);
    //         }
    //     }
    //     if ((left > middle) && (left > right)) {
    //         letterChecker.add(root.left);
    //         if (right > middle) {
    //             letterChecker.add(root.right);
    //         } else {
    //             letterChecker.add(root.middle);
    //         }
    //     }
    //     if ((right > left) && (right > middle)) {
    //         letterChecker.add(root.right);
    //         if (middle > left) {
    //             letterChecker.add(root.middle);
    //         } else {
    //             letterChecker.add(root.left);
    //         }
    //     }
    // }

    /** 
      * Finds the node where the prefix ends.
      * @param root : Keeps track of the root node (for recursive calls).
      * @param prefix : word we want to check.
      * @param count : keeps track of the index in the word.
      * @return Node : returns the node of the last character of the prefix.
      */
    protected TSTNode prefixNode(TSTNode root, String prefix, int count) {
        char c = prefix.charAt(count);
        System.out.println(c);
        if (root == null) {
            System.out.println("ZZZZZZZZ");
            return root;
        }
        System.out.println(root.character);
        if (root.character == c) {
            System.out.println("CCCCCCCC");
            if (count == prefix.length() - 1) {
                System.out.println("dfdfdfdfdf");
                return root;
            }
            System.out.println("VVVVVVVV");
            return prefixNode(root.middle, prefix, count + 1);
        } else if (c < root.character) {
            System.out.println("BBBBBBBB");
            return prefixNode(root.left, prefix, count);
        } else {
            System.out.println("NNNNNNNN");
            return prefixNode(root.right, prefix, count);
        }
        // char c = prefix.charAt(count);
        // System.out.println("ccccc");
        // System.out.println(c);
        // System.out.println("ddddd");
        // if (root == null) {
        //     System.out.println("slslslslslsl");
        //     return root;
        // }
        // if (root.character == c) {
        //     if (count == prefix.length() - 1) {
        //         return root;
        //     }
        //     return prefixNode(root.middle, prefix, count + 1);
        // } else if (root.character > c) {
        //     return prefixNode(root.left, prefix, count);
        // } else {
        //     return prefixNode(root.right, prefix, count);
        // }
        // System.out.println("sfsdfsf");
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
        if (root.character == c) {
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

    boolean first = false;

    /**
      * Does the core functionality of insert, but also take in a Node object and the int
      * that keeps track of the character number that we are currently on within the String.
      * @param root : Keeps track of the root node (for recursive calls).
      * @param word : word we want to check.
      * @param count : keeps track of the index in the word.
      * @param wordWeight: keeps track of the weight of the full word.
      * @return TSTNode : returns the root node.
      */
    protected TSTNode insert(TSTNode root, String word, int count, double wordWeight) {
        char c = word.charAt(count);
        first = false;
        if (root.character == null) {
            root.character = c;
            boolean first = true;
        }
        if (root.middle == null) {
            root.middle = new TSTNode();
        }
        if (root.right == null) {
            root.right = new TSTNode();
        }
        if (root.left == null) {
            root.left = new TSTNode();
        }
        if (count == word.length() - 1) {
            root.exists = true;
            root.maxWeight = wordWeight;
            root.weight = wordWeight;
            return root;
        }
        if (first == true) {
            return insert(root.middle, word, count + 1, wordWeight);
        }
        if (root.character == word.charAt(count)) {
            if (root.middle.maxWeight < wordWeight) {
                root.middle.maxWeight = wordWeight;
            }
            return insert(root.middle, word, count + 1, wordWeight);
        } else if (root.character > word.charAt(count)) {
            if (root.right.maxWeight < wordWeight) {
                root.right.maxWeight = wordWeight;
            }
            return insert(root.right, word, count, wordWeight);
        } else if (root.character > word.charAt(count)) {
            if (root.left.maxWeight < wordWeight) {
                root.left.maxWeight = wordWeight;
            }
            return insert(root.left, word, count, wordWeight);
        }
        return root;
        // } else {
        //     root.character = c;
        //     if ()
        // }
        // if (root.character == c) {
        //     if (count == word.length() - 1) {
        //         root.exists = true;
        //         root.maxWeight = wordWeight;
        //         root.weight = wordWeight;
        //     } else {
        //         if (root.middle == null) {
        //             root.middle = new TSTNode();
        //         }
        //         insert(root.middle, word, count + 1, wordWeight);
        //     }
        // } else if (root.character > c) {
        //     if (wordWeight > root.maxWeight) {
        //         root.maxWeight = wordWeight;
        //     }
        //     insert(root.left, word, count, wordWeight);
        // } else if (root.character > c) {
        //     if (wordWeight > root.maxWeight) {
        //         root.maxWeight = wordWeight;
        //     }
        //     insert(root.right, word, count, wordWeight);
        // }
    }
}
