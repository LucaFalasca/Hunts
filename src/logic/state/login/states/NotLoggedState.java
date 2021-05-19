package logic.state.login.states;

public class NotLoggedState implements LoginState{

	private static NotLoggedState istance = new NotLoggedState();
	
	public static NotLoggedState getIstance() {
		return istance;
	}
	
	@Override
	public boolean isLogged() {
		return false;
	}

}
