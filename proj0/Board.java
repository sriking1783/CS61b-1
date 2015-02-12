public class Board {
	private static boolean beEmpty;
	private Piece[][] board;
	private final int SIZE = 8;
	private boolean fireTurn;
	private boolean selected;
	private boolean moved;
	private boolean captured;
	private Piece selectedPiece;
	private int selectedX;
	private int selectedY;
	private int direction;

	/** Starts a GUI supported version of the game. */
	public static void main (String[] args) {
		Board b = new Board(false);
		b.getBoardGUI();
		while (b.winner() == null) {
			if (StdDrawPlus.mousePressed()) {
                double x = StdDrawPlus.mouseX();
                double y = StdDrawPlus.mouseY();
                // StdDrawPlus.filledSquare((int) x + .5, (int) y + .5, .5);
                if (b.canSelect((int) x, (int) y)) {
                	// StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                	b.select((int) x, (int) y);
                }
                b.updatePiecesGUI();
            }            
		}

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
		fireTurn = true;
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
			this.updatePiecesGUI();
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
	private void updatePiecesGUI() {
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
		if (withinBounds(x, y) && (board[x][y] != null)) {
			return board[x][y];
		}
		return null;
	}

	/** Tests to see if a certain (x, y) is within the bounds of the board */
	private boolean withinBounds(int x, int y) {
		if (x < SIZE && y < SIZE && x >= 0 && y >= 0) {
			return true;
		}
		return false;
	}

	/** Returns true if the square at (x, y) can be selected. */
	public boolean canSelect(int x, int y) {
		if (!withinBounds(x, y)) {
			return false;
		}
		if (board[x][y] != null && (board[x][y].isFire() == fireTurn)) {
			if (!selected || (selected && !moved)) {
				return true;
			}
		} else if (board[x][y] == null) {
			if ((selected && !moved)) {
				if (validMove(selectedX, selectedY, x, y)) {
					return true;
				}
			} else if ((selected && captured)) {
				if (validMove(selectedX, selectedY, x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	/** Returns true if the piece at (xi, yi) can either move to (xf, yf)
	  * or capture to (xf, yf), strictly from a geometry/piece-race point
	  * of view. validMove does not need to take into consideration whose
	  * turn it is or if a move has already been made, but rather can. 
	  * (Note: method is NOT required and will not be tested.) */
	private boolean validMove(int xi, int yi, int xf, int yf) {
		if (board[xf][yf] != null) {
			return false;
		}
		return directedValidMove(xi, yi, xf, yf);
	}

	private int directionFunc() {
		if (fireTurn) {
			direction = 1;
		} else {
			direction = -1;
		}
		return direction;
	}

	/** Checks to see if there is any valid move in the up direction */
	private boolean directedValidMove(int xi, int yi, int xf, int yf) {
		if ((xf == xi + (direction * 1)) && ((yf == yi + 1) || (yf == yi - 1))) {
			return true;
		} else if (board[xi + (direction * 1)][yi + 1] != null) {
			if ((xf == xi + (direction * 2)) && ((yf == yi + 2))) {
				return true;
			}
		} else if (board[xi + (direction * 1)][yi - 1] != null) {
			if ((xf == xi + (direction * 2)) && ((yf == yi - 2))) {
				return true;
			}
		}
		if (board[xi][yi].isKing()) {
			int reverse;
			if (direction == 1) {
				reverse = -1;
			} else {
				reverse = 1;
			}
			if ((xf == xi + (reverse * 1)) && ((yf == yi + 1) || (yf == yi - 1))) {
				return true;
			} else if (board[xi + (reverse * 1)][yi + 1] != null) {
				if ((xf == xi + (reverse * 2)) && ((yf == yi + 2))) {
					return true;
				}
			} else if (board[xi + (reverse * 1)][yi - 1] != null) {
				if ((xf == xi + (reverse * 2)) && ((yf == yi - 2))) {
					return true;
				}
			}
		}
		return false;
	}

	/** Selects the square at (x, y). This method assumes canSelect (x,y)
	  * returns true. Optionally, it is recommended to color the background
	  * of the selected square white on the GUI via the pen color function.
	  * For any piece to perform a capture, that piece must have been selected
	  * first. If you select a square with a piece, you are prepping that
	  * piece for movement. If you select an empty square (assuming canSelect
	  * returns true), you should move your most recently selected piece to
	  * that square. */
	public void select(int x, int y) {
		if (board[x][y] == null) {
			board[x][y].move(x, y);
		} else {
			if (!selected || (selected && !moved)) {
				selectedPiece = board[x][y];
				selectedX = x;
				selectedY = y;
				selected = true;
			}
		}
	}

	/** Places p at (x, y). If (x, y) is out of bounds or if p is null, does
	  * nothing. If another piece already exists at (x, y), p will replace
	  * that piece. (This method is potentially useful for creating specific
	  * test circumstances.) */
	public void place(Piece p, int x, int y) {
		if (withinBounds(x, y) && (p != null)) {
			// this.findAndRemove(p);
			board[x][y] = p;
		}
	}

	// /** If p already exists in the current Board, it removes it from the initial
	//   *	position. */
	// private void findAndRemove(Piece piece) {
	// 	for (int i = 0; i < SIZE; i++) {
	// 		for (int j = 0; j < SIZE; j++) {
	// 			if (piece == board[i][j]) {
	// 				Piece p = this.remove(i, j);
	// 			}
	// 		}
	// 	}
	// }

	/** Executes a remove. Returns the piece that was removed. If the input
	  * (x, y) is out of bounds, returns null and prints an appropriate
	  * message. If there is no piece at (x, y), returns null and prints an
	  * appropriate message. */
	public Piece remove(int x, int y) {
		if (!withinBounds(x, y)) {
			System.out.println("Either x or y is out of bounds.");
			return null;
		} else if (board[x][y] == null) {
			System.out.println("There is no piece in this position.");
			return null;
		}
		Piece removedPiece = board[x][y];
		board[x][y] = null;
		return removedPiece;
	}

	/** Returns whether or not the the current player can end their turn.
	  * To be able to end a turn, a piece must have moved or performed a
	  * capture. */
	public boolean canEndTurn() {
		if (moved || captured) {
			return true;
		}
		return false;
	}

	/** Called at the end of each turn. Handles switching of players and
	  * anything else that should happen at the end of a turn. */
	public void endTurn() {
		if (fireTurn) {
			fireTurn = false;
		} else {
			fireTurn = true;
		}
		selected = false;
		moved = false;
		captured = false;
		selectedPiece = null;
		selectedX = 0;
		selectedY = 0;
		direction = directionFunc();
	}

	/** Returns the winner of the game. "Fire", "Water", "No one" (tie /
	  * no pieces on the board), or null if the game is not yet over.
	  * Assume there is no stalemate situation in which the current player
	  * has pieces but cannot legally move any of them (In this event, just
	  * leave it at null). Determine the winner solely by the number of
	  * pieces belonging to each team. */
	public String winner() {
		int fireCount = 0;
		int waterCount = 0;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (board[i][j] != null) {
					if (board[i][j].isFire()) {
						fireCount += 1;
					}
					if (!board[i][j].isFire()) {
						waterCount += 1;
					}
				}
			}
		}
		if (fireCount == 0 && waterCount == 0) {
			return "No one";
		} else if (fireCount == 0) {
			return "Water";
		} else if (waterCount == 0) {
			return "Fire";
		}
		return null;
	}
}