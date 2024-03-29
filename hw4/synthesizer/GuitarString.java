// Make sure to make this class a part of the synthesizer package
package synthesizer;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
      * the values cannot be changed at runtime. We'll discuss this and other topics
      * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    
    /* Buffer for storing sound data. */
    private BoundedQueue buffer;
    
    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer(capacity);
    }
    
    
    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        if (!buffer.isEmpty()) {
            int x = buffer.capacity();
            for (int i = 0; i < x; i++) {
                buffer.dequeue();
            }
        }
        for (int j = 0; j < buffer.capacity(); j++) {
            double r = Math.random() - 0.5;
            buffer.enqueue(r);
        }
        System.out.println(buffer.fillCount());
    }
    
    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double x = buffer.dequeue();
        buffer.enqueue(DECAY * (0.5 * (x + buffer.peek())));
    }
    
    /* Return the double at the front of the buffer. */
    public double sample() {
        return 0;
    }
    
}
