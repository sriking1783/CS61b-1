/** 
  * Represents each node within the TernaryST. (Implementation taken from lecture slides).
  * Note: Using a TreeMap implementation because it was recommended to save memory, even
  * though it may be slightly less efficient.
  * 
  * @author Aditya Iyengar
  */
public class TSTNode {
    /** Holds the maximum weight of the subtrees. */
    public double maxWeight;
    /** Holds the weight of the current node (if it exists). This value
      * remains at 0 unless exists is true. */
    public double weight;
    /** True if this node makes a full word. */
    public boolean exists;
    /** Holds a link to the left Trie node. */
    public TSTNode left;
    /** Holds a link to the middle Trie node. */
    public TSTNode middle;
    /** Holds a link to the right Trie node. */
    public TSTNode right;
    /** Holds the character that represents this node */
    public Character character;

    /** Basic constructor for the TSTNode class. */
    public TSTNode() {
        exists = false;
        weight = 0;
        maxWeight = 0;
        character = null;
    }
}
