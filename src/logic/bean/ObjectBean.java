package logic.bean;

public class ObjectBean {
	
	private String name;

	private String path;

	private String description;
	
	public ObjectBean(String name, String description, String path) {
		this.name = name;
		this.description = description;
		this.path = path;
	}

	public String getObject() {
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
	
	
}
