package logic.view.desktop.controller.item;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundError;
import logic.view.desktop.controller.ControllerWithLogin;

public abstract class ItemController extends ControllerWithLogin{
	
	protected ControllerWithLogin mainController;
	
	protected ItemController(Pages page, ControllerWithLogin mainController){
    	var fxmlLoader = new FXMLLoader(getClass().getResource(page.getPath()));
        fxmlLoader.setController(this);
        
        try {
			fxmlLoader.load();
		} catch (IOException e) {
			var alert = new Alert(AlertType.ERROR, "Error");
			alert.showAndWait();
		}
        
        this.mainController = mainController;
    }
	
	public abstract Parent getBox();

	@Override
	public void changeScene(Pages page, String arg, Object param) throws PageNotFoundError {
		mainController.changeScene(page, arg, param);
	}

	@Override
	public void changeScene(Pages page) throws PageNotFoundError {
		mainController.changeScene(page);
	}
	
	
	
}
