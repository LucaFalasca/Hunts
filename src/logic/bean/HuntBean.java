package logic.bean;

import java.util.ArrayList;
import java.util.List;

public class HuntBean {
	
	private int idHunt;
	
	private String huntName;
	
	private List<RiddleBean> riddle = new ArrayList<>();
	
	private List<ObjectBean> object = new ArrayList<>();
	
	private List<Integer> index = new ArrayList<>();
	
	private MapBean map;
	
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

	public List<Integer> getIndex() {
		return index;
	}

	public void setIndex(List<Integer> index) {
		this.index = index;
	}

	public MapBean getMap() {
		return map;
	}

	public void setMap(MapBean map) {
		this.map = map;
	}

	
	
	
}
