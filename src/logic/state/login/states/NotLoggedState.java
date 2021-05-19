package logic.state.login.states;

import logic.state.draw.states.RectangleState;

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
