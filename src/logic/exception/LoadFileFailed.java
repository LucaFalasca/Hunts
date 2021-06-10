package logic.exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoadFileFailed extends Exception{
	
	private Alert alert = new Alert(AlertType.ERROR);
	
	public LoadFileFailed() {
		alert.setContentText("Cannot create file");
		alert.showAndWait();
	}

}
