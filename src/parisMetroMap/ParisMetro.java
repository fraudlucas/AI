package parisMetroMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

public class ParisMetro {
	public final static int STATIONS = 14;
	protected static int[][] minimumDistanceStations;
	
	protected static HashMap<String, Station> parisMap = new HashMap<String, Station>();
	protected static HashMap<String, Integer> parisMapRealDistances = new HashMap<String, Integer>();
	protected PriorityQueue<NodeStation> nodeStationList = new PriorityQueue<NodeStation>();
	protected Stack<NodeStation> trackedList = new Stack<NodeStation>();
	
	protected static Station goal;
	
	public static void main(String[] args) {
		
		loadMinimumDistanceStations();
		loadStationsRealDistances();
		loadStationsInfo();
		
		String initial = "E1";
		String finalGoal = "E2";
		
		goal = parisMap.get(finalGoal);
		
		Station initialStation = parisMap.get(initial);
		NodeStation first = new NodeStation(initialStation, null);
		
		int costToTheGoal = minimumDistanceStations[initialStation.getId()][goal.getId()];
		first.setCostToTheGoal(costToTheGoal);
		
		System.out.println("Initial station: " + first.getSelf().getName());
		System.out.println("Final Goal: " + goal.getName());
		
		ParisMetro parisMetro = new ParisMetro();
		parisMetro.searching(first);
		
		
	}
	
	public void searching(NodeStation first) {
		boolean flag = false;

		NodeStation nodeStation;
		nodeStationList.add(first);
		NodeStation foundGoal = null;
		
		do {
			
			nodeStation = nodeStationList.poll();
			
			flag = (nodeStation.getCostToTheGoal() == 0 ? true : false);
			if (!flag) {
				generatingNodesFrom(nodeStation);
				Arrays.sort(nodeStationList.toArray());
			} else {
				foundGoal = nodeStation;
			}
		
		} while (!flag); 

		if (flag) { // If the goal is reached, print out the solution.
			trackSolution(foundGoal);
			int test = 1;
			while (!trackedList.empty()) {
				NodeStation t = trackedList.pop();
				System.out.println(test++ + ")");
				System.out.println(t.getSelf().getName() + " -- Current Cost: " + t.getCurrentCost() + " -- Cost to the goal: " + t.getCostToTheGoal() );
			}
		}
		
		System.out.println("Solution: " + foundGoal.getSelf().getName() + " -- TOTAL COST: "
				+ foundGoal.getCurrentCost() + " -- TRIP TIME ~= " + (foundGoal.getCurrentCost() * 2) + " min.");
	}
	
	public void trackSolution(NodeStation nodeStation) {
		NodeStation previous = (NodeStation) nodeStation.getPrevious();
		trackedList.push(nodeStation);
		
		if (previous != null) {
			trackSolution(previous);
		}
	}
	
	public void generatingNodesFrom(NodeStation nodeStation) {		
		for (Station ns : nodeStation.getSelf().getNextStations()) {
			boolean flag = false;
			NodeStation nextNode = new NodeStation(ns, nodeStation);
			flag = isAParentNode(nextNode, nodeStation); // check if the next node is a parent one
			if (!flag) {
				int currentCost = parisMapRealDistances.get(ns.getName() + "" + nodeStation.getSelf().getName());
				int extraCost = extraCostByChangingLine(nodeStation, nextNode); // If the line is changed, there is an extra cost
				nextNode.setCurrentCost(nodeStation.getCurrentCost() + currentCost + extraCost);
				int costToTheGoal = minimumDistanceStations[ns.getId()][goal.getId()];
				nextNode.setCostToTheGoal(costToTheGoal);
				nodeStationList.add(nextNode);
			}
		}
		
	}
	
	public char getCommonLine(Station station1, Station station2) {
		
		for(int i = 0; i < station1.getLines().length; i++) {
			for(int j = 0; j < station2.getLines().length; j++) {
				if (station1.getLines()[i] == station2.getLines()[j]) {
					return station1.getLines()[i];
				}
			}
		}
		
		return '0';
	}
	
	public int extraCostByChangingLine(NodeStation nodeStation, NodeStation nextNodeStation) {
		
		if (nodeStation.getPrevious() == null) {
			return 0;
		}
		
		char previousCommonLine = getCommonLine(nodeStation.getSelf(), nodeStation.getPrevious().getSelf());
		char newCommonLine = getCommonLine(nodeStation.getSelf(), nextNodeStation.getSelf());
		
		if (previousCommonLine != newCommonLine) {
			return 3;
		}
		
		return 0;
	}
	
	public boolean isAParentNode(NodeStation node, NodeStation previous) {
		
		if (previous.getPrevious() == null) {
			return false;
		} else if (previous.getSelf().getId() == node.getSelf().getId()) {
			return true;
		} else {
			return isAParentNode(node, previous.getPrevious());
		}
		
	}
	
