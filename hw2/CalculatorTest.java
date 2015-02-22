import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    /* Do not change this to be private. For silly testing reasons it is public. */
    public Calculator tester;

    /**
     * setUp() performs setup work for your Calculator.  In short, we 
     * initialize the appropriate Calculator for you to work with.
     * By default, we have set up the Staff Calculator for you to test your 
     * tests.  To use your unit tests for your own implementation, comment 
     * out the StaffCalculator() line and uncomment the Calculator() line.
     **/
    @Before
    public void setUp() {
        tester = new StaffCalculator(); // Comment me out to test your Calculator
        // tester = new Calculator();   // Un-comment me to test your Calculator
    }

    // TASK 1: WRITE JUNIT TESTS
    @Test
    public void testAdd1() {
        int x = 8;
        int y = 4;
        int z = 2;
        assertEquals(12, tester.add(x, y));
        assertEquals(10, tester.add(x, z));
        assertEquals(6, tester.add(y, z));
    }

    @Test
    public void testAdd2() {
        int x = 3;
        int y = 0;
        int z = -1;
        assertEquals(3, tester.add(x, y));
        assertEquals(2, tester.add(x, z));
        assertEquals(-1, tester.add(y, z));
    }

    @Test
    public void testAdd3() {
        int x = -1;
        int y = -4;
        int z = 7;
        assertEquals(-5, tester.add(x, y));
        assertEquals(6, tester.add(x, z));
        assertEquals(3, tester.add(y, z));
    }
    
    @Test
    public void testMultiply1() {
        int x = 8;
        int y = 4;
        int z = 2;
        assertEquals(32, tester.multiply(x, y));
        assertEquals(16, tester.multiply(x, z));
        assertEquals(8, tester.multiply(y, z));
    }

    @Test
    public void testMultiply2() {
        int x = 3;
        int y = 0;
        int z = -1;
        assertEquals(0, tester.multiply(x, y));
        assertEquals(-3, tester.multiply(x, z));
        assertEquals(0, tester.multiply(y, z));
    }

    @Test
    public void testMultiply3() {
        int x = -1;
        int y = -4;
        int z = 7;
        assertEquals(4, tester.multiply(x, y));
        assertEquals(-7, tester.multiply(x, z));
        assertEquals(-28, tester.multiply(y, z));
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       
}