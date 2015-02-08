import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the DoubleChain class
 */

public class TestDoubleChain {

    /** Tests the constructor of DoubleChain */
    @Test
    public void testConstructor() {
        DoubleChain d = new DoubleChain(5);
        assertEquals(5,d.getFront().val, 1e-6);
        assertEquals(null, d.getFront().prev);
        assertEquals(null, d.getFront().next);
    }

    /** Tests some basic DoubleChain operations. */
    @Test
    public void testBasicOperations() {
        DoubleChain d = new DoubleChain(5);
        assertEquals(5, d.getFront().val, 1e-11);
        assertEquals(5, d.getBack().val, 1e-11);

        d.insertFront(2);
        d.insertFront(1);
        d.insertBack(7);
        d.insertBack(8);
        assertEquals(1, d.getFront().val, 1e-11);
        assertEquals(8, d.getBack().val, 1e-11);

        assertEquals(8, d.deleteBack().val, 1e-11);
        assertEquals(7, d.getBack().val, 1e-11);

        DoubleChain x = new DoubleChain(5);
        assertEquals(5, x.deleteBack().val, 1e-11);
        assertEquals(null, x.deleteBack());

        DoubleChain y = new DoubleChain(5);
        y.insertBack(6.0);
        assertEquals(6.0, y.getFront().next.val, 1e-6);
        y.insertFront(4.1);
        assertEquals(4.1, y.getBack().prev.prev.val, 1e-6);
    }

    @Test
    public void testToString() {
        DoubleChain d = new DoubleChain(3.3);
        d.insertFront(4.6);
        d.insertFront(5.2);
        d.insertBack(2.1);
        d.insertBack(1.0);
        assertEquals("1.0, 2.1, 3.3, 4.6, 5.2", d.toString());
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestDoubleChain.class);
    }
}
