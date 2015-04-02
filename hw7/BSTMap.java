import java.util.Set;
import java.util.TreeSet;

/* A form of a map built around the idea of a Binary Search Tree (BST);
 * Implements the Map61b Interface. */ 
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size = 0;
    private Node root;
    private Set<K> keySet = new TreeSet<K>();

    /* I took this some parts of this implementation of the private Node
     * class from the BST example given in the Princeton Library (attached
     * to the HW); however, I did change it to meet my requirements.
     * 
     * (Note: Used the Princeton Library code only for this part of the
     * homework. Everything else was written by me.) */
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        size = 0;
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return containsKey(this.root, key);
    }

    /* Used to check if a particular tree contains the key; particularly
     * used to search through subtrees (overrides containsKey method). */
    private boolean containsKey(Node n, K key) {
        if (n == null) {
            return false;
        }
        int compare = key.compareTo(n.key);
        if (compare == 0) {
            return true;
        } else if (compare < 0) {
            return containsKey(n.left, key);
        }
        return containsKey(n.right, key);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. 
     */
    public V get(K key) {
        return get(this.root, key);
    }

    /* Checks a particular tree and gets the key; particularly used to search
     * through subtrees (overrides get method). */
    private V get(Node n, K key) {
        if (n == null) {
            return null;
        }
        int compare = key.compareTo(n.key);
        if (compare == 0) {
            return n.value;
        } else if (compare < 0) {
            return get(n.left, key);
        }
        return get(n.right, key);
    }

   /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (root != null) {
            this.put(root, key, value);
        } else {
            root = new Node(key, value);
        }
        keySet.add(key);
        size += 1;
    }

    /* Searches through a particular tree and returns the appropriate value;
     * used to search through subtrees (overrides get method). */
    private void put(Node n, K key, V value) {
        int compare = key.compareTo(n.key);
        if (compare == 0) {
            n.value = value;
        } else if (compare < 0) {
            if (n.left == null) {
                n.left = new Node(key, value);
            } else {
                put(n.left, key, value);
            }
        } else if (compare > 0) {
            if (n.right == null) {
                n.right = new Node(key, value);
            } else {
                put(n.right, key, value);
            }
        }
    }

    /* Prints the BSTMap in order of increasing key. */
    public void printInOrder() {
        for (K key : keySet) {
            System.out.println(this.get(key));
        }
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for HW6. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for HW6a. */
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /* Returns a Set view of the keys contained in this map. Not required for HW6. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }    
}
