public class Board {
	private static boolean beEmpty;
	private Piece[][] board;
	private final int SIZE = 8;

	/** Starts a GUI supported version of the game. */
	public static void main (String[] args) {
		Board b = new Board(false);
		b.getBoardGUI();
	}

	/** The constructor for Board. If shouldBeEmpty is true, initializes
	  * a empty Board. Otherwise, initializes a Board with the default
	  * configuration. Note that an empty Board could possibly be useful
	  * for testing purposes. */
	public Board(boolean shouldBeEmpty) {
		board = new Piece[SIZE][SIZE];
		beEmpty = shouldBeEmpty;
		if (!shouldBeEmpty) {
			this.addInitPieces();
		}
	}

	/** Creates a board that represents the GUI */
	private void getBoardGUI() {
		StdDrawPlus.setXscale(0, SIZE);
        StdDrawPlus.setYscale(0, SIZE);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if ((i + j) % 2 == 0) {
					StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
				} else {
					StdDrawPlus.setPenColor(StdDrawPlus.RED);
				}
				StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
			}
		}
		if (!beEmpty) {
			this.addPiecesGUI();
		}
	}

	/** Determines where pieces should be added in the initial version
	  * of the board and adds them to both the GUI and algorithm board */
	private void addInitPieces() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if ((j == 0) && (i % 2 == 0)) {
					board[i][j] = new Piece(true, this, i, j, "pawn");
				} else if ((j == 1) && (i % 2 == 1)) {
					board[i][j] = new Piece(true, this, i, j, "shield");
				} else if ((j == 2) && (i % 2 == 0)) {
					board[i][j] = new Piece(true, this, i, j, "bomb");
				} else if ((j == 5) && (i % 2 == 1)) {
					board[i][j] = new Piece(false, this, i, j, "bomb");
				} else if ((j == 6) && (i % 2 == 0)) {
					board[i][j] = new Piece(false, this, i, j, "shield");
				} else if ((j == 7) && (i % 2 == 1)) {
					board[i][j] = new Piece(false, this, i, j, "pawn");
				}
			}
		}
	}

	/** Adds pieces to the GUI in all the appropriate tiles where there
	  * already exists a piece */
	private void addPiecesGUI() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] != null) {
					String type = "";
					if (board[i][j].isFire()) {
						if (board[i][j].isBomb()) {
							type = "bomb-fire";
						} else if (board[i][j].isShield()) {
							type = "shield-fire";
						} else {
							type = "pawn-fire";
						}
					} else {
						 if (board[i][j].isBomb()) {
							type = "bomb-water";
						} else if (board[i][j].isShield()) {
							type = "shield-water";
						} else {
							type = "pawn-water";
						}
					}
					String image = "img/" + type + ".png";
					StdDrawPlus.picture(i + .5, j + .5, image, 1, 1);
				}
			}
		}
	}

	/** Gets the piece at posistion (x, y) on the board, or null if there
	  * if there is no piece. If (x, y) are out of bounds, returns null. */
	public Piece pieceAt(int x, int y) {
		if ((x < SIZE && y < SIZE && x > 0 && y > 0) && (board[x][y] != null)) {
			return board[x][y];
		}
		return null;
	}

	/** Returns true if the square at (x, y) can be selected. */
	public boolean canSelect(int x, int y) {
		return false;
	}

	/** Returns true if the piece at (xi, yi) can either move to (xf, yf)
	  * or capture to (xf, yf), strictly from a geometry/piece-race point
	  * of view. validMove does not need to take into consideration whose
	  * turn it is or if a move has already been made, but rather can. 
	  * (Note: method is not required and will not be tested.) */
	public boolean validMove(int xi, int yi, int xf, int yf) {
		return false;
	}

	/** Selects the piece at (x, y) if possible. Optionally, it is recommended
	  * to color the background of the selected square white on the GUI via
	  * the pen color function. For any piece to perform a capture, that
	  * piece myst have been selected first. */
	public void select(int x, int y) {

	}

	/** Places p at (x, y). If (x, y) is out of bounds or if p is null, does
	  * nothing. If p already exists in the current Board, first removes it
	  * from its initial position. If another piece already exists at (x, y),
	  * p will replace that piece. (This method is potentially useful for creating
	  * specific test circumstances.) */
	public void place(Piece p, int x, int y) {
		if ((x < SIZE && y < SIZE && x > 0 && y > 0) || (p != null)) {
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (p == board[i][j]) {
						board[i][j] = null;
					}
				}
			}
			board[x][y] = p;
		}
	}

	/** Executes a remove. Returns the piece that was removed. If the input
	  * (x, y) is out of bounds, returns null and prints an appropriate
	  * message. If there is no piece at (x, y), returns null and prints an
	  * appropriate message. */
	public Piece remove(int x, int y) {
		return null;
	}

	/** Returns whether or not the the current player can end their turn.
	  * To be able to end a turn, a piece must have moved or performed a
	  * capture. */
	public boolean canEndTurn() {
		return false;
	}

	/** Called at the end of each turn. Handles switching of players and
	  * anything else that should happen at the end of a turn. */
	public void endTurn() {

	}

	/** Returns the winner of the game. "Fire", "Water", "No one" (tie /
	  * no pieces on the board), or null if the game is not yet over.
	  * Assume there is no stalemate situation in which the current player
	  * has pieces but cannot legally move any of them (In this event, just
	  * leave it at null). Determine the winner solely by the number of
	  * pieces belonging to each team. */
	public String winner() {
		return "";
	}
}