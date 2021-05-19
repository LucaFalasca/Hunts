package logic.view.desktop.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundException;
import logic.state.login.LogMachine;
import logic.state.login.states.LoggedState;
import logic.state.login.states.NotLoggedState;

public abstract class ControllerWithLogin {
	
	LogMachine logMachine;
	
	abstract void start();
	
	//Only Use after check the credential
	protected void setAsLogged() {
		logMachine.setState(LoggedState.getIstance());
	}
	
	protected ControllerWithLogin() {
		logMachine = new LogMachine(NotLoggedState.getIstance());
	}
	
	private void setLogMachine(LogMachine logMachine) {
		this.logMachine = logMachine;
	}
	
	protected void logout() {
		logMachine.setState(NotLoggedState.getIstance());
	}
	
	protected boolean isLogged() {
		return logMachine.isLogged();
	}
	
	protected void changeScene(Stage stage, Pages page, boolean needLogin) throws PageNotFoundException {
		if(needLogin) {
			if(!logMachine.isLogged()) {
				return;
			}
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(page.getPath()));
			Parent root = (Parent)loader.load();
			ControllerWithLogin controller = loader.<ControllerWithLogin>getController();
			controller.setLogMachine(logMachine);
			controller.start();
			stage.setScene(new Scene(root));
			
		} catch (IOException e) {
			throw new PageNotFoundException();
		}
	}
	
	
}
