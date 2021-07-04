package logic.exception;

public class LoadFileFailed extends Exception{
	
	private static final long serialVersionUID = 1L;

	public LoadFileFailed(){
		super("Problem with the creation or the save of the file");
	}
}
