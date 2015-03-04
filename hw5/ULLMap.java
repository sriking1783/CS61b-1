import java.util.Set; /* java.util.Set needed only for challenge problem. */
import java.util.Iterator;

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<Key, Value> implements Map61B<Key, Value>, Iterable<Key> {
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as a the actual front item
      * of the linked list. 
      */
    private Entry front;
    private int size;

    @Override
    public Value get(Key key) {
        Entry temp = front;
        while (temp != null) {
            if (key.equals(temp.key)) {
                return temp.val;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    public void put(Key key, Value val) {
        Entry temp = front;
        int replace = 1;
        while (temp != null) {
            if (key.equals(temp.key)) {
                temp.val = val;
                replace -= 1;
                break;
            }
            temp = temp.next;
        }
        if (replace != 0) {
            Entry iter = front;
            if (size > 0) {
                while (iter.next != null) {
                    iter = iter.next;
                }
                iter.next = new Entry(key, val, null);
            } else {
                front = new Entry(key, val, null);
            }
            size += 1;
        }
    }

    @Override
    public boolean containsKey(Key key) {
        return this.get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = null;
        size = 0;
    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry {
    
        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(Key k, Value v, Entry n) {
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(Key k) {
            Entry temp = this;
            while (temp != null) {
                if (k.equals(this.key)) {
                    return this;
                }
            }
            return null;
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private Key key;
        /** Stores the value of the key-value pair of this node in the list. */
        private Value val;
        /** Stores the next Entry in the linked list. */
        private Entry next;
    
    }

    /** Returns an object of type Iterator that iterates over all the elements
     *  of a data structure. */
    @Override
    public Iterator<Key> iterator() {
        return new ULLMapIter(this);
    }

    /** Iterable version of ULLMap that implements the Iterable interface */
    private class ULLMapIter implements Iterator<Key> {
    
        public ULLMapIter(ULLMap<Key, Value> m) {
            umap = m;
        }

        /** Returns true if there are more elements left to return. */
        @Override
        public boolean hasNext() {
            if (umap.front != null) {
                return true;
            }
            return false;
        }

        /** Returns the next element in the iterator. */
        @Override
        public Key next() {
            if (this.hasNext()) {
                Entry temp = umap.front;
                umap.front = umap.front.next;
                return temp.key;
            }
            return null;
        }

        /** Removed the most recently returned element. Often this method is
         *  not actually implemented (as in here). */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Stores the ULLMap in the iterator. */
        private ULLMap<Key, Value> umap;
    }

    // public static Map<Value, Key> inverse(Map<Key, Value> m) {

    // }

    /* Methods below are all challenge problems. Will not be graded in any way. 
     * Autograder will not test these. */
    @Override
    public Value remove(Key key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Value remove(Key key, Value value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Key> keySet() {
        throw new UnsupportedOperationException();
    }
}