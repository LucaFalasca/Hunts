package logic.view.desktop.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ManageHuntGController{
    
    @FXML
    private Button btnaddRiddle;

    @FXML
    private Button btnCreateMap;

    @FXML
    private Button btnChooseMap;
    
    @FXML
    private Button btnRemoveClue;
    
    @FXML
    private Button btnRemoveObject;

    @FXML
    private Button btnAddClue;
    
    @FXML
    private Button bntAddObject;
    
    @FXML
    private Button bntFinish;

    @FXML
    private Button btnRemoveRiddle;
    
    @FXML
    private Button btnUploadFile;
    
    @FXML
    private GridPane conteiner;
    
    @FXML
    private GridPane clueConteiner;
    
    @FXML
    private ListView<GridPane> lvRiddle;
    
    @FXML
    private ListView<GridPane> lvClue;
    
    @FXML
    private TextField tfRiddleSolution;

    @FXML
    private TextField tfRiddleText;

    @FXML
    private TextField tfObjectName;
   
    @FXML
    private TextField tfClueText;

    @FXML
    private Label lbRiddle;

    @FXML
    private Pane objectConteiner;
    
    @FXML
    private VBox vbRiddle;

    private String id;
    
    private int numRiddle = 0, numClue = 0;
    
    @FXML
    void handlerAddRiddleBoxes(ActionEvent event) {
    	try {
    		
    		conteiner = (GridPane)FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/RiddleLayout.fxml"));
    		
    		conteiner.setUserData(numRiddle);
    		
    		numRiddle++;
    		
    		id = lvRiddle.getId();
    		
    		lvRiddle.getItems().add(conteiner);
    		
    		
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    @FXML
    void handleCreateMap(ActionEvent event) {
    	//TODO
    }

    @FXML
    void handleChooseMap(ActionEvent event) {
    	//TODO 
    }
    
    @FXML
    void handleAddClue(ActionEvent event) {
    	try {

			clueConteiner = (GridPane)FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/ClueLayout.fxml"));
			
			lvClue.getItems().add(clueConteiner);
			
			clueConteiner.setUserData(numClue);
			
			numClue++;
			
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
	@FXML
    void handleRemoveRiddle(ActionEvent event) {
    	try {
    		
    		lvRiddle = (ListView<GridPane>) conteiner.getParent().getParent().getParent().getParent().getParent();
    		
    		lvRiddle.getChildrenUnmodifiable().remove(conteiner.getUserData());
    		
    		numRiddle--;
    		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleAddObject(ActionEvent event) {
    	try {
			//TOREDO
			//HBox element = (HBox)FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/ObjectLayout.fxml"));

			//conteiner.getChildren().add(9, element);    		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleRemoveClue(ActionEvent event) {
    	lvClue.getItems().remove(clueConteiner.getUserData());
    }
    
    @FXML
    void handleUplaodFile(ActionEvent event) {

    }
    

    @FXML
    void handleRemoveObject(ActionEvent event) {

    }
    
    @FXML
    void handleFinish(ActionEvent event) {

    }
}
