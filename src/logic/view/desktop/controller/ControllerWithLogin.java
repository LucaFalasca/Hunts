package logic.view.desktop.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundException;
import logic.exception.UsernameNotLoggedException;
import logic.state.login.LogMachine;
import logic.state.login.states.LoggedState;
import logic.state.login.states.NotLoggedState;

public abstract class ControllerWithLogin {
	
	private Stage stage;
	
	private LogMachine logMachine;
	
	abstract void start(Object param);
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	//Only Use after check the credential
	protected void setAsLogged(String username) {
		logMachine.setState(LoggedState.getIstance());
		logMachine.setUsername(username);
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
	
	protected String getUsername() throws UsernameNotLoggedException {
		return logMachine.getUsername();
	}
	
	protected void changeScene(Pages page, boolean needLogin, Object param) throws PageNotFoundException {
		if(needLogin) {
			if(!logMachine.isLogged()) {
				changeScene(Pages.LOGIN, false, page.name());
				return;
			}
		}
		try {
			var loader = new FXMLLoader(getClass().getResource(page.getPath()));
			Parent root = (Parent)loader.load();
			ControllerWithLogin controller = loader.<ControllerWithLogin>getController();
			controller.setLogMachine(logMachine);
			controller.setStage(stage);
			controller.start(param);
			stage.setScene(new Scene(root));
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new PageNotFoundException();
		}
	}
	
	
}
