package queenspuzzle8_VersionAlmostFinal;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

public class QueensPuzzle8 {

	protected PriorityQueue<QueensChess> queensChessList = new PriorityQueue<QueensChess>(); // Items to be used in the search.
	protected Stack<QueensChess> trackedList = new Stack<QueensChess>(); // List with the solution tracked.
	protected Stack<QueensChess> checkedList = new Stack<QueensChess>(); // Items (nodes) already used.
	
	public static void main(String[] args) {
		int[] initialQueensPositions = new int[] {0,1,2,3,4,5,6,7};
		QueensPuzzle8 queensPuzzle8 = new QueensPuzzle8();
		
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
			queensChess = queensChessList.poll();

			flag = (queensChess.getQueensUnderAttack() == 0 ? true : false);
			if (!flag) {
				generatingNodesFrom(queensChess);
				Arrays.sort(queensChessList.toArray());
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

	public void generatingNodesFrom(QueensChess queensChess) {
		int current = queensChess.getCurrentQueen();
		queensChessList = new PriorityQueue<QueensChess>();
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
	
	public void trackSolution(QueensChess queensChess) {
		QueensChess previous = (QueensChess) queensChess.getPrevious();
		trackedList.push(queensChess);
		
		if (previous != null) {
			trackSolution(previous);
		}
	}
	
}
