package logic.enumeration;

public enum Pages {
	MAIN_MENU,
	LOGIN,
	REGISTER,
	MANAGE_HUNT,
	MANAGE_MAP,
	CHOOSE_MAP,
	ITEM_HUNT,
	ITEM_HUNTS,
	ITEM_MAP,
	HUNT,
	PLAY_HUNT,
	ITEM_RIDDLE_SHORT,
	ITEM_RIDDLE, 
	ITEM_OBJECT,
	ITEM_ZONE, 
	HUNT_INFORMATION, 
	PROFILE, 
	ITEM_HUNT_RATING, 
	ITEM_GAME_HISTORY, 
	REVIEW,
	ITEM_REVIEW;
	
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
			case PLAY_HUNT: 	return "/logic/view/desktop/layout/PlayHunt.fxml";
			case 
			ITEM_RIDDLE_SHORT: 	return "/logic/view/desktop/layout/item/ItemRiddleShort.fxml";
			case ITEM_RIDDLE:  	return "/logic/view/desktop/layout/item/ItemRiddle.fxml";
			case ITEM_OBJECT:   return "/logic/view/desktop/layout/item/ItemObject.fxml";
			case REGISTER:		return "/logic/view/desktop/layout/RegisterLayout.fxml";
			case ITEM_ZONE: 	return "/logic/view/desktop/layout/item/ItemZone.fxml";
			case 
			HUNT_INFORMATION:   return "/logic/view/desktop/layout/HuntInformation.fxml";
			case PROFILE: 		return "/logic/view/desktop/layout/Profile.fxml";
			case 
			ITEM_HUNT_RATING:   return "/logic/view/desktop/layout/item/ItemHuntRating.fxml";
			case 
			ITEM_GAME_HISTORY:  return "/logic/view/desktop/layout/item/ItemGameHistory.fxml";
			case REVIEW: 		return "/logic/view/desktop/layout/Review.fxml";
			case ITEM_REVIEW:   return "/logic/view/desktop/layout/item/ItemReview.fxml";
			default: 			return MAIN_MENU.getPath();
		}
	}
	
	public String getWebPath() {
		switch(this) {
			case MAIN_MENU:		return "MainMenu.jsp";
			case LOGIN: 		return "Login.jsp";
			case MANAGE_HUNT: 	return "ManageHunt.jsp";
			case MANAGE_MAP: 	return "ManageMap.jsp";
			case PLAY_HUNT:    return "PlayHunt.jsp";
			default:			return MAIN_MENU.getWebPath();
		}
	}
	
	public boolean needLogin() {
		switch(this) {
			case MAIN_MENU:		return false;
			case LOGIN: 		return false;
			case MANAGE_HUNT: 	return true;
			case MANAGE_MAP: 	return true;
			case 
			HUNT_INFORMATION: 	return false;
			case PROFILE:		return true;
			default:			return false;
		}
	}
	
}
