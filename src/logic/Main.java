package logic;

import javafx.stage.Stage;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setMinWidth(1280);
			primaryStage.setMinHeight(720);
			FXMLLoader loader = new FXMLLoader(getClass().getResource(Pages.MAIN_MENU.getPath()));
			Parent root = (Parent)loader.load();
			ControllerWithLogin controller = loader.getController();
			controller.setStage(primaryStage);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
