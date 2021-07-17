package logic.view.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.LoginBean;
import logic.control.UserControl;
import logic.enumeration.Pages;

public class RegisterGController extends ControllerWithLogin{

	@FXML
    private TextField tfUserName;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Label lblError;

    @Override
	protected void start(String arg, Object param) {
		//This method is not needed
	}
    
    @FXML
    void handleRegister(ActionEvent event) {
    	UserControl controller = new UserControl();
    	LoginBean bean = new LoginBean();
    	bean.setUsername(tfUserName.getText());
    	bean.setPassword(tfPassword.getText());
    	
    	if(controller.registerAccount(bean)) {
    		changeScene(Pages.MAIN_MENU, null, null);
    	} else {
    		showAlert("This username already exist");
    	}
    }

	
}
