package logic.model.entity;

import java.util.List;

public class Map {

	private int id;
	private String name;
	private String imagePath;
	private List<Zone> zones;
	
	public Map(int id) {
		this.id = id;
	}
	
	public Map() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Zone> getZones() {
		return zones;
	}
	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
}
