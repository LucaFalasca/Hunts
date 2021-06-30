package logic.view.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ToolBarController{

    @FXML
    private Label lbUsername;

    @FXML
    private Button btnHunt;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;
    
    @FXML
    private Button btnLogout;

    
    public void setAsLogged(String username) {
    	lbUsername.setText(username);
		lbUsername.setVisible(true);
		btnRegister.setVisible(false);
		btnLogin.setVisible(false);
		btnLogout.setVisible(true);
    }

    public void logout() {
    	lbUsername.setText("");
		lbUsername.setVisible(false);
		btnRegister.setVisible(true);
		btnLogin.setVisible(true);
		btnLogout.setVisible(false);
    }
}
