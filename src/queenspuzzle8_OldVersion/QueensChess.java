package queenspuzzle8_OldVersion;

/**
 * 
 * Heuristic is how many queens are under attack.
 * 
 * @author marcu
 *
 */

public class QueensChess {

	public final static int COLS = 8;

	private int[] queensPositions = new int[COLS];
	
	private int currentQueen = 0;
	
	private int queensUnderAttack = 0;
	
	private QueensChess previous;

	public String getString() {
		String str = "";
		str = " --------------- \n";
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < COLS; j++) {
				str += "|" + (getQueensPositions()[j] == i ? 'Q' : " ");
			}
			str += "| \n";
		}
		
		str += " ---------------";
		return str;
	}
	
	/*
	 * Check how many queens are under attack diagonally and horizontally.
	 * Considering (i,j) the current position and (i',j') to be checked, where "i" is column and "j" is line. 
	 * Checking if one queen are under attack diagonally using the following formulas:
	 * 		2 formulas for descending diagonals:
	 * 			1') i - j == i' - j'
	 * 		1 formula for ascending diagonals:
	 * 			2') i + j == i' + j'
	 * Checking if one queen are under attack horizontally using the following formula:
	 * 		3') j == j'
	 */
	public void checkQueensUnderAttack() {
		queensUnderAttack = 0;
		for (int i = 0; i < queensPositions.length; i++) {
			for (int j = 0; j < queensPositions.length; j++) {
				boolean flag = (j == i ? false : true); // if equals, it means they are in the same position... So, no checking.
				if (flag) {
					
					if (i - queensPositions[i] == j - queensPositions[j] // 1'
							|| i + queensPositions[i] == j + queensPositions[j] // 2'
							|| queensPositions[i] == queensPositions[j]) { // 3'
						queensUnderAttack++;
						break; // end this "for" and moves on to the next queen
					}
					
				}
			}
		}
	}
	
	public QueensChess getPrevious() {
		return previous;
	}

	public void setPrevious(QueensChess previous) {
		this.previous = previous;
	}	

	public int[] getQueensPositions() {
		return queensPositions;
	}

	public void setQueensPositions(int[] queensPositions) {
		this.queensPositions = queensPositions;
		checkQueensUnderAttack();
	}
	
	public int getCurrentQueen() {
		return currentQueen;
	}
	
	public void setCurrentQueen(int currentQueen) {
		this.currentQueen = currentQueen;
	}

	public int getQueensUnderAttack() {
		return queensUnderAttack;
	}

	public void setQueensUnderAttack(int queensUnderAttack) {
		this.queensUnderAttack = queensUnderAttack;
	}
	
}
