package logic.view.desktop.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainMenuController{
	
	@FXML
	private Button btnCreateMap;
	
	public void handleCreateMapButton(ActionEvent event) throws IOException {
		Stage stage = (Stage)btnCreateMap.getScene().getWindow();
		AnchorPane newRoot = FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/ManageMapLayout.fxml"));
		stage.setScene(new Scene(newRoot));
		
	}

}
