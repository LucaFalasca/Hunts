package logic.bean;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class MapBean {

	private int id;
	private String name;
	private Image image;
	private Canvas canvas;
	
	public MapBean() {
		
	}
	public MapBean(int id, String name, Canvas canvas) {
		this.id = id;
		this.name = name;
		this.canvas = canvas;
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
	public Canvas getCanvas() {
		return canvas;
	}
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	
}
