package logic.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Riddle {
	private int nRiddle;
	
	private String riddleText;
	
	private String solutionText;
	
	private List<String> clueList = new ArrayList<>();
	
	private String reward;
	
	private String zone;
	
	public int getnRiddle() {
		return nRiddle;
	}

	public void setnRiddle(int nRiddle) {
		this.nRiddle = nRiddle;
	}

	public String getRiddleText() {
		return riddleText;
	}

	public void setRiddleText(String riddleText) {
		this.riddleText = riddleText;
	}

	public String getSolutionText() {
		return solutionText;
	}

	public void setSolutionText(String solutionText) {
		this.solutionText = solutionText;
	}

	public List<String> getClueList() {
		return clueList;
	}
	
	public void setClueList(List<String> clueList) {
		this.clueList = clueList;
	}
	
	public void setClueListElement(int index, String clue) {
		clueList.add(index, clue);
	}
	
	public String getClueListElement(int index) {
		return clueList.get(index);
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	
	
}
