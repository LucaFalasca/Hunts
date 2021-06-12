package logic.enumeration;


public enum StringHardCode {
	ERRORLOGIN,
	ERROR,
	FILE; 
	
	public String returnString() {
		switch(this) {
			case ERRORLOGIN: return "Error with Login";
			case ERROR: return  "An error occurenct, Try again";
			case FILE: return "Cannot create file";
			default:
				return "Error";
		}
		
	}
}
