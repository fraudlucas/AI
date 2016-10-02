package puzzle8;

/**
 * 
 * @author Marcus Lucas Abreu de Araujo Falcão
 *
 */

public class Board {

	private final static int ROWS = 3;
	private final static int COLS = 3;
	
	private int currentRow = 0;
	private int currentCol = 0;

	private char[][] board = new char[ROWS][COLS];
	private Board previous;
	
	public Board() {
		this(null);
	}

	public Board(Board previous) {
		this.setPrevious(previous);
	}

	public char[][] getBoard() {
		return board;
	}
	
	public void setBoard(char[][] board) {
		this.board = board;
		checkEmptyBlock();
	}
	
	public Board getPrevious() {
		return previous;
	}

	public void setPrevious(Board previous) {
		this.previous = previous;
	}
	
	public String getString() {
		String str = "";
		str = "  ----------- \n";
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				str += " | " + (getBoard()[i][j] == '0' ? " " : getBoard()[i][j]);
			}
			str += " | \n";
		}
		str += "  ----------- \n";
		return str;
	}

	/*
	 * If the empty block can slide to the right, it is slided "and can returns true"
	 */
	public boolean slideRight() {
		if (currentCol < COLS - 1) {
			board[currentRow][currentCol] = board[currentRow][currentCol + 1];
			currentCol++;
			board[currentRow][currentCol] = '0';
			return true;
		}
		
		return false;
	}

	/*
	 * If the empty block can slide to the left, it is slided "and can returns true"
	 */
	public boolean slideLeft() {
		if (currentCol > 0) {
			board[currentRow][currentCol] = board[currentRow][currentCol - 1];
			currentCol--;
			board[currentRow][currentCol] = '0';
			return true;
		}
		
		return false;
	}
	
	/*
	 * If the empty block can slide up, it is slided "and can returns true"
	 */
	public boolean slideUp() {
		if (currentRow > 0) {
			board[currentRow][currentCol] = board[currentRow - 1][currentCol];
			currentRow--;
			board[currentRow][currentCol] = '0';
			return true;
		}
		
		return false;
	}
	
	/*
	 * If the empty block can slide down, it is slided "and can returns true"
	 */
	public boolean slideDown() {
		if (currentRow < ROWS - 1) {
			board[currentRow][currentCol] = board[currentRow + 1][currentCol];
			currentRow++;
			board[currentRow][currentCol] = '0';
			return true;
		}
		
		return false;
	}
	
	public void checkEmptyBlock() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (board[i][j] == '0') {
					currentRow = i;
					currentCol = j;
				}
			}
		}
	}


}