	protected static void loadStationsRealDistances() {
		
		parisMapRealDistances.put("E1E2", 11);
		parisMapRealDistances.put("E2E1", 11);
		parisMapRealDistances.put("E2E3", 9);
		parisMapRealDistances.put("E3E2", 9);
		parisMapRealDistances.put("E2E9", 11);
		parisMapRealDistances.put("E9E2", 11);
		parisMapRealDistances.put("E2E10", 4);
		parisMapRealDistances.put("E10E2", 4);
		parisMapRealDistances.put("E3E4", 7);
		parisMapRealDistances.put("E4E3", 7);
		parisMapRealDistances.put("E3E9", 10);
		parisMapRealDistances.put("E9E3", 10);
		parisMapRealDistances.put("E3E13", 19);
		parisMapRealDistances.put("E13E3", 19);
		parisMapRealDistances.put("E4E5", 14);
		parisMapRealDistances.put("E5E4", 14);
		parisMapRealDistances.put("E4E8", 16);
		parisMapRealDistances.put("E8E4", 16);
		parisMapRealDistances.put("E4E13", 12);
		parisMapRealDistances.put("E13E4", 12);
		parisMapRealDistances.put("E5E6", 3);
		parisMapRealDistances.put("E6E5", 3);
		parisMapRealDistances.put("E5E7", 2);
		parisMapRealDistances.put("E7E5", 2);
		parisMapRealDistances.put("E5E8", 33);
		parisMapRealDistances.put("E8E5", 33);
		parisMapRealDistances.put("E8E9", 10);
		parisMapRealDistances.put("E9E8", 10);
		parisMapRealDistances.put("E8E12", 7);
		parisMapRealDistances.put("E12E8", 7);
		parisMapRealDistances.put("E9E11", 14);
		parisMapRealDistances.put("E11E9", 14);
		parisMapRealDistances.put("E13E14", 5);
		parisMapRealDistances.put("E14E13", 5);
		
	}
	
	protected static void loadMinimumDistanceStations() {
		minimumDistanceStations = new int[][] {
			{0, 11, 20, 27, 40, 43, 39, 28, 18, 10, 18, 30, 30, 32},
			{11, 0, 9, 16, 29, 32, 28, 19, 11, 4, 17, 23, 21, 24},
			{20, 9, 0, 7, 20, 22, 19, 15, 10, 11, 21, 21, 13, 18},
			{27, 16, 7, 0, 13, 16, 12, 13, 13, 18, 26, 21, 11, 17},
			{40, 29, 20, 13, 0, 3, 2, 21, 25, 31, 38, 27, 16, 20},
			{43, 32, 22, 16, 3, 0, 4, 23, 28, 33, 41, 30, 17, 20},
			{39, 28, 19, 12, 2, 4, 0, 22, 25, 29, 38, 28, 13, 17},
			{28, 19, 15, 13, 21, 23, 22, 0, 9, 22, 18, 7, 25, 30},
			{18, 11, 10, 13, 25, 28, 25, 9, 0, 13, 12, 12, 23, 28},
			{10, 4, 11, 18, 31, 33, 29, 22, 13, 0, 20, 27, 20, 23},
			{18, 17, 21, 26, 38, 41, 38, 18, 12, 20, 0, 15, 35, 39},
			{30, 23, 21, 21, 27, 30, 28, 7, 12, 27, 15, 0, 31, 37},
			{30, 21, 13, 11, 16, 17, 13, 25, 23, 20, 35, 31, 0, 5},
			{32, 24, 18, 17, 20, 20, 17, 30, 28, 23, 39, 37, 5, 0},
		};
	}
	
	protected static void loadStationsInfo() {
		
		Station[] stations = new Station[STATIONS];
		for (int i = 0; i < stations.length; i++) {
			Station s = new Station(i, "E" + (i + 1));
			stations[i] = s;
		}
		
		stations[0].setLines(new char[]{'b'});
		stations[0].getNextStations().add(stations[1]);
		
		stations[1].setLines(new char[]{'b', 'y'});
		stations[1].getNextStations().add(stations[0]);
		stations[1].getNextStations().add(stations[2]);
		stations[1].getNextStations().add(stations[8]);
		stations[1].getNextStations().add(stations[9]);
		
		stations[2].setLines(new char[]{'b', 'r'});
		stations[2].getNextStations().add(stations[1]);
		stations[2].getNextStations().add(stations[3]);
		stations[2].getNextStations().add(stations[8]);
		stations[2].getNextStations().add(stations[12]);
		
		stations[3].setLines(new char[]{'b', 'g'});
		stations[3].getNextStations().add(stations[2]);
		stations[3].getNextStations().add(stations[4]);
		stations[3].getNextStations().add(stations[12]);
		stations[3].getNextStations().add(stations[7]);
		
		stations[4].setLines(new char[]{'b', 'y'});
		stations[4].getNextStations().add(stations[3]);
		stations[4].getNextStations().add(stations[5]);
		stations[4].getNextStations().add(stations[6]);
		stations[4].getNextStations().add(stations[7]);
		
		stations[5].setLines(new char[]{'b'});
		stations[5].getNextStations().add(stations[4]);
		
		stations[6].setLines(new char[]{'y'});
		stations[6].getNextStations().add(stations[4]);
		
		stations[7].setLines(new char[]{'y', 'g'});
		stations[7].getNextStations().add(stations[8]);
		stations[7].getNextStations().add(stations[4]);
		stations[7].getNextStations().add(stations[3]);
		stations[7].getNextStations().add(stations[11]);
		
		stations[8].setLines(new char[]{'y', 'r'});
		stations[8].getNextStations().add(stations[1]);
		stations[8].getNextStations().add(stations[7]);
		stations[8].getNextStations().add(stations[2]);
		stations[8].getNextStations().add(stations[10]);
		
		stations[9].setLines(new char[]{'y'});
		stations[9].getNextStations().add(stations[1]);
		
		stations[10].setLines(new char[]{'r'});
		stations[10].getNextStations().add(stations[8]);
		
		stations[11].setLines(new char[]{'g'});
		stations[11].getNextStations().add(stations[7]);
		
		stations[12].setLines(new char[]{'g', 'r'});
		stations[12].getNextStations().add(stations[2]);
		stations[12].getNextStations().add(stations[3]);
		stations[12].getNextStations().add(stations[13]);
		
		stations[13].setLines(new char[]{'g'});
		stations[13].getNextStations().add(stations[12]);
		
		for (int i = 0; i < stations.length; i++) {
			parisMap.put(stations[i].getName(), stations[i]);
		}
	}
	

}
