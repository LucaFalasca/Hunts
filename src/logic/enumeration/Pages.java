package logic.enumeration;

import logic.exception.PageNotFoundException;

public enum Pages {
	MAIN_MENU,
	LOGIN,
	MANAGE_HUNT,
	MANAGE_MAP;
	
	public String getPath() throws PageNotFoundException {
		switch(this) {
			case MAIN_MENU:		return "/logic/view/desktop/layout/MainMenu.fxml";
			case LOGIN: 		return "/logic/view/desktop/layout/LoginLayout.fxml";
			case MANAGE_HUNT: 	return "/logic/view/desktop/layout/ManageHunt.fxml";
			case MANAGE_MAP: 	return "/logic/view/desktop/layout/ManageMapLayout.fxml";
			default:
				throw new PageNotFoundException();
		}
	}
	
	public String getWebPath() throws PageNotFoundException {
		switch(this) {
			case MAIN_MENU:		return "";
			case LOGIN: 		return "";
			case MANAGE_HUNT: 	return "";
			case MANAGE_MAP: 	return "";
			default:
				throw new PageNotFoundException();
		}
	}
	
	public boolean needLogin() {
		switch(this) {
			case MAIN_MENU:		return false;
			case LOGIN: 		return false;
			case MANAGE_HUNT: 	return true;
			case MANAGE_MAP: 	return true;
			default:
				return false;
		}
	}
	
}
