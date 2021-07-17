package logic;

import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.ToolBarController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setMinWidth(1280);
			primaryStage.setMinHeight(720);
			
			var borderPane = new BorderPane();
			
			var loader2 = new FXMLLoader(getClass().getResource("/logic/view/desktop/layout/ToolBar.fxml"));
			Parent toolBar = (Parent)loader2.load();
			ToolBarController controller2 = loader2.getController();
			
			var loader = new FXMLLoader(getClass().getResource(Pages.MAIN_MENU.getPath()));
			Parent root = (Parent)loader.load();
			ControllerWithLogin controller = loader.getController();
						
			borderPane.setTop(toolBar);
			borderPane.setCenter(root);
			
			controller.setStage(primaryStage);
			
			var scene = new Scene(borderPane);
			primaryStage.setScene(scene);
			
			controller.setToolbar(toolBar, controller2);
			primaryStage.show();
			controller.changeScene(Pages.MAIN_MENU,null,null);
			
		} catch(Exception e) {
			var alert = new Alert(AlertType.ERROR, "error opening the software ");
			alert.showAndWait();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
