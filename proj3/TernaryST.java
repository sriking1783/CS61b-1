// import java.util.TreeMap;
// /**
//   * A special version of a Trie that contains all terms along with
//   * their weights. Each node also stores maximum weight of its subtries.
//   * 
//   * @author Aditya Iyengar
//   */
// public class TernaryST<V> {

//     protected TSTNode<V> root;

//     class TSTNode<V> {
//         /** Holds the maximum weight of the subtrees. */
//         public int maxWeight;
//         /** Holds the weight of the current node (if it exists). This value
//           * remains at 0 unless exists is true. */
//         public int weight;
//         /** True if this node makes a full word. */
//         public boolean exists;
//         * Holds a link to the left Trie node. 
//         public TSTNode<V> left;
//         /** Holds a link to the middle Trie node. */
//         public TSTNode<V> middle;
//         /** Holds a link to the right Trie node. */
//         public TSTNode<V> right;

//         /** Basic constructor for the TSTNode class. */
//         public TSTNode() {
//             left = new TSTNode<V>();
//             middle = new TSTNode<V>();
//             right = new TSTNode<V>();
//             exists = false;
//             weight = 0;
//             maxWeight = 0;
//         }
//     }

//     protected void insert(String s) {
//         if (s == null || s.equals("")) {
            
//         }
//     }

// }