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
import logic.enumeration.Pages;
import logic.exception.PageNotFoundException;

public class MainMenuGController extends ControllerWithLogin{

    @FXML
    private Button btnHuntCreate;

    @FXML
    private Button btnCreateMap;
    
    @FXML
    private ToolBar btnHome;

    private Stage stage;
    
    @FXML
    private Button btnLogin;
    
    @Override
	void start() {
		// TODO Auto-generated method stub
		
	}
    
    @FXML
    void initialize() {
    	
    }
    
    
    @FXML
    void handleManageHunt(ActionEvent event) {
    	try {
			changeScene(Pages.MANAGE_HUNT, true);
		} catch (PageNotFoundException e) {
			System.out.println("beh");
			e.printStackTrace();
		}
    }
    

    @FXML
    void handleCreateMap(ActionEvent event) {
    	try {
			changeScene(Pages.MANAGE_MAP, true);
		} catch (PageNotFoundException e) {
			System.out.println("beh");
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleLogin(ActionEvent event) {
    	try {
			changeScene(Pages.LOGIN, false);
		} catch (PageNotFoundException e) {
			System.out.println("beh");
			e.printStackTrace();
		}
    }

}

