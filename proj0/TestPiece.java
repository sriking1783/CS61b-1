import static org.junit.Assert.*;

import org.junit.Test;

/** Perform tests of the Piece class
 */

public class TestPiece {

    /** Tests all of the basic methods of Piece */
    @Test
    public void testBasicMethods() {
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

    @Test
    public void testSimpleMove() {
        Board b = new Board(true);
        Piece px = new Piece(true, b, 3, 3, "shield");
        Piece py = new Piece(false, b, 4, 4, "pawn");
        b.place(px, 3, 3);
        b.place(py, 4, 4);
        assertTrue(b.pieceAt(3, 3) != null);
        assertTrue(b.pieceAt(4, 4) != null);
        px.move(4, 2);
        assertTrue(b.pieceAt(3, 3) == null);
        assertEquals(px, b.pieceAt(4, 2));

        /** Testing Fire Kinging */
        Piece preKing = new Piece (true, b, 6, 6, "pawn");
        b.place(preKing, 6, 6);
        assertTrue(b.pieceAt(6, 6) != null);
        assertFalse(b.pieceAt(6, 6).isKing());
        preKing.move(7, 7);
        assertTrue(b.pieceAt(6, 6) == null);
        assertEquals(preKing, b.pieceAt(7, 7));
        assertTrue(preKing.isKing());

        Piece preKing2 = new Piece (true, b, 6, 6, "pawn");
        b.place(preKing2, 6, 6);
        assertTrue(b.pieceAt(6, 6) != null);
        assertFalse(b.pieceAt(6, 6).isKing());
        preKing2.move(7, 5);
        assertTrue(b.pieceAt(6, 6) == null);
        assertEquals(preKing2, b.pieceAt(7, 5));
        assertTrue(preKing2.isKing());

        /** Testing Water Kinging */
        Piece preKingW = new Piece (false, b, 1, 1, "pawn");
        b.place(preKingW, 1, 1);
        assertTrue(b.pieceAt(1, 1) != null);
        assertFalse(b.pieceAt(1, 1).isKing());
        preKingW.move(0, 0);
        assertTrue(b.pieceAt(1, 1) == null);
        assertEquals(preKingW, b.pieceAt(0, 0));
        assertTrue(preKingW.isKing());

        Piece preKingW2 = new Piece (false, b, 1, 1, "pawn");
        b.place(preKingW2, 1, 1);
        assertTrue(b.pieceAt(1, 1) != null);
        assertFalse(b.pieceAt(1, 1).isKing());
        preKingW2.move(0, 2);
        assertTrue(b.pieceAt(1, 1) == null);
        assertEquals(preKingW2, b.pieceAt(0, 2));
        assertTrue(preKingW2.isKing());
    }

    @Test
    public void testCaptureMove() {
        Board b = new Board(true);
        Piece px = new Piece(true, b, 3, 3, "shield");
        Piece py = new Piece(false, b, 4, 4, "pawn");
        b.place(px, 3, 3);
        b.place(py, 4, 4);
        assertTrue(b.pieceAt(3, 3) != null);
        assertTrue(b.pieceAt(4, 4) != null);
        px.move(5, 5);
        assertTrue(b.pieceAt(3, 3) == null);
        assertEquals(px, b.pieceAt(5, 5));
        assertTrue(px.hasCaptured());
        assertEquals(null, b.pieceAt(4, 4));

        /** Testing Fire Kinging after Capture */
        Piece preKing = new Piece (true, b, 5, 5, "pawn");
        Piece captured = new Piece (false, b, 6, 6, "shield");
        b.place(preKing, 5, 5);
        b.place(captured, 6, 6);
        assertTrue(b.pieceAt(5, 5) != null);
        assertFalse(b.pieceAt(5, 5).isKing());
        assertTrue(b.pieceAt(6, 6) != null);
        preKing.move(7, 7);
        assertTrue(b.pieceAt(5, 5) == null);
        assertEquals(preKing, b.pieceAt(7, 7));
        assertEquals(null, b.pieceAt(6, 6));
        assertTrue(preKing.hasCaptured());
        assertTrue(preKing.isKing());

        /** Should not skip if no piece */
        Piece preKing2 = new Piece (true, b, 5, 1, "pawn");
        b.place(preKing2, 5, 1);
        assertTrue(b.pieceAt(5, 1) != null);
        assertFalse(b.pieceAt(5, 1).isKing());
        preKing2.move(7, 3);
        assertTrue(b.pieceAt(5, 1) != null);
        assertFalse(preKing2.equals(b.pieceAt(7, 3)));
        assertFalse(preKing2.hasCaptured());
        assertFalse(preKing2.isKing());

        // Piece preKing2 = new Piece (true, b, 6, 6, "pawn");
        // b.place(preKing2, 6, 6);
        // assertTrue(b.pieceAt(6, 6) != null);
        // assertFalse(b.pieceAt(6, 6).isKing());
        // preKing2.move(7, 5);
        // assertTrue(b.pieceAt(6, 6) == null);
        // assertEquals(preKing2, b.pieceAt(7, 5));
        // assertTrue(preKing2.isKing());

        /** Testing Water Kinging after Capture */
        // Piece preKingW = new Piece (false, b, 1, 1, "pawn");
        // b.place(preKingW, 1, 1);
        // assertTrue(b.pieceAt(1, 1) != null);
        // assertFalse(b.pieceAt(1, 1).isKing());
        // preKingW.move(0, 0);
        // assertTrue(b.pieceAt(1, 1) == null);
        // assertEquals(preKingW, b.pieceAt(0, 0));
        // assertTrue(preKingW.isKing());

        // Piece preKingW2 = new Piece (false, b, 1, 1, "pawn");
        // b.place(preKingW2, 1, 1);
        // assertTrue(b.pieceAt(1, 1) != null);
        // assertFalse(b.pieceAt(1, 1).isKing());
        // preKingW2.move(0, 2);
        // assertTrue(b.pieceAt(1, 1) == null);
        // assertEquals(preKingW2, b.pieceAt(0, 2));
        // assertTrue(preKingW2.isKing());
    }

    @Test
    public void testCapturing() {
       
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }
}
