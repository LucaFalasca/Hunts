package logic.bean;

import java.util.List;
import javafx.scene.image.Image;

public class MapBean {

	private int id;
	private String name;
	private Image image;
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public List<ZoneBean> getZones() {
		return zones;
	}
	public void setZones(List<ZoneBean> zones) {
		this.zones = zones;
	}
	
	
	
	
}
