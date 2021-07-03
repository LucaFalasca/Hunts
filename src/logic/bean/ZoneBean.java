package logic.bean;

public class ZoneBean {
	
	private String nameZone;
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private String shape;
	
	public ZoneBean(String nameZone, double x1, double y1, double x2, double y2, String shape) {
		this.nameZone = nameZone;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.shape = shape;
	}

	public String getNameZone() {
		return nameZone;
	}

	public void setNameZone(String name) {
		this.nameZone = name;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double startX) {
		this.x1 = startX;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double startY) {
		this.y1 = startY;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double endX) {
		this.x2 = endX;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double endY) {
		this.y2 = endY;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String type) {
		this.shape = type;
	}
	
	@Override
	public String toString() {
		return nameZone;
	}
	
}