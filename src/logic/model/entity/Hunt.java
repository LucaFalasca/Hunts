package logic.model.entity;

import java.util.List;

public class Hunt {
	
	private int idHunt;
	
	private String huntName;
	
	private List<Object> objectList;
	private List<Riddle> riddleList;
	
	private Map map;
	
	private String creatorName;
	
	private boolean indoor;
	
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

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public boolean isIndoor() {
		return indoor;
	}

	public void setIndoor(boolean indoor) {
		this.indoor = indoor;
	}

	
	
	
	
}
