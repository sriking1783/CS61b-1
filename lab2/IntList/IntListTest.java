import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Example test that verifies correctness of the IntList.list static 
     *  method. The main point of this is to convince you that 
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test 
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareList() {
      IntList L = IntList.list(1, 2, 3);
      IntList.dSquareList(L);
      assertEquals(IntList.list(1, 4, 9), L);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.  
     * 
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with 
     *  IntList empty = IntList.list(). 
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A. 
     */

    @Test
    public void testSquareListRecursive() {
        IntList empty = IntList.list();
        assertEquals(null, empty);

        IntList L = IntList.list(1, 2, 3);
        IntList X = IntList.squareListRecursive(L);
        assertEquals(IntList.list(1, 4, 9), X);
        assertEquals(IntList.list(1, 2, 3), L);
        assertEquals(false, X == L);


        IntList A = IntList.list(2, 4, 6, 8);
        IntList B = IntList.squareListRecursive(A);
        assertEquals(IntList.list(4, 16, 36, 64), B);
        assertEquals(IntList.list(2, 4, 6, 8), A);
        assertEquals(false, A == B);
    }

    @Test
    public void testDcatenate() {
        IntList empty = IntList.list();
        IntList A = IntList.list(1, 2, 3);
        IntList B = IntList.list(4, 5, 6, 7);
        IntList C = IntList.list(2, 4, 6, 8, 10);

        // checks if the method accounts for empty IntLists
        assertEquals(IntList.list(1, 2, 3), IntList.dcatenate(empty, A));
        assertEquals(IntList.list(1, 2, 3), IntList.dcatenate(A, empty));

        // checks if the method is destructive and works correctly
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7), IntList.dcatenate(A, B));
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7), A);

        // checks if the method is detructive
        // last line confirms that changing B does not affect A (from previous part)
        assertEquals(IntList.list(4, 5, 6, 7, 2, 4, 6, 8, 10), IntList.dcatenate(B, C));
        assertEquals(IntList.list(4, 5, 6, 7, 2, 4, 6, 8, 10), B);
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7), A);

        // checks that appropriate changes where made to each list by the end (destructive)
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7, 4, 5, 6, 7, 2, 4, 6, 8, 10), IntList.dcatenate(A, B));
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7, 4, 5, 6, 7, 2, 4, 6, 8, 10), A);
        assertEquals(IntList.list(4, 5, 6, 7, 2, 4, 6, 8, 10), B);
        assertEquals(IntList.list(2, 4, 6, 8, 10), C);
    }

    @Test
    public void testCatenate() {
        IntList empty = IntList.list();
        IntList A = IntList.list(1, 2, 3);
        IntList B = IntList.list(4, 5, 6, 7);
        IntList C = IntList.list(2, 4, 6, 8, 10);

        // checks if the method accounts for empty IntLists
        assertEquals(IntList.list(1, 2, 3), IntList.catenate(empty, A));
        assertEquals(IntList.list(1, 2, 3), IntList.catenate(A, empty));

        // checks if the method is non-destructive and works correctly
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7), IntList.catenate(A, B));
        assertEquals(IntList.list(1, 2, 3), A);
        assertEquals(IntList.list(4, 5, 6, 7), B);

        // checks if the method is non-destructive and works correctly
        assertEquals(IntList.list(4, 5, 6, 7, 2, 4, 6, 8, 10), IntList.catenate(B, C));
        assertEquals(IntList.list(4, 5, 6, 7), B);
        assertEquals(IntList.list(2, 4, 6, 8, 10), C);

        // checks that each list has not changed by the end (non-destructive)
        assertEquals(IntList.list(2, 4, 6, 8, 10, 1, 2, 3), IntList.catenate(C, A));
        assertEquals(IntList.list(1, 2, 3), A);
        assertEquals(IntList.list(4, 5, 6, 7), B);
        assertEquals(IntList.list(2, 4, 6, 8, 10), C);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(IntListTest.class);
    }       
}   
