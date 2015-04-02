public class Board {

    public static final int SIZE = 8;
    // You can call this variable by Board.SIZE.

	private Piece[][] pieces;
    private boolean isFireTurn;

    public Board() {
        pieces = new Piece[SIZE][SIZE];
        isFireTurn = true;
    }

    /** Makes a custom Board. Not a completely safe operation because you could do
    * some bad stuff here, but this is for the purposes of testing out hash
    * codes so let's forgive the author. 
    */
    public Board(Piece[][] pieces) {
        this.pieces = pieces;
        isFireTurn = true;
    }

	@Override
	public boolean equals(Object o) {
        if (o != null && o instanceof Board) {
            Board obj = (Board) o;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (obj.pieces[i][j].equals(this.pieces[i][j])) {
                        return true;
                    }
                }
            }
        }
        return false; // YOUR CODE HERE
	}

    @Override
    public int hashCode() {
        return 6; // YOUR CODE HERE
    }
}