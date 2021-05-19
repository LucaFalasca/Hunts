package logic.state.login;

import logic.exception.UsernameNotLoggedException;
import logic.state.login.states.LoginState;

public class LogMachine {
	
	private LoginState state;
	private String username;
	
	public LogMachine(LoginState state) {
		this.state = state;
	}
	
	public void setState(LoginState state) {
		this.state = state;
	}
	
	public boolean isLogged() {
		return state.isLogged();
	}
	
	public String getUsername() throws UsernameNotLoggedException {
		if(isLogged()) {
			return username;
		}
		else {
			throw new UsernameNotLoggedException();
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
}
