package logic.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hunt {
	
	private String huntName;
	
	private List<String> riddleList = new ArrayList<>();
	
	private List<String> solutionList = new ArrayList<>();
	
	private Map<Integer, String> clueList = new HashMap<>();
	
	private Map<Integer, String> objectList = new HashMap<>();

	public String getHuntName() {
		return huntName;
	}

	public void setHuntName(String huntName) {
		this.huntName = huntName;
	}

	public List<String> getRiddleList() {
		return riddleList;
	}

	public void setRiddleList(List<String> riddleList) {
		this.riddleList = riddleList;
	}

	public List<String> getSolutionList() {
		return solutionList;
	}

	public void setSolutionList(List<String> solutionList) {
		this.solutionList = solutionList;
	}

	public Map<Integer, String> getClueList() {
		return clueList;
	}

	public void setClueList(Map<Integer, String> clueList) {
		this.clueList = clueList;
	}

	public Map<Integer, String> getObjectList() {
		return objectList;
	}

	public void setObjectList(Map<Integer, String> objectList) {
		this.objectList = objectList;
	}
	
	
}
