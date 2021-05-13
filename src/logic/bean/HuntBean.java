package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class HuntBean {
	
	private int idHunt;
	
	private String huntName;
	
	int nRiddle;
	
	private String riddleText;
	
	private String solutionText;
		
	private List<String> clueList = new ArrayList<>();
	
	private List<Integer> indexList = new ArrayList<>();
	
	private String objectName;
	
	private String nameZone;
	
	public int getIdHunt() {
		return idHunt;
	}

	public void setIdHunt(int idHunt) {
		this.idHunt = idHunt;
	}

	public String getHuntName() {
		return huntName;
	}

	public void setHuntName(String huntName) {
		this.huntName = huntName;
	}
	
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

	public String getNameZone() {
		return nameZone;
	}

	public void setNameZone(String nameZone) {
		this.nameZone = nameZone;
	}

	
	
}
