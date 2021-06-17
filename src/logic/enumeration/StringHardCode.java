package logic.enumeration;


public enum StringHardCode {
	ERRORLOGIN,
	ERROR,
	FILE,
	HUNT,
	MAP; 
	
	public String getString() {
		switch(this) {
			case ERRORLOGIN: return "Error with Login";
			case ERROR: return  "An error occurenct, Try again";
			case FILE: return "Cannot create file";
			case HUNT: return "hunt";
			case MAP:return "map";
			default:
				return "Error";
		}
		
	}
}
