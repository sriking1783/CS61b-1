import java.util.AbstractList;
import java.lang.IllegalArgumentException;

public class ArrayList61B<Type> extends AbstractList<Type> {
	
	public Type[] items;
    /** Number of things in the array. */
    public int size = 0; 

	/** This constructor should initialize the size of the internal array
	 *  to be initialCapacity and throw an IllegalArgumentException if
	 *  initialCapacity is less than 1. */
	public ArrayList61B(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		items = (Type[]) new Object[initialCapacity];
	}

	/** This constructor should initialize the size of the internal
	  * array to be 1. */
	public ArrayList61B() {
		this(1);
	}

	/** This method should return the ith element in the list, This method
	 *  should throw an IllegalArgumentException if i is less than 0 or
	 *  greater than or equal to the number of elements in the list. */
	public Type get(int i) {
		if (i < 0 || i >= size) {
			throw new IllegalArgumentException();
		}
		return items[i];
	}

	/** This method should add item to the end of the list, resizing the
	  * internal array if necessary. This method should always return true
	  * (if you are curious about this, read the api for AbstractList). */
	public boolean add(Type item) {
		if (size == items.length) {
			this.resize(size);
		}
		items[size] = item;
		size += 1;
		return true;
	}

	/** This method should return the number of elements in the list. Note
	  * that this is not necessarily the same as the number of elements in
	  * the internal array. */
	public int size() {
		return size;
	}

	/** Resizes ITEMS to C, copying things over. (taken from lecture) */
    private void resize(int c) {
        Type[] newItems =  (Type[]) new Object[c * 2];
        for (int i = 0; i < items.length; i += 1) {
            newItems[i] = items[i];
        }
        items = newItems;
    }
}