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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import logic.model.entity.Hunt;
import logic.bean.HuntBean;
import logic.bean.ObjectBean;
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
    
    @FXML
    private TextArea txtDescription;
	
	ManageHuntControl manageHuntControl = new ManageHuntControl();
	
	ObjectBean objectBean = new ObjectBean();
	HuntBean huntBean = new HuntBean();
	
	int deleteRiddle = 0;
	
    @FXML
    void initialize() {
    	
    	int idHunt = manageHuntControl.createHunt();
    	huntBean.setIdHunt(idHunt);
    	
    	lbRiddle.setText("Riddle " + rdlList.size());
    	
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
    		
    		huntBean.setnRiddle(rdlList.size());
    		
    		if(lbRiddleError.isVisible())
    			lbRiddleError.setVisible(false);
    		
    		huntBean.setRiddleText(tfRiddleText.getText());
    		
    		huntBean.setSolutionText(tfRiddleSolution.getText());
    		
    		for(int index = 0; index < tfClueText.size(); index++)
    			huntBean.addClueListElement(index, tfClueText.get(index).getText());
    		    		
    		objectBean.setName(cmbObject.getSelectionModel().getSelectedItem());
    		
    		manageHuntControl.addRiddle(objectBean, huntBean, "nomeZona");
    		
    		rdlList.add(tfRiddleText.getText());
    		
    		lbRiddle.setText("Riddle " + rdlList.size());
    		
    		btnAddRiddle.setText("Add new Riddle");
    		
    		cancelTextView();
    		
    	} else {
    	
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
    		
    		index = lvRiddle.getSelectionModel().getSelectedIndex() + deleteRiddle;
    		
    		huntBean.setnRiddle(index);
    		
    		rdlList.remove(index);
    		
    		manageHuntControl.deleteRiddle(huntBean);
    		
    		if(index != rdlList.size()-1)
    			deleteRiddle++;
    		
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleModifyRiddle(ActionEvent event) {
    	
    	int index = lvRiddle.getSelectionModel().getSelectedIndex() + deleteRiddle;
    	
    	if(index != -1) {
    		
    		if(lbRiddleError.isVisible())
    			lbRiddleError.setVisible(false);
    		
    		cancelTextView();
    		
    		huntBean.setnRiddle(index);
    		
    		huntBean = manageHuntControl.modifyRiddle(huntBean);
    		
    		int nRiddle = huntBean.getnRiddle();
    		
    		tfRiddleText.setText(huntBean.getRiddleText());
    		
    		tfRiddleSolution.setText(huntBean.getSolutionText());
    		
    		for(int i = 0; i < tfClueText.size(); i++) 
    			tfClueText.get(i).setText(huntBean.getClueListElement(i));
    		
    		btnAddRiddle.setText("Add modify");
    		
    		lbRiddle.setText("Riddle " + nRiddle);
    		
    		manageHuntControl.deleteRiddle(huntBean);
    		
    		rdlList.remove(nRiddle);
    		
    	} else {
    		
    		lbRiddleError.setText("You must selected an item from the List");
    		lbRiddleError.setVisible(true);
    		
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
	    			
	    			objectBean.setName(objectName);
	    			
	    			objectBean.setDescription(txtDescription.getText());
	    			
	    			manageHuntControl.addObject(objectBean, huntBean);
	    			
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
    	
    	int index = lvObject.getSelectionModel().getSelectedIndex();
    	
    	objList.remove(index);
    	
    	objectBean.setName(lvObject.getSelectionModel().getSelectedItem());
    		
    	huntBean = manageHuntControl.removeObject(objectBean, huntBean);
    		
    	List<Integer> indexList = huntBean.getIndexList();
    	
    	for(int i = 0; i < indexList.size(); i++) {
    		rdlList.remove(indexList.get(i) - deleteRiddle);
    	}
    	
    		
    }
    
    @FXML
    void handleModifyObject(ActionEvent event) {
    	
    	objectBean.setName(lvObject.getSelectionModel().getSelectedItem());
    	
    	objectBean = manageHuntControl.modifyObject(objectBean, huntBean);
    	
    	
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
    	
    	int index = lvObject.getSelectionModel().getSelectedIndex();
    	
    	if(index != -1) {
    		
    		if(lbErrorObjName.isVisible())
    			lbErrorObjName.setVisible(false);
    		
    		cancelTextView();
    		
    		//objectBean = manageHuntControl.modifyObject(index);
    		
    		tfObjectName.setText(objectBean.getObject());
    		
    	} else {
    		
    		lbErrorObjName.setText("You must selected an item from the List");
    		lbErrorObjName.setVisible(true);
    		
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
    		
    		manageHuntControl.addHunt(hunt);
    	}
    }
    
    
    
    private void cancelTextView() {
    	tfRiddleText.setText("");
    	
    	tfRiddleSolution.setText("");
    	
    	tfClueText1.setText("");
    	
    	tfClueText2.setText("");
    	
    	tfClueText3.setText("");

    }
}
