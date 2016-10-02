package puzzle8;

/**
 * 
 * @author Marcus Lucas Abreu de Araujo Falcão
 * 
 */

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Puzzle8 {

	protected LinkedList<Board> boardsList = new LinkedList<Board>(); // Items to be used in the search.
	protected Stack<Board> trackedList = new Stack<Board>(); // List with the solution tracked.
	protected Stack<Board> checkedList = new Stack<Board>(); // Items (nodes) already used.
	protected static char[][] goal = new char[][]{
		{'0', '1', '2'},
		{'3', '4', '5'},
		{'6', '7', '8'}
	};
	
	public static void main(String[] args) {
//		char[][] initialBoard = new char[][]{
//			{'3', '1', '2'},
//			{'4', '7', '5'},
//			{'6', '8', '0'}
//		};
		char[][] initialBoard = new char[][]{
			{'8', '7', '6'},
			{'5', '4', '3'},
			{'2', '1', '0'}
		};
		Puzzle8 puzzle8 = new Puzzle8();
		Board first = new Board();
		first.setBoard(initialBoard);
		
		System.out.println("Solution:");
		
		puzzle8.searching(first);
		
	}

	public void searching(Board first) {
		boolean flag = false;
		Board board = new Board();
		boardsList.add(first);
		
//		int test = 0;
		
		do {
			board = boardsList.removeFirst();
			
			if (!hasBeenUsed(board)) {
				flag = Arrays.deepEquals(board.getBoard(), goal);
				if (!flag) {
					generatingNodesFrom(board);
				}
				
//				System.out.println("Nodes checked: " + test++);
				checkedList.push(board);
			}

		} while (!flag && !boardsList.isEmpty());
		
		if (flag) { // If the goal is reached, print out the solution.
			trackSolution(board);
			int teste = 1;
			while (!trackedList.empty()) {
				Board t = trackedList.pop();
				System.out.println(teste++ + ")");
				System.out.println(t.getString());
			}
		} else {
			System.out.println("There is no solution!");
		}
				
	}

	public void generatingNodesFrom(Board board) {
		char[] a = new char[]{'d', 'u', 'r', 'l'};
		
		for (char c : a) {
			Board newboard = generates(board, c);
			if (newboard != null) {
				boardsList.add(newboard);
			}
		}
	}
	
	public Board generates(Board board, char a) {
		Board newboard = new Board();
		boolean flag = false;
		int length = board.getBoard().length;
		
		char[][] clone = new char[length][]; // cloning (passing by value) the 2d array from the board
		for (int i = 0; i < length; i++) {
			clone[i] = board.getBoard()[i].clone();
		}
		
		newboard.setBoard(clone);
		
		switch (a) {
		case 'd':
			flag = newboard.slideDown();
			break;
			
		case 'l':
			flag = newboard.slideLeft();
			break;
			
		case 'r':
			flag = newboard.slideRight();
			break;
			
		case 'u':
			flag = newboard.slideUp();
			break;
		}
		
		
		if (flag) {
			newboard.setPrevious(board);
			return newboard;
		} else {
			return null;
		}
	}
	

	public boolean hasBeenUsed(Board board) {
		for (Board checked : checkedList) {
			if (Arrays.deepEquals(board.getBoard(), checked.getBoard()))
				return true;
		}
		
		return false;
	}
	

	public void trackSolution(Board board) {
		Board previous = (Board) board.getPrevious();
		trackedList.push(board);
		
		if (previous != null) {
			trackSolution(previous);
		}
	}

}
