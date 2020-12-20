package logic.model.entity;

public class User {

	private String username;
	private String password;
	private String Nome;
	private String cognome;
	private String email;
	private String dataDiNascita;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
