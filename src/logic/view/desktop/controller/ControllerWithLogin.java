package logic.view.desktop.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundError;
import logic.exception.UsernameNotLoggedException;
import logic.state.login.LogMachine;
import logic.state.login.states.LoggedState;
import logic.state.login.states.NotLoggedState;

public abstract class ControllerWithLogin{
	
	private Stage stage;
	
	private LogMachine logMachine;
	
	private Parent toolbar;
	private ToolBarController toolBarController;
	
	abstract void start(String arg, Object param);
	
	public void setToolbar(Parent toolbar, ToolBarController controller) throws IOException {
		this.toolbar = toolbar;
		this.toolBarController = controller;
		
		BorderPane borderPane = (BorderPane) stage.getScene().getRoot();
		borderPane.setTop(toolbar);
		
		Button login = (Button) stage.getScene().lookup("#btnLogin");
		login.setOnAction(e -> {
			try {
				changeScene(Pages.LOGIN);
			} catch (PageNotFoundError e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Button b = (Button) stage.getScene().lookup("#btnHunt");
		b.setOnAction(e -> {
			try {
				changeScene(Pages.MAIN_MENU);
			} catch (PageNotFoundError e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	//Only Use after check the credential
	protected void setAsLogged(String username) {
		logMachine.setState(LoggedState.getIstance());
		logMachine.setUsername(username);
		
		toolBarController.setAsLogged(username);
	}
	
	protected ControllerWithLogin() {
		logMachine = new LogMachine(NotLoggedState.getIstance());
	}
	
	private void setLogMachine(LogMachine logMachine) {
		this.logMachine = logMachine;
	}
	
	protected void logout() {
		logMachine.setState(NotLoggedState.getIstance());
		
		toolBarController.logout();
	}
	
	protected boolean isLogged() {
		return logMachine.isLogged();
	}
	
	protected String getUsername() throws UsernameNotLoggedException {
		return logMachine.getUsername();
	}
	
	protected void changeScene(Pages page, String arg, Object param) throws PageNotFoundError {
		if(page.needLogin() && !logMachine.isLogged()) {
			changeScene(Pages.LOGIN, "NEXT_PAGE", page.name());
		}
		else {
			try {
				BorderPane borderPane = (BorderPane) stage.getScene().getRoot();
				var loader = new FXMLLoader(getClass().getResource(page.getPath()));
				Parent root = (Parent)loader.load();
				ControllerWithLogin controller = loader.<ControllerWithLogin>getController();
				controller.setLogMachine(logMachine);
				controller.setStage(stage);
				controller.setToolbar(toolbar, toolBarController);
				controller.start(arg, param);
				borderPane.setCenter(root);
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new PageNotFoundError();
			}
		}
	}
	
	protected void changeScene(Pages page) throws PageNotFoundError {
		changeScene(page, "", null);
	}

}
