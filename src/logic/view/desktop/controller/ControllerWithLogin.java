package logic.view.desktop.controller;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundError;
import logic.exception.UsernameNotLoggedException;
import logic.state.login.LogMachine;
import logic.state.login.states.LoggedState;
import logic.state.login.states.NotLoggedState;
import logic.view.desktop.controller.item.ItemController;

public abstract class ControllerWithLogin{
	
	private Stage stage;
	
	private LogMachine logMachine;
	
	private Parent toolbar;
	private ToolBarController toolBarController;
	
	private Alert alert = new Alert(null);
	
	protected abstract void start(String arg, Object param);
	
	public void setToolbar(Parent toolbar, ToolBarController controller) {
		this.toolbar = toolbar;
		this.toolBarController = controller;
		
		var borderPane = (BorderPane) stage.getScene().getRoot();
		borderPane.setTop(toolbar);
		
		Button login = (Button) stage.getScene().lookup("#btnLogin");
		login.setOnAction(e -> changeScene(Pages.LOGIN));
		
		Button register = (Button) stage.getScene().lookup("#btnRegister");
		register.setOnAction(e -> changeScene(Pages.REGISTER));
		
		ImageView mainMenu = (ImageView) stage.getScene().lookup("#imgHome");
		mainMenu.setOnMouseClicked(e -> changeScene(Pages.MAIN_MENU));
		
		Button profile = (Button) stage.getScene().lookup("#btnProfile");
		profile.setOnAction(e -> changeScene(Pages.PROFILE));
		
		Button logout = (Button) stage.getScene().lookup("#btnLogout");
		logout.setOnAction(e -> {
			logout();
			changeScene(Pages.MAIN_MENU);
		});
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public ControllerWithLogin getIstance() {
		return this;
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
	
	protected String getUsername() {
		String username = null;
		try {
			username = logMachine.getUsername();
		} catch (UsernameNotLoggedException e) {
			showAlert(e.getMessage());
			changeScene(Pages.LOGIN);
		}
		return username;
	}
	
	protected void createStage(ItemController controller, List<?> item, String title) {
		controller.start(title, item);
    	var popUpStage = new Stage();
    	popUpStage.setTitle(title);
        var scene = new Scene(controller.getBox());
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
	}
	
	public void changeScene(Pages page, String arg, Object param) throws PageNotFoundError {
		if(page.needLogin() && !logMachine.isLogged()) {
			changeScene(Pages.LOGIN, "NEXT_PAGE", page.name());
		}
		else {
			try {
				var borderPane = (BorderPane) stage.getScene().getRoot();
				var loader = new FXMLLoader(getClass().getResource(page.getPath()));
				Parent root = (Parent)loader.load();
				ControllerWithLogin controller = loader.<ControllerWithLogin>getController();
				controller.setLogMachine(logMachine);
				controller.setStage(stage);
				controller.setToolbar(toolbar, toolBarController);
				borderPane.setCenter(root);
				controller.start(arg, param);
			} catch (IOException e) {
				throw new PageNotFoundError();
			}
		}
	}
	
	public void changeScene(Pages page) throws PageNotFoundError {
		changeScene(page, "", null);
	}
	
	protected void showAlert(String message) {
		showAlert(AlertType.ERROR, "System Error", message);
	}
	
	protected void showAlert(AlertType type , String title, String header) {
		alert.setAlertType(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.showAndWait();
	}

}
