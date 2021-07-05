package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class HuntBean {
	
	private int idHunt;
	
	private String username;
	
	private String huntName;
	
	private List<RiddleBean> riddle = new ArrayList<>();
	
	private List<ObjectBean> object = new ArrayList<>();
	
	private MapBean map;
	
	private boolean isPrivate;
	
	private boolean avgRating;
	
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
	
	public List<RiddleBean> getRiddle() {
		return riddle;
	}

	public void setRiddle(List<RiddleBean> riddle) {
		this.riddle = riddle;
	}

	public List<ObjectBean> getObject() {
		return object;
	}

	public void setObject(List<ObjectBean> object) {
		this.object = object;
	}

	public MapBean getMap() {
		return map;
	}

	public void setMap(MapBean map) {
		this.map = map;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public boolean getPrivate() {
		return isPrivate;
	}

	public boolean isAvgRating() {
		return avgRating;
	}

	public void setAvgRating(boolean avgRating) {
		this.avgRating = avgRating;
	}
	
	
}
