package logic.view.desktop.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenuGController {

    @FXML
    private Button btnHuntCreate;

    @FXML
    private Button btnCreateMap;
    
    @FXML
    private ToolBar btnHome;

    private Stage stage;
    
    @FXML
    void handleManageHunt(ActionEvent event) {
    	try {
    		BorderPane pane = (BorderPane)FXMLLoader.load(getClass().getResource("logic.view.desktop.layout.ManageHunt"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

    @FXML
    void handleCreateMap(ActionEvent event) {
    	stage = (Stage) btnCreateMap.getScene().getWindow();
    	try {
    		BorderPane pane = (BorderPane)FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/ManageMapLayout.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

