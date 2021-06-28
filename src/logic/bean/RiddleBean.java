package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class RiddleBean {
	
	private int numRiddle;
	
	private String riddle;
	
	private String solution;
	
	private String clue1;
	private String clue2;
	private String clue3;
		
	private List<String> clue = new ArrayList<>();
	
	private String objectName;
	
	private String zoneName;
	
	public RiddleBean(int numRiddle, String riddle, String solution, String clue1, String clue2, String clue3, String objectName, String zoneName) {
		
		this.numRiddle = numRiddle;
		this.riddle = riddle;
		this.solution = solution;
		
		this.clue1 = clue1;
		this.clue2 = clue2;
		this.clue3 = clue3;
		clue.add(this.clue1);
		clue.add(this.clue2);
		clue.add(this.clue3);
		
		this.objectName = objectName;
		this.zoneName = zoneName;
	}

	public RiddleBean() {
	}

	public String getRiddle() {
		return riddle;
	}

	public void setRiddle(String riddle) {
		this.riddle = riddle;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public List<String> getClue() {
		return clue;
	}

	public void setClue(List<String> clue) {
		this.clue = clue;
	}
	
	public String getClueElement(int index) {
		return clue.get(index);
	}
	
	public void setClueElement(int index, String clue) {
		this.clue.add(index, clue);
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getClue1() {
		return clue1;
	}

	public void setClue1(String clue1) {
		this.clue1 = clue1;
	}

	public String getClue2() {
		return clue2;
	}

	public void setClue2(String clue2) {
		this.clue2 = clue2;
	}

	public String getClue3() {
		return clue3;
	}

	public void setClue3(String clue3) {
		this.clue3 = clue3;
	}

	public int getNumRiddle() {
		return numRiddle;
	}

	public void setNumRiddle(int numRiddle) {
		this.numRiddle = numRiddle;
	}
	
	
}
