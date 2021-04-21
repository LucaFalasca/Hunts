package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class RiddleBean {
	
int nRiddle;
	
	private String riddleText;
	
	private String solutionText;
		
	private List<String> clueList = new ArrayList<>();
	
	private List<Integer> indexList = new ArrayList<>();
	
	private String objectName;
	
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
	
	public void addClueListElement(int index, String clue) {
		clueList.add(index, clue);
	}
	
	public String getClueListElement(int index) {
		return clueList.get(index);
	}
	
	public void emptyClueList() {
		clueList.clear();
	}

	public List<Integer> getIndexList() {
		return indexList;
	}

	public void setIndexList(List<Integer> indexList) {
		this.indexList = indexList;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
}
