package logic.model.entity;

import java.util.List;

public class Hunt {
	
	private int idHunt;
	
	private String huntName;
	
	private List<RealObject> objectList;
	private List<Riddle> riddleList;
	
	private Map map;
	
	private String creatorName;
	
	private boolean indoor;
	
	private boolean visible;
	
	private double avgRatingHunt;
	
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

	public List<RealObject> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<RealObject> objectList) {
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public double getAvgRatingHunt() {
		return avgRatingHunt;
	}

	public void setAvgRatingHunt(double avgRatingHunt) {
		this.avgRatingHunt = avgRatingHunt;
	}
	

	
	
	
	
}
