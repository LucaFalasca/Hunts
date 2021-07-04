package logic.exception;

public class NotConnectedException extends Exception {

	
	private static final long serialVersionUID = -4705252396080762978L;

	public NotConnectedException() {
		super("Connection problem , try again later");
	}
}
