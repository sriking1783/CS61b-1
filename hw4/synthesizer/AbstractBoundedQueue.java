package synthesizer;

public abstract class AbstractBoundedQueue implements BoundedQueue {
	protected int fillCount;
	protected int capacity;

	public int capacity() {
		return capacity;
	}

	public int fillCount() {
		return fillCount;
	}

	public boolean isEmpty() {
		if (fillCount != 0) {
			return false;
		}
		return true;
	}
	
	public boolean isFull() {
		return fillCount == capacity;
	}
}