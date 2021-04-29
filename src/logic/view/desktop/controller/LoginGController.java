package logic.view.desktop.controller;

import java.io.IOException;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.LoginBean;
import logic.control.LoginControl;

public class LoginGController {

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
    		
    		loginBean.setUsername(tfUserName.getText());
    		loginBean.setPassword(tfPassword.getText());
    		
    		boolean result = loginController.verifyAccount(loginBean);
    		if(result) {
    			//change stage
    			
    			//start session
    			try {
	    			Stage stage = (Stage) btnLogin.getScene().getWindow();
	    			SplitPane newRoot = (SplitPane) FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/MainMenu.fxml"));
	    			stage.setScene(new Scene(newRoot));
    			}
    			catch(IOException e) {
    				//to gesture
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

}
