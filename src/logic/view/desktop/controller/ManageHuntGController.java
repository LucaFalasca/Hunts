package logic.view.desktop.controller;

import java.util.ArrayList;
import java.util.List;

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
import logic.bean.Hunt;
import logic.bean.ObjectBean;
import logic.bean.RiddleBean;
import logic.control.ManageHuntControl;

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
	    
	    private List<TextField> tfClueText = new ArrayList<>();

	    @FXML
	    private Button btnCreateMap1;

	    @FXML
	    private Button btnRemoveObject;

	    @FXML
	    private TextField tfObjectName;

	    @FXML
	    private Label lbRiddleError;
	    
	    @FXML
	    private Button btnModifyRiddle;
		
		private static final String RIDDLE = "Riddle";
	    ManageHuntControl manageHuntControl = new ManageHuntControl();
		
		RiddleBean riddleBean = new RiddleBean();
		ObjectBean objectBean = new ObjectBean();
	
	    @FXML
	    void initialize() {
	    	
	    	lbRiddle.setText(RIDDLE + rdlList.size());
	    	
	    	lvObject.setItems(objList);
	    	cmbObject.setItems(objList);
	    	lvRiddle.setItems(rdlList);
	    	
	    	tfClueText.add(tfClueText1);
	    	tfClueText.add(tfClueText2);
	    	tfClueText.add(tfClueText3);
	    	
	    }
	    
	    @FXML
	    void handlerAddRiddle(ActionEvent event) {
	    	
	    	
	    	if(!(tfRiddleText.getText().equals("")) && !(tfRiddleSolution.getText().equals(""))){
	    		
	    		riddleBean.setnRiddle(rdlList.size());
	    		
	    		if(lbRiddleError.isVisible())
	    			lbRiddleError.setVisible(false);
	    		
	    		riddleBean.setRiddleText(tfRiddleText.getText());
	    		
	    		riddleBean.setSolutionText(tfRiddleSolution.getText());
	    		
	    		for(int index = 0; index < tfClueText.size(); index++)
	    			riddleBean.addClueListElement(tfClueText.get(index).getText());
	    		    		
	    		objectBean.setObject(cmbObject.getSelectionModel().getSelectedItem());
	    		
	    		
	    		
	    		String riddleLabel = String.format("Number: %s   Text: %s   Solution: %s   Clue1: %s   Clue2: %s   Clue3: %s   Object: %s", 
	    									   String.valueOf(rdlList.size()), tfRiddleText.getText(), tfRiddleSolution.getText(), tfClueText.get(0).getText(), tfClueText.get(1).getText(), tfClueText.get(2).getText(), cmbObject.getSelectionModel().getSelectedItem());
	    		
	    		rdlList.add(riddleLabel);
	    		
	    		lbRiddle.setText(RIDDLE + rdlList.size());
	    		
	    		btnAddRiddle.setText("Add new Riddle");
	    		
	    		cancelTextView();
	    	}
	    	else {
	    	
	    		lbRiddleError.setVisible(true);
	    		lbRiddleError.setText("There are the Textes of the already added Riddle");
	    		
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
	    		
	    		if(!(objectName.equals(""))) {
	    			
		    		for(int i = 0; i < lvObject.getItems().size(); i++){
		    			
		    			if(objectName.equals(lvObject.getItems().get(i))) {
		    				
		    				flag = true;
		    				break;
		    			}
		    			
		    		}
		    		
		    		if(!(flag)) {
		    			
		    			objList.add(objectName);
		    			
		    			objectBean.setObject(objectName);
		    			
		    			
		    			
		    			if(lbErrorObjName.isVisible()) {
		    				lbErrorObjName.setVisible(false);
		    			}
		    			
		    		} else {
		    			
		    			lbErrorObjName.setVisible(true);
		    			lbErrorObjName.setText("The name has already been entered");
		    			
		    		}
		    	
	    		} else {

	    			lbErrorObjName.setText("Insert a valid Name");
					lbErrorObjName.setVisible(true);
				
	    			
	    		}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    @FXML
	    void handleRemoveObject(ActionEvent event) {
	    	
	    	
	    	
	    	objList.remove(lvObject.getSelectionModel().getSelectedIndex());
	    		
	    	
	    		
	    	rdlList.remove(lvObject.getSelectionModel().getSelectedIndex());
	    		
	    }
	    
	    @FXML
	    void handleObjectSelected(MouseEvent event) {
	    	
	    	visible(event, lvObject.getSelectionModel().getSelectedItem());
	    
	    }
	    
	    @FXML
	    void handleUplaodFile(ActionEvent event) {
	    	//TODO
	    }
	    
	    @FXML
	    void handleModifyRiddle(ActionEvent event) {
	    	
	    	int index = lvRiddle.getSelectionModel().getSelectedIndex();
	    	
	    	if(index != -1) {
	    		
	    		if(lbRiddleError.isVisible())
	    			lbRiddleError.setVisible(false);
	    		
	    		cancelTextView();
	    		
	    		
	    		
	    		int nRiddle = riddleBean.getnRiddle();
	    		
	    		tfRiddleText.setText(riddleBean.getRiddleText());
	    		
	    		tfRiddleSolution.setText(riddleBean.getSolutionText());
	    		
	    		for(int i = 0; i < tfClueText.size(); i++) 
	    			tfClueText.get(i).setText(riddleBean.getClueListElement(i));
	    		
	    		btnAddRiddle.setText("Add modify");
	    		
	    		lbRiddle.setText(RIDDLE + nRiddle);
	    		
	    		
	    		
	    		rdlList.remove(nRiddle);
	    		
	    	} else {
	    		
	    		lbRiddleError.setText("You must selected an item from the List");
	    		lbRiddleError.setVisible(true);
	    		
	    	}
	    	
	    }
	    
	    @FXML
	    void handleFinish(ActionEvent event) {
	    	if(tfHuntName.getText().equals("")) {
	    		
	    		lbErrorObjName.setText("Insert the Hunt name");
	    		lbErrorObjName.setVisible(true);
	    		
	    	}
	    	else {
	    		Hunt hunt = new Hunt();
	    		
	    		hunt.setHuntName(tfHuntName.getText());
	    		
	    	}
	    }
	    

	    private void cancelTextView() {
	    	tfRiddleText.setText("");
	    	
	    	tfRiddleSolution.setText("");
	    	
	    	tfClueText1.setText("");
	    	
	    	tfClueText2.setText("");
	    	
	    	tfClueText3.setText("");

	    }
	    
	    private void visible(MouseEvent type, String element) {
	    	if((type.getEventType() == MouseEvent.MOUSE_CLICKED) && (element != null))
	    		btnRemoveObject.setVisible(true);
	    	else
	    		btnRemoveObject.setVisible(false);
	    }
}
