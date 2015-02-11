import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Piece class
 */

public class TestPiece {

    /** Tests all of the basic methods of Piece */
    @Test
    public void testMethods() {
        Piece p1 = new Piece(true, new Board(true), 0, 0, "bomb");
        Piece p2 = new Piece(false, new Board(true), 3, 4, "shield");
        assertEquals(true, p1.isFire());
        assertEquals(false, p2.isFire());
        assertEquals(0, p1.side());
        assertEquals(1, p2.side());
        assertEquals(false, p1.isKing());
        assertEquals(false, p2.isKing());
        assertEquals(true, p1.isBomb());
        assertEquals(false, p2.isBomb());
        assertEquals(true, p2.isShield());
        assertEquals(false, p1.isShield());
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }
}
