import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Board class
 */

public class TestBoard {

    /** Tests the PieceAt method of Board */
    @Test
    public void testPieceAt() {
        Board b1 = new Board(false);
        Piece p1 = b1.pieceAt(2, 6);
        Piece p2 = b1.pieceAt(6, 2);
        assertEquals(false, p1.isFire());
        assertEquals(true, p1.isShield());
        assertEquals(true, p2.isFire());
        assertEquals(true, p2.isBomb());

        // Null tests
        Piece pNull1 = b1.pieceAt(3, 3); 
        Piece pNull2 = b1.pieceAt(1, 9);
        Piece pNull3 = b1.pieceAt(8, 0);
        Piece pNull4 = b1.pieceAt(-1, 0);
        Piece pNull5 = b1.pieceAt(3, -2);
        assertEquals(null, pNull1);
        assertEquals(null, pNull2);
        assertEquals(null, pNull3);
        assertEquals(null, pNull4);
        assertEquals(null, pNull5);

        // Test out empty board too
        Board b2 = new Board(true);
        Piece pEmt1 = b2.pieceAt(3, 7);
        Piece pEmt2 = b2.pieceAt(4, 5);
        assertEquals(null, pEmt1);
        assertEquals(null, pEmt2);
    }

    /** Tests the Place method of Board */
    @Test
    public void testPlace() {
        Board b1 = new Board(false);
        b1.place(new Piece(false, b1, 5, 3, "shield"), 5, 3);
        Piece p1 = b1.pieceAt(5, 3);
        assertEquals(false, p1 == null);
        assertEquals(false, p1.isFire());
        assertEquals(true, !(p1.isFire()));
        assertEquals(false, p1.isBomb());
        assertEquals(true, p1.isShield());

        b1.place(new Piece(true, b1, 3, 5, "pawn"), 3, 5);
        Piece p2 = b1.pieceAt(3, 5);
        assertEquals(true, p2.isFire());
        assertEquals(false, !(p2.isFire()));
        assertEquals(false, p2.isBomb());
        assertEquals(false, p2.isShield());

        b1.place(new Piece(true, b1, 5, 3, "bomb"), 5, 3);
        Piece p3 = b1.pieceAt(5, 3);
        assertEquals(true, p3.isFire());
        assertEquals(false, !(p3.isFire()));
        assertEquals(true, p3.isBomb());
        assertEquals(false, p3.isShield());

        // b1.place(new Piece(false, b1, 2, 0, "shield"), 8, 0);
        // Piece p4 = b1.pieceAt(2, 0);
        // assertEquals(true, p4.isFire());
        // assertEquals(false, !(p4.isFire()));
        // assertEquals(false, p4.isBomb());
        // assertEquals(false, p4.isShield());
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}
