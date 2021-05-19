package logic.view.desktop.controller;

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
    void handleLogin(ActionEvent event) {
    		LoginControl loginController = new LoginControl();
    		
    		LoginBean loginBean = new LoginBean();
    		
    		String username = tfUserName.getText();
    		String password = tfPassword.getText();
    		
    		loginBean.setUsername(username);
    		loginBean.setPassword(password);
    		
    		boolean result = loginController.verifyAccount(loginBean);
    		if(result) {
    			setAsLogged(username);
    			try {
					changeScene(Pages.MAIN_MENU, true);
				} catch (PageNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		else {
				//TODO
    		}
    }

    @FXML
    void HandleLoginGoogle(ActionEvent event) {
    	//TODO
    }

	@Override
	void start() {
		// TODO Auto-generated method stub
		
	}

}
