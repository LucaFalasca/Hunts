package logic.view.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ToolBarController{

    @FXML
    private Label lbUsername;
    
    @FXML
    private Button btnLogin;
    
    public void setAsLogged(String username) {
    	lbUsername.setText(username);
		lbUsername.setVisible(true);
		btnLogin.setVisible(false);
    }

    public void logout() {
		lbUsername.setVisible(false);
		btnLogin.setVisible(true);
    }
}
