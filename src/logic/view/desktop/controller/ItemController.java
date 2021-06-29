package logic.view.desktop.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import logic.enumeration.Pages;

public abstract class ItemController {
	
	private ControllerWithLogin mainController;
	
	protected ItemController(Pages page, ControllerWithLogin mainController){
    	var fxmlLoader = new FXMLLoader(getClass().getResource(page.getPath()));
        fxmlLoader.setController(this);
        
        
        try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.mainController = mainController;
    }

	public abstract void setInfo(Object itemBean);
	
	protected void changeScene(Pages page, String arg, Object obj) {
		mainController.changeScene(page, arg, obj);
	}
	
	protected void changeScene(Pages page) {
		mainController.changeScene(page);
	}
	
	public abstract Parent getBox();
}
