package logic.view.desktop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ManageHuntGController{


    @FXML
    private Label lbRiddle;

    @FXML
    private HBox objectConteiner;

    @FXML
    private GridPane clueConteiner;

    @FXML
    private TextField tfHuntName;

    @FXML
    private ComboBox<String> cmbObject;

    @FXML
    private Label lbErrorObjName;

    @FXML
    private TextField tfRiddleSolution;

    @FXML
    private TextField tfRiddleText;

    @FXML
    private GridPane riddleConteiner;

    @FXML
    private Button btnChooseMap;

    @FXML
    private GridPane clueConteiner1;

    @FXML
    private GridPane clueConteiner2;

    @FXML
    private Button btnUploadFile;

    @FXML
    private ListView<String> lvObject;

    private ObservableList<String> objList = FXCollections.observableArrayList();
    
    @FXML
    private ListView<String> lvRiddle;
    
    private ObservableList<String> rdlList = FXCollections.observableArrayList();
    
    @FXML
    private Button btnAddRiddle;

    @FXML
    private Button bntFinish;

    @FXML
    private Button btnRemoveRiddle;

    @FXML
    private Button btnAddToList;

    @FXML
    private TextField tfClueText2;

    @FXML
    private TextField tfClueText3;

    @FXML
    private TextField tfClueText1;

    @FXML
    private Button btnCreateMap1;

    @FXML
    private Button btnRemoveObject;

    @FXML
    private TextField tfObjectName;

    @FXML
    private Label lbRiddleError;

    private int nRiddle = 0;
    
    private List<String> riddleList = new ArrayList<String>();
	
	private List<String> solutionList = new ArrayList<String>();
	
	private Map<Integer, String> clueList = new HashMap<>();
	
	private Map<Integer, String> objectList = new HashMap<>();
	
    @FXML
    void initialize() {
    	
    	lbErrorObjName.setText("The name has already been entered");
    	lbRiddle.setText("Riddle " + nRiddle);
    	lbRiddleError.setText("Text or Solution are empty");
    	
    	lvObject.setItems(objList);
    	cmbObject.setItems(objList);
    	lvRiddle.setItems(rdlList);
    }
    
    @FXML
    void handlerAddRiddle(ActionEvent event) {
    	
    	if(!(tfRiddleText.getText().equals("")) && !(tfRiddleSolution.getText().equals(""))){
    		
    		String componentRiddle = null;
    		
    		if(lbRiddleError.isVisible())
    			lbRiddleError.setVisible(false);
    		
    		riddleList.add(tfRiddleText.getText());
    		
    		componentRiddle = "T: " + riddleList.get(nRiddle) + " ";
    		
    		solutionList.add(tfRiddleSolution.getText());
    		
    		componentRiddle = componentRiddle + ("S: " + solutionList.get(nRiddle) + "   ");
    		
    		if(!(tfClueText1.getText().equals(""))){
    			clueList.put(nRiddle, tfClueText1.getText());
    			componentRiddle = componentRiddle + ("C1: " + clueList.get(nRiddle) + "   ");
    		}
    		
    		if(!(tfClueText2.getText().equals(""))){
    			clueList.put(nRiddle, tfClueText2.getText());
    			componentRiddle = componentRiddle + ("C2: " + clueList.get(nRiddle) + "   ");
    		}
    		
    		if(!(tfClueText3.getText().equals(""))){
    			clueList.put(nRiddle, tfClueText3.getText());
    			componentRiddle = componentRiddle + ("C3: " + clueList.get(nRiddle) + "   ");
    		}
    		
    		if(cmbObject.getSelectionModel().getSelectedItem() != null) {
    			objectList.put(nRiddle, cmbObject.getSelectionModel().getSelectedItem());
    			componentRiddle = componentRiddle + ("Ob: " + objectList.get(nRiddle) + "   ");
    		}
    		
    		rdlList.add(componentRiddle);
    		
    		nRiddle++;
    	}
    	else {
    	
    		lbRiddleError.setVisible(true);
    		
    	}
    }
    
    @FXML
    void handleRiddleSelected(MouseEvent event) {
    	if((event.getEventType() == MouseEvent.MOUSE_CLICKED) && (lvRiddle.getSelectionModel().getSelectedItem() != null))
    		btnRemoveRiddle.setVisible(true);
    	else
    		btnRemoveRiddle.setVisible(false);
    }
    
    @FXML
    void handleRemoveRiddle(ActionEvent event) {
    	try {
	    	
    		int index = 0;
    		
    		index = lvRiddle.getSelectionModel().getSelectedIndex();
    		
    		rdlList.remove(index);
    		
    		riddleList.remove(index);
    		
    		solutionList.remove(index);
    		
    		for (Map.Entry<Integer, String> pair : clueList.entrySet()) {
    		    if(pair.getKey() == index) {
    		    	clueList.remove(pair.getKey());
    		    }
    		    else if(pair.getKey() > index) {
    		    	break;
    		    }
    		}
    		
    		for (Map.Entry<Integer, String> pair : objectList.entrySet()) {
    		    if(pair.getKey() == index) {
    		    	clueList.remove(pair.getKey());
    		    }
    		    else if(pair.getKey() > index) {
    		    	break;
    		    }
    		}
    		
    		nRiddle--;
    		
		}
		catch(Exception e) {
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
    void handleAddObject(ActionEvent event) {
    	try {
    		
    		boolean flag = false;
    		String objectName = tfObjectName.getText();
    		if(objectName != "") {
    			
	    		for(int i = 0; i < lvObject.getItems().size(); i++){
	    			
	    			if(objectName.equals(lvObject.getItems().get(i))) {
	    				flag = true;
	    				break;
	    			}
	    			
	    		}
	    		
	    		if(!(flag)) {
	    			
	    			objList.add(objectName);

	    			if(lbErrorObjName.isVisible()) {
	    				lbErrorObjName.setVisible(false);
	    			}
	    			
	    		}
	    		else
	    			lbErrorObjName.setVisible(true);
    		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleRemoveObject(ActionEvent event) {
    	
    	String object = objList.get(lvObject.getSelectionModel().getSelectedIndex());
    	
    	for(int i = 0; i < rdlList.size(); i++) {
    		if(rdlList.get(i).contains(object)) {
    			rdlList.remove(i);
    		}
    	}
    	
    	objList.remove(lvObject.getSelectionModel().getSelectedIndex());
    }
    
    @FXML
    void handleObjectSelected(MouseEvent event) {
    	if((event.getEventType() == MouseEvent.MOUSE_CLICKED) && (lvObject.getSelectionModel().getSelectedItem() != null))
    		btnRemoveObject.setVisible(true);
    	else
    		btnRemoveObject.setVisible(false);
    }
    
    @FXML
    void handleUplaodFile(ActionEvent event) {

    }
    
    @FXML
    void handleFinish(ActionEvent event) {
    	
    }
    
}
