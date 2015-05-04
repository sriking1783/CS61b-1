import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Comparator;
/** 
  * Represents each node within the Trie. (Implementation taken from lecture slides).
  * Note: Using a TreeMap implementation because it was recommended to save memory, even
  * though it may be slightly less efficient.
  * 
  * @author Aditya Iyengar
  */
public class Node {
    /** Keeps track of whether or not this is the last node in a full word. */
    boolean exists;
    /** Keeps track of all of the words that have this node in their prefix. */
    TreeMap<Character, Node> links;
    /** Stores the order of characters in the alphabet (Used for the constructor). */
    ArrayList<Character> alphabet;

    /** Constructor for the Node class. */
    public Node() {
        links = new TreeMap<Character, Node>();
        exists = false;
    }

    /**
      * Constructor for the Node class that takes in the order of the alphabet.
      * (allows for easier sorting when inserting objects into the SortedTries)
      * @param alphabetOrder : order of the alphabet for comparator object.
      */
    public Node(ArrayList<Character> alphabetOrder) {
        alphabet = alphabetOrder;
        links = new TreeMap<Character, Node>(new Comparator<Character>() {
            @Override
            public int compare(Character x1, Character x2) {
                if (alphabet.indexOf(x1) < alphabet.indexOf(x2)) {
                    return -1;
                } else if (alphabet.indexOf(x1) > alphabet.indexOf(x2)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        exists = false;
    }
}
