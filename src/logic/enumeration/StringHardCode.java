package logic.enumeration;


public enum StringHardCode {
	ERRORSELECTED,
	HUNT,
	MAP; 
	
	@Override
	public String toString() {
		switch(this) {
			case HUNT: 			return "hunt";
			case MAP:			return "map";
			case ERRORSELECTED: return "You must selected an item from the List";
			default:
				return "Error";
		}
		
	}
}
