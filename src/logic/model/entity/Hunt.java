package logic.model.entity;

import java.util.List;

public class Hunt {
	
	private int idHunt;
	
	private String huntName;
	
	private List<Object> objectList;
	private List<Riddle> riddleList;
	
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

	public List<Object> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}

	public List<Riddle> getRiddleList() {
		return riddleList;
	}

	public void setRiddleList(List<Riddle> riddleList) {
		this.riddleList = riddleList;
	}
	
	
}
