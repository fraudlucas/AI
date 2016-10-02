package parisMetroMap;

public class NodeStation implements Comparable<NodeStation> {
	
	public NodeStation() {
	}
	
	public NodeStation(Station self, NodeStation previous) {
		setSelf(self);
		setPrevious(previous);
	}
	
	private NodeStation previous;
	private Station self;
	private int currentCost = 0;
	private int costToTheGoal = 0;
	
	public NodeStation getPrevious() {
		return previous;
	}
	public void setPrevious(NodeStation previous) {
		this.previous = previous;
	}
	public Station getSelf() {
		return self;
	}
	public void setSelf(Station self) {
		this.self = self;
	}
	public int getCurrentCost() {
		return currentCost;
	}
	public void setCurrentCost(int currentCost) {
		this.currentCost = currentCost;
	}
	public int getCostToTheGoal() {
		return costToTheGoal;
	}
	public void setCostToTheGoal(int costToTheGoal) {
		this.costToTheGoal = costToTheGoal;
	}
	
//	@Override
//	public int compare(NodeStation o1, NodeStation o2) {
//		// TODO Auto-generated method stub
//		int o1TotalCost = o1.getCostToTheGoal() + o1.getCostToTheGoal();
//		int o2TotalCost = o2.getCostToTheGoal() + o2.getCostToTheGoal();
//		
//		if (o1TotalCost < o2TotalCost) {
//			return -1;
//		} else if (o1TotalCost > o2TotalCost) {
//			return 1;
//		}
//		
//		return 0;
//	}

	@Override
	public int compareTo(NodeStation nodeToCompare) {
		// TODO Auto-generated method stub
		
		int totalCost = getCostToTheGoal() + getCurrentCost();
		int nodeToCompareTotalCost = nodeToCompare.getCostToTheGoal() + nodeToCompare.getCurrentCost();
		
		return totalCost - nodeToCompareTotalCost;
	}

}
