package logic.bean;

import java.util.List;

public class MapBean {

	private int id;
	private String CreatorName;
	private String name;
	private String image;
	private List<ZoneBean> zones;
	
	public MapBean() {
		
	}
	public MapBean(int id, String name, List<ZoneBean> zones) {
		this.id = id;
		this.name = name;
		this.zones = zones;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<ZoneBean> getZones() {
		return zones;
	}
	public void setZones(List<ZoneBean> zones) {
		this.zones = zones;
	}
	public String getCreatorName() {
		return CreatorName;
	}
	public void setCreatorName(String creatorName) {
		CreatorName = creatorName;
	}
	
	
	
	
}
