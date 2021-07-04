package logic.exception;

public class UsernameNotLoggedException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameNotLoggedException() {
		super("Error with Login. You will get back in the registration page");
	}
}
