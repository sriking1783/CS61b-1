public class Piece {
	private boolean fireType;
	private boolean crowned;
	private Board board;
	private int xPos;
	private int yPos;
	private String pieceType;
	private boolean captures;
	
	/** Constructor for a piece. isFire determines whether the piece is a
	  * fire or water piece. b represents the Board that the piece is on
	  * (this will be only be used for the move() method). (x, y) is the
	  * position to initialize it at. type is a string representing the
	  * type of the Piece. It should be either "pawn", "bomb", or "shield".
	  * (Don’t worry about bounds checking). The new piece should not be
	  * a king. */
	public Piece(boolean isFire, Board b, int x, int y, String type) {
		fireType = isFire;
		board = b;
		xPos = x;
		yPos = y;
		pieceType = type;
		crowned = false;
		captures = false;
	}

	/** Returns whether or not the piece is a fire piece. */
	public boolean isFire() {
		if (this.fireType) {
			return true;
		}
		return false;
	}

	/** Returns 0 if the piece is a fire piece, or 1 if the piece is a
	  * water piece. This might seem redundant with isFire(), and there
	  * are better ways, but for this 0th project, we'll just provide
	  * these both as tools that you might want to use later. */
	public int side() {
		if (this.fireType) {
			return 0;
		}
		return 1;
	}

	/** Returns whether or not the piece has been crowned. */
	public boolean isKing() {
		if (crowned) {
			return true;
		}
		return false;
	}

	/** Returns whether or not the piece is a Bomb Piece. */
	public boolean isBomb() {
		if (this.pieceType.equals("bomb")) {
			return true;
		}
		return false;
	}

	/** Returns whether or not the piece is a Bomb Piece. */
	public boolean isShield() {
		if (this.pieceType.equals("shield")) {
			return true;
		}
		return false;
	}

	/** Assumes this Piece's movement from its current position to (x, y)
	  * is valid. Moves the piece to (x, y), capturing any intermediate
	  * piece if applicable. This will be a difficult method to write. */
	public void move(int x, int y) {
		if (this.isFire() && this.x == 7 && !this.isKing()) {
			this.crowned = true;
		}
		else if (!this.isFire() && this.x == 0 && !this.isKing()) {
			this.crowned = true;
		}
		board.place(this, x, y);
		board.remove(xPos, yPos).captures = true;
		if ((Math.abs(x - xPos) == 2) && (Math.abs(y - yPos) == 2)) {
			int[] middlePoint = midpoint(x, y);
			board.remove(middlePoint[0], middlePoint[1]);
		}
		if (this.isBomb()) {
			board.remove(x, y);
			for (int i = x - 1; i < x + 1; i += 2) {
				for (int j = y - 1; j < y + 1; j += 2) {
					if (board.pieceAt(i, j) != null && !board.pieceAt(i, j).isShield()) {
						board.remove(i, j);
					}
				}
			}
		}
		xPos = x;
		yPos = y;
	}

	private int[] midpoint(int x, int y) {
		int a = (x + xPos) / 2;
		int b = (y + yPos) / 2;
		return new int[] {a, b};
	}

	/** Returns whether or not this Piece has captured another piece this
	  * turn. */
	public boolean hasCaptured() {
		if (captures) {
			return true;
		}
		return false;
	}

	/** Called at the end of each turn on the Piece that moved. Makes sure
	  * the piece's hasCaptured() value return to false. */
	public void doneCapturing() {
		captures = false;
	}
}