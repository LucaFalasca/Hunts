package logic.view.desktop.controller;

import java.awt.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.LoginBean;
import logic.control.LoginControl;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundException;

public class LoginGController extends ControllerWithLogin{

    @FXML
    private Button btnLogin;

    @FXML
    private TextField tfUserName;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button btnLoginGoogle;
    
    @FXML
    private Label lblError;
    
    private Pages pageAfterLogin = Pages.MAIN_MENU;

    @Override
	void start(String param) {
    	if(param != null) {
    		pageAfterLogin = Pages.valueOf(param);
    	}
    	
		lblError.setVisible(false);
		
	}
    
    @FXML
    void handleLogin(ActionEvent event) {
    		var loginController = new LoginControl();
    		
    		var loginBean = new LoginBean();
    		
    		String username = tfUserName.getText();
    		String password = tfPassword.getText();
    		
    		loginBean.setUsername(username);
    		loginBean.setPassword(password);
    		
    		boolean result = loginController.verifyAccount(loginBean);
    		if(result) {
    			setAsLogged(username);
    			try {
					changeScene(pageAfterLogin, true, null);
				} catch (PageNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		else {
				lblError.setText("Username or password not correct");
				lblError.setVisible(true);
    		}
    }
    
    @FXML
    void handleLoginMenu(ActionEvent event) {
    	try {
			changeScene(Pages.LOGIN, false, null);
		} catch (PageNotFoundException e) {
			System.out.println("beh");
			e.printStackTrace();
		}
    }

    @FXML
    void HandleLoginGoogle(ActionEvent event) {
    	//TODO
    }

	

}
