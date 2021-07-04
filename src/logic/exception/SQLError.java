package logic.exception;

public class SQLError extends Exception{
	
	private static final long serialVersionUID = -8140933433557482163L;

	public SQLError() {
		super("Problem with the retrive of information. Try again later");
	}
}
