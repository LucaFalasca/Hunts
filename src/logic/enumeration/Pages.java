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
}
