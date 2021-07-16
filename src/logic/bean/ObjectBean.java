package logic.bean;

public class ObjectBean {
	
	private int idObject;
	
	private String name;

	private String path;

	private String description;
	
	public ObjectBean(int idObject, String name, String description, String path) {
		this.idObject = idObject;
		this.name = name;
		this.description = description;
		this.path = path;
	}
	
	public ObjectBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String object) {
		this.name = object;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdObject() {
		return idObject;
	}
	
	public void setIdObject(int idObject) {
		this.idObject = idObject;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
