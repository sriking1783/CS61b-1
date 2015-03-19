import org.junit.Test;
import static org.junit.Assert.*;

public class BSTMapTest {

    // @Test
    // public void testMapFunctions() {
    //     BSTMap<Integer, String> bMap = new BSTMap<Integer, String>();
    //     bMap.put(10, "apple");
    //     assertEquals(1, bMap.size());
    //     assertEquals("apple", bMap.get(10));
    //     bMap.put(20, "cherry");
    //     assertEquals("cherry", bMap.get(20));
    //     assertEquals(2, bMap.size());
    //     bMap.put(15, "banana");
    //     assertEquals("banana", bMap.get(15));
    //     assertEquals(3, bMap.size());
    //     assertEquals(true, bMap.containsKey(20));
    //     assertEquals(true, bMap.containsKey(15));
    //     assertEquals(false, bMap.containsKey(12));
    //     bMap.clear();
    //     assertEquals(0, bMap.size());
    //     assertEquals(false, bMap.containsKey(10));
    //     assertEquals(false, bMap.containsKey(15));
    // }

    // @Test
    // public void testPrintInOrder() {
    //     BSTMap<Integer, String> bMap = new BSTMap<Integer, String>();
    //     bMap.put(10, "apple");
    //     bMap.put(20, "cherry");
    //     bMap.put(15, "banana");
    //     bMap.put(12, "guava");
    //     bMap.put(5, "grapefruit");
    //     bMap.printInOrder();
    // }

    @Test
    public void testSpeed() {
        BSTMap<Integer, String> bMap = new BSTMap<Integer, String>();
        for (int i = 0; i < 1000000000; i++) {
            bMap.put(0, "hi");
        }
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(BSTMapTest.class);
    }

}
