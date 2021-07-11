package logic.enumeration;


public enum StringHardCode {
	ERRORSELECTED,
	HUNT,
	MAP, 
	PLAYEDHUNT, 
	RIDDLE, 
	OBJECT,
	HUNT_INFORMATION,
	REVIEW; 
	
	@Override
	public String toString() {
		switch(this) {
			case HUNT: 			return "hunt";
			case MAP:			return "map";
			case ERRORSELECTED: return "You must selected an item from the List";
			case PLAYEDHUNT:    return "playedhunt";
			case RIDDLE: 		return "riddle";
			case OBJECT: 		return "object";
			case REVIEW: 		return "review";
			case 
			HUNT_INFORMATION:   return "Hunt information";
			default:
				return "Error";
		}
		
	}
}
