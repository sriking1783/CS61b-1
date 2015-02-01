/*
 * JUnit tests for the Triangle class
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author melaniecebula
 */
public class TriangleTest {
  /**  We've already created a testScalene method.  Please fill in testEquilateral, and additionally
   *   create tests for Isosceles, Negative Sides, and Invalid sides
   **/

    @Test
    public void testScalene() {
        Triangle t = new Triangle(30, 40, 50);
        String result = t.triangleType();
        assertEquals("Scalene", result);
    }

    @Test
    public void testEquilateral() {
        Triangle t1 = new Triangle(30, 30, 30);
        String result1 = t1.triangleType();
        assertEquals("Equilateral", result1);
    }

    @Test
    public void testIsosceles() {
        Triangle t1 = new Triangle(30, 40, 30);
        String result1 = t1.triangleType();
        assertEquals("Isosceles", result1);

        Triangle t2 = new Triangle(30, 30, 40);
        String result2 = t2.triangleType();
        assertEquals("Isosceles", result2);

        Triangle t3 = new Triangle(40, 30, 30);
        String result3 = t3.triangleType();
        assertEquals("Isosceles", result3);
    }

    @Test
    public void testNegativeSides() {
        Triangle t = new Triangle(30, -40, 50);
        String result = t.triangleType();
        assertEquals("At least one length is less than 0!", result);
    }

    @Test
    public void testInvalidSides() {
        Triangle t = new Triangle(30, 40, 71);
        String result = t.triangleType();
        String expected = "The lengths of the triangles do not form a valid triangle!";
        assertEquals(expected, result);
    }

    public static void main(String[] args) {
      jh61b.junit.textui.runClasses(TriangleTest.class);
    }
}
