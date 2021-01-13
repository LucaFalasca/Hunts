package logic;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			SplitPane root = (SplitPane)FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/MainMenu.fxml"));
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
	
	public void changeScene() {
		try {
			Parent pane = (Parent)FXMLLoader.load(getClass().getResource(""));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
