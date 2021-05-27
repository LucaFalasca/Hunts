package logic.view.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class CreateGroupGcontroller extends ControllerWithLogin{

    @FXML
    private Button btnChooseHunt;

    @FXML
    private TextField tfChooseHunt;

    @FXML
    private Button bntCreateGroup;

    @FXML
    private TextField tfGroupName;

    @FXML
    private CheckBox cbElection;
    
    @FXML
    private Slider sldNumberHunters;
    
    @FXML
    private CheckBox cbVisibility;

    @FXML
    void handleChooseHunt(ActionEvent event) {
    	
    	String nomeHunt = "nome";
    	tfChooseHunt.setText(nomeHunt);
    	
    }

    @FXML
    void handleCreateGroup(ActionEvent event) {
    	//TODO
    }

	@Override
	void start(String param) {
		// TODO Auto-generated method stub
		
	}


}


