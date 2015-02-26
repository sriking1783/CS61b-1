// Make sure to make this class a part of the synthesizer package
package synthesizer;

public class ArrayRingBuffer extends AbstractBoundedQueue {
  /* Index for the next dequeue or peek. */
  private int first;           
  /* Index for the next enqueue. */
  private int last;             
  /* Array for storing the buffer data. */
  private double[] rb;

  /** Create a new ArrayRingBuffer with the given capacity. */
  public ArrayRingBuffer(int capacity) {
      rb = new double[capacity];
      first = 0;
      last = 0;
      fillCount = 0;
      this.capacity = capacity;
  }

  /** Adds x to the end of the ring buffer. If there is no room, then
    * throw new RuntimeException("Ring buffer overflow") 
    */
  public void enqueue(double x) {
      if (fillCount == capacity) {
          throw new RuntimeException("Ring buffer overflow");
      }
      if (last == capacity) {
          last = 0;
      }
      if (first == capacity) {
          first = 0;
      }
      rb[last] = (int) x;
      last += 1;
      fillCount += 1;
  }

  /** Dequeue oldest item in the ring buffer. If the buffer is empty, then
    * throw new RuntimeException("Ring buffer underflow");
    */
  public double dequeue() {
      if (fillCount == 0) {
          throw new RuntimeException("Ring buffer underflow");
      }
      if (last == capacity) {
          last = 0;
      }
      if (first == capacity) {
          first = 0;
      }
      int removed = (int) rb[first];
      rb[first] = 0;
      first += 1;
      fillCount -= 1;
      return removed;
  }

  /** Return oldest item, but don't remove it. */
  public double peek() {
      if (fillCount == 0) {
          throw new RuntimeException("Ring buffer underflow");
      }
      if (last == capacity) {
          last = 0;
      }
      if (first == capacity) {
          first = 0;
      }
      return rb[first];
  }

}
