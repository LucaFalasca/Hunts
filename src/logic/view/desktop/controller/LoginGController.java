package logic.view.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import logic.bean.LoginBean;
import logic.control.LoginControl;
import logic.enumeration.Pages;

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
    
    private Pages pageAfterLogin;

    @Override
	void start(String arg, Object param) {
    	if(arg.equals("NEXT_PAGE")) {
    		pageAfterLogin = Pages.valueOf((String) param);	
    	}else {
    		pageAfterLogin = Pages.MAIN_MENU;
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
				changeScene(pageAfterLogin);
    		}
    		else {
				lblError.setText("Username or password not correct");
				lblError.setVisible(true);
    		}
    }
    
    @FXML
    void handleLoginMenu(ActionEvent event) {
		changeScene(Pages.LOGIN);
    }

    @FXML
    void HandleLoginGoogle(ActionEvent event) {
    	//TODO
    }

	

}
