package logic.state.login.states;

public class LoggedState implements LoginState{

	private static LoggedState istance = new LoggedState();
	
	public static LoggedState getIstance() {
		return istance;
	}
	
	@Override
	public boolean isLogged() {
		return true;
	}

}
