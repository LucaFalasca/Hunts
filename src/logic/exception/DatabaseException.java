package logic.exception;

public class DatabaseException extends Exception{
	
	private static final long serialVersionUID = -8140933433557482163L;

	public DatabaseException() {
		super("Problem with the retrive of information. Try again later");
	}
}
