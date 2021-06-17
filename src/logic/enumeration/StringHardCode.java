package logic.enumeration;


public enum StringHardCode {
	ERRORLOGIN,
	ERROR,
	FILE,
	ERRORSELECTED,
	HUNT,
	MAP; 
	
	public String getString() {
		switch(this) {
			case ERRORLOGIN: 	return "Error with Login";
			case ERROR: 		return  "An error occurenct, Try again";
			case FILE: 			return "Cannot create file";
			case HUNT: 			return "hunt";
			case MAP:			return "map";
			case ERRORSELECTED: return "You must selected an item from the List";
			default:
				return "Error";
		}
		
	}
}
