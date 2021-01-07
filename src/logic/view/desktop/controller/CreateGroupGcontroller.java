package logic.view.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import logic.control.ManageMapControl;

public class CreateGroupGcontroller {

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

    private int idHunt;
    
    @FXML
    void handleChooseHunt(ActionEvent event) {
    	int rtnidHunt = 15;
    	String nomeHunt = "nome";
    	idHunt = rtnidHunt;
    	tfChooseHunt.setText(nomeHunt);
    }

    @FXML
    void handleCreateGroup(ActionEvent event) {
    	ManageMapControl manageMapControl = new ManageMapControl();
    	
    	//manageMapControl.createGroup();
    }


}


