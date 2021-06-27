package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class RiddleBean {
	
	private String riddle;
	
	private String solution;
		
	private List<String> clue = new ArrayList<>();
	
	private String objectName;
	
	private String zoneName;
	
	public RiddleBean(String riddle, String solution, String clue1, String clue2, String clue3, String objectName, String zoneName) {
		this.riddle = riddle;
		this.solution = solution;
		clue.add(clue1);
		clue.add(clue2);
		clue.add(clue3);
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
	
	
	
}
