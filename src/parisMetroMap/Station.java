package parisMetroMap;

import java.util.ArrayList;

public class Station {
	
	public Station(int id, String name) {
		setId(id);
		setName(name);
	}

	private int id;
	private String name;
	private ArrayList<Station> nextStations = new ArrayList<Station>();
	private char[] lines = null;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Station> getNextStations() {
		return nextStations;
	}
	public void setNextStations(ArrayList<Station> nextStations) {
		this.nextStations = nextStations;
	}
	public char[] getLines() {
		return lines;
	}
	public void setLines(char[] lines) {
		this.lines = lines;
	}
	
}
