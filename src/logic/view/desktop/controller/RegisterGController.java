package logic.view.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.LoginBean;
import logic.control.LoginControl;
import logic.enumeration.Pages;

public class RegisterGController extends ControllerWithLogin{

	@FXML
    private TextField tfUserName;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Label lblError;

    @Override
	void start(String arg, Object param) {
		// TODO Auto-generated method stub
		
	}
    
    @FXML
    void handleRegister(ActionEvent event) {
    	LoginControl controller = new LoginControl();
    	LoginBean bean = new LoginBean();
    	bean.setUsername(tfUserName.getText());
    	bean.setPassword(tfPassword.getText());
    	
    	if(controller.registerAccount(bean)) {
    		changeScene(Pages.MAIN_MENU, null, null);
    	} else {
    		showAlert("Error system, try again");
    	}
    }

	
}
