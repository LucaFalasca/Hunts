package logic.enumeration;

public enum Pages {
	MAIN_MENU,
	LOGIN,
	MANAGE_HUNT,
	MANAGE_MAP,
	CHOOSE_MAP,
	ITEM_HUNT,
	ITEM_HUNTS,
	ITEM_MAP,
	HUNT,
	RIDDLE;
	
	public String getPath() {
		switch(this) {
		
			case MAIN_MENU:		return "/logic/view/desktop/layout/MainMenu.fxml";
			case LOGIN: 		return "/logic/view/desktop/layout/LoginLayout.fxml";
			case MANAGE_HUNT: 	return "/logic/view/desktop/layout/ManageHunt.fxml";
			case MANAGE_MAP: 	return "/logic/view/desktop/layout/ManageMapLayout.fxml";
			case CHOOSE_MAP:    return "/logic/view/desktop/layout/ChooseMap.fxml";
			case ITEM_HUNT:		return "/logic/view/desktop/layout/item/ItemHunt.fxml";
			case ITEM_HUNTS:	return "/logic/view/desktop/layout/item/ItemHunts.fxml";
			case ITEM_MAP:      return "/logic/view/desktop/layout/item/ItemMap.fxml";
			case HUNT: 			return "/logic/view/desktop/layout/Hunt.fxml";
			case RIDDLE:        return "/logic/view/desktop/layout/item/ItemRiddle.fxml";
			default: 			return MAIN_MENU.getPath();
		}
	}
	
	public String getWebPath() {
		switch(this) {
			case MAIN_MENU:		return "";
			case LOGIN: 		return "";
			case MANAGE_HUNT: 	return "";
			case MANAGE_MAP: 	return "";
			case CHOOSE_MAP:    return "";
			default:			return MAIN_MENU.getWebPath();
		}
	}
	
	public boolean needLogin() {
		switch(this) {
			case MAIN_MENU:		return false;
			case LOGIN: 		return false;
			case MANAGE_HUNT: 	return true;
			case MANAGE_MAP: 	return true;
			default:			return false;
		}
	}
	
}
