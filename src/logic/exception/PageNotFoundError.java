package logic.exception;

public class PageNotFoundError extends Error {

	private static final long serialVersionUID = -5195616630527740028L;

	public PageNotFoundError() {
		super("An error occurenct, Try again");
	}

}
