package logic.view.desktop.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.bean.LoginBean;
import logic.control.LoginControl;

public class LoginGController {

    @FXML
    private Button BtnLogin;

    @FXML
    private TextField TfUserName;

    @FXML
    private PasswordField TfPassword;

    @FXML
    private Button BtnLoginGoogle;

    @FXML
    void HandleLogin(ActionEvent event) {
    		LoginControl loginController = new LoginControl();
    		
    		LoginBean loginBean = new LoginBean();
    		
    		loginBean.setUsername(TfUserName.getText());
    		loginBean.setPassword(TfPassword.getText());
    		
    		boolean result = loginController.verifyAccount(loginBean);
    		
    		if(result) {
    			//cambia schermata
    			try {
	    			Stage stage = (Stage) BtnLogin.getScene().getWindow();
	    			AnchorPane newRoot = (AnchorPane) FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/main_menu.fxml"));
	    			stage.setScene(new Scene(newRoot));
    			}
    			catch(IOException e) {
    				//da gestire
    				e.printStackTrace();
    			}
    		}
    		else {
    			System.out.println("Errore");
    		}
    }

    @FXML
    void HandleLoginGoogle(ActionEvent event) {

    }

}
