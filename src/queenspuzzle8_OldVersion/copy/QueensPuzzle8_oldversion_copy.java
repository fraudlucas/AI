package queenspuzzle8_OldVersion.copy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

import queenspuzzle8_VersionAlmostFinal.QueensChess;

public class QueensPuzzle8_oldversion_copy {

	protected LinkedList<QueensChess> queensChessList = new LinkedList<QueensChess>(); // Items to be used in the search.
	protected Stack<QueensChess> trackedList = new Stack<QueensChess>(); // List with the solution tracked.
	protected Stack<QueensChess> checkedList = new Stack<QueensChess>(); // Items (nodes) already used.
	
	public static void main(String[] args) {
		int[] initialQueensPositions = new int[] {0,1,2,3,4,5,6,7};
		QueensPuzzle8_oldversion_copy queensPuzzle8 = new QueensPuzzle8_oldversion_copy();
		
		QueensChess first = new QueensChess();
		first.setQueensPositions(initialQueensPositions);
		
		System.out.println("Solution:");
		
		queensPuzzle8.searching(first);
	}
	
	public void searching(QueensChess first) {
		boolean flag = false;
		QueensChess queensChess = new QueensChess();
		queensChessList.add(first);
		
		
		do {
			queensChess = pickingTheBestSolution();

			flag = (queensChess.getQueensUnderAttack() == 0 ? true : false);
			if (!flag) {
				generatingNodesFrom(queensChess);
			}


		} while (!flag && !queensChessList.isEmpty());
		
		if (flag) { // If the goal is reached, print out the solution.
			trackSolution(queensChess);
			int test = 1;
			while (!trackedList.empty()) {
				QueensChess t = trackedList.pop();
				System.out.println(test++ + ")");
				System.out.println(t.getString());
			}
		}
				
	}
	
	public QueensChess pickingTheBestSolution() {
		QueensChess bestSolution = queensChessList.removeFirst();
		QueensChess toBeChecked = null;
		
		while (!queensChessList.isEmpty()) {
			toBeChecked = queensChessList.removeFirst();
			if (toBeChecked.getQueensUnderAttack() < bestSolution.getQueensUnderAttack()) {
				bestSolution = toBeChecked;
			}
		}
		
		return bestSolution;
	}
	
	public void generatingNodesFrom(QueensChess queensChess) {
		int current = queensChess.getCurrentQueen();
		for (int i = 0; i < QueensChess.COLS; i++) {
			int[] clone = queensChess.getQueensPositions().clone(); // cloning (passing by value) the array from the queensChess
			
			if (current == i) continue; 
			
			clone[current] = i;
			
			QueensChess newQueensChess = new QueensChess();
			newQueensChess.setQueensPositions(clone);
			newQueensChess.setCurrentQueen((current + 1 == QueensChess.COLS ? current + 1 - QueensChess.COLS : current + 1));
			newQueensChess.checkQueensUnderAttack();
			newQueensChess.setPrevious(queensChess);
			
			if (!isAParentNode(newQueensChess, queensChess)) {
				queensChessList.add(newQueensChess);
			}
		}
	}
	
	public boolean isAParentNode(QueensChess node, QueensChess previous) {

		if (previous.getPrevious() == null) {
			return false;
		} else if (Arrays.equals(previous.getQueensPositions(), node.getQueensPositions())) {
			return true;
		} else {
			return isAParentNode(node, previous.getPrevious());
		}

	}
	
	public boolean hasBeenUsed(QueensChess queensChess) {
		for (QueensChess checked : checkedList) {
			if (Arrays.equals(queensChess.getQueensPositions(), checked.getQueensPositions())) {
				return true;
			}
		}
		
		return false;
	}
	
	public void trackSolution(QueensChess queensChess) {
		QueensChess previous = (QueensChess) queensChess.getPrevious();
		trackedList.push(queensChess);
		
		if (previous != null) {
			trackSolution(previous);
		}
	}
	
}
