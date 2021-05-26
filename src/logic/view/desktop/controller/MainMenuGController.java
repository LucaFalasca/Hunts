package logic.view.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundException;
import logic.exception.UsernameNotLoggedException;

public class MainMenuGController extends ControllerWithLogin{

    @FXML
    private Button btnHuntCreate;

    @FXML
    private Button btnCreateMap;
    
    @FXML
    private Label lbUsername;
    
    @FXML
    private ToolBar btnHome;
    
    @FXML
    private Button btnLogin;
    
    @Override
	void start() {
		if(isLogged()) {
			try {
				lbUsername.setText(getUsername());
				lbUsername.setVisible(true);
				btnLogin.setVisible(false);
			} catch (UsernameNotLoggedException e) {
				e.printStackTrace();
			}
		}
	}
    
    @FXML
    void initialize() {
    	
    }
    
    
    @FXML
    void handleManageHunt(ActionEvent event) {
    	try {
			changeScene(Pages.MANAGE_HUNT, true);
		} catch (PageNotFoundException e) {
			System.out.println("");
			e.printStackTrace();
		}
    }
    

    @FXML
    void handleCreateMap(ActionEvent event) {
    	try {
			changeScene(Pages.MANAGE_MAP, false);
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

