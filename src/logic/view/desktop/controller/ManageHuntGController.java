package logic.view.desktop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.ObjectBean;
import logic.bean.RiddleBean;
import logic.bean.ZoneBean;
import logic.control.ManageHuntControl;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundException;
import logic.exception.UsernameNotLoggedException;

public class ManageHuntGController extends ControllerWithLogin{
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
    private TextField tfClueText1;
    
    @FXML
    private TextField tfClueText2;

    @FXML
    private TextField tfClueText3;
    
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
    
    @FXML
    private Button btnSave;
    
    @FXML
    private ComboBox<String> cmbZone;
    
    private ObservableList<String> zoneList = FXCollections.observableArrayList();
    
	ManageHuntControl manageHuntControl = new ManageHuntControl();
	
	private List<ObjectBean> objectList = new ArrayList<>();
	private List<RiddleBean> riddleBean = new ArrayList<>();
	HuntBean huntBean = new HuntBean();
	
	private String riddle;
	
	int deletedRiddle = 0;
	int deletedObject = 0;
	
	static final String SEPARATOR = "; ";
	static final String ERRORSELECTED = "You must selected an item from the List";
	
    @FXML
    void initialize() {
    	
    	int idHunt = -1;
    	huntBean.setIdHunt(idHunt);
    	
    	lbRiddle.setText("Riddle " + rdlList.size());
    	zoneList.add("");
    	lvObject.setItems(objList);
    	lvRiddle.setItems(rdlList);
    	cmbObject.setItems(objList);
    	cmbZone.setItems(zoneList);
    	
    	tfClueText.add(tfClueText1);
    	tfClueText.add(tfClueText2);
    	tfClueText.add(tfClueText3);
    	
    }
    
    @FXML
    void handlerAddRiddle(ActionEvent event) {
    	
    	
    	if(!(tfRiddleText.getText().equals("")) && !(tfRiddleSolution.getText().equals(""))){
    		
    		if(lbRiddleError.isVisible())
    			lbRiddleError.setVisible(false);
    		
    		riddle = String.format("%s; %s;", tfRiddleText.getText(), tfRiddleSolution.getText());
    		
    		for(var i = 0; i < 3; i++) {
    			riddle = riddle.concat(tfClueText.get(i).getText() + SEPARATOR);
    		}
    		
    		riddle = riddle.concat(cmbObject.getSelectionModel().getSelectedItem() + SEPARATOR);
    		riddle = riddle.concat(cmbZone.getSelectionModel().getSelectedItem() + SEPARATOR);
    			
    		rdlList.add(riddle);
    		
    		lbRiddle.setText("Riddle " + (rdlList.size()-deletedRiddle));
    		
    		btnAddRiddle.setText("Add new Riddle");
    		
    		cancelTextView();
    		
    		if(lbRiddleError.isVisible()) {
    			lbRiddleError.setVisible(false);
    		}
    		
    	} else {
    	
    		lbRiddleError.setVisible(true);
    		lbRiddleError.setText("There are the Textes of the already added Riddle");
    		
    	}
    }
    
    @FXML
    void handleRiddleSelected(MouseEvent event) {
    	
    	btnRemoveRiddle.setVisible(setVisible(event, lvRiddle.getSelectionModel().getSelectedItem()));
    	
    }
    
    @FXML
    void handleRemoveRiddle(ActionEvent event) {
    	try {
    		
    		int index = lvRiddle.getSelectionModel().getSelectedIndex();
    		
    		if(index == -1) 
    			rdlList.remove(index);
    		else {
    			lbRiddleError.setText(ERRORSELECTED);
        		lbRiddleError.setVisible(true);
    		}
    			
    		deletedRiddle++;
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleModifyRiddle(ActionEvent event) {
    	
    	int index = lvRiddle.getSelectionModel().getSelectedIndex();
    	
    	if(index != -1) {
    		
    		riddle = lvRiddle.getItems().get(index);
    		
    		if(lbRiddleError.isVisible())
    			lbRiddleError.setVisible(false);
    		
    		var st = new StringTokenizer(riddle, SEPARATOR);
    		
    		if(st.hasMoreElements()) {
    			tfRiddleText.setText(st.nextToken());
    			
    			tfRiddleSolution.setText(st.nextToken());
    			
    			for(var i = 0; i < 3; i++)
    				tfClueText.get(i).setText(st.nextToken());
    			
    			cmbObject.setValue(st.nextToken());
    			
    			cmbZone.setValue(st.nextToken());
    			
    			lvRiddle.getItems().remove(index);
    			deletedRiddle++;
    		} else {
    			lbRiddleError.setText(ERRORSELECTED);
        		lbRiddleError.setVisible(true);
    		}
 
    	} else {
    		
    		lbRiddleError.setText(ERRORSELECTED);
    		lbRiddleError.setVisible(true);
    		
    	}
    	
    }
    
    @FXML
    void handleCreateMap(ActionEvent event) throws PageNotFoundException {
    	changeScene(Pages.MANAGE_MAP);
    }

    @FXML
    void handleChooseMap(ActionEvent event) throws UsernameNotLoggedException {
    	List<MapBean> mapList;
    	var mpc = new ManageMapControl();
    	
    	mapList = mpc.getAllMaps(getUsername());
    	
    	
    	
    	
    }

    @FXML
    void handleAddObject(ActionEvent event) {
    	try {
    		
    		String objectName = tfObjectName.getText();
    		
    		if(!(objectName.equals(""))) {
    			
	    		for(var i = 0; i < lvObject.getItems().size(); i++){
	    			
	    			if(!(objectName.equals(lvObject.getItems().get(i)))) {
	    				
	    				objectName = objectName.concat(String.format("%s; %s;", txtDescription.getText(), "Path"));
	    				objList.add(objectName);	    		
	    			}
	    			
	    		}

	    			
	    		if(lbErrorObjName.isVisible()) 
	    			lbErrorObjName.setVisible(false);
	    		 
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
    	
    	deletedObject++;
    }
    
    @FXML
    void handleModifyObject(ActionEvent event) {
    	
    	String object = lvObject.getSelectionModel().getSelectedItem();
    	
    	objList.remove(lvObject.getSelectionModel().getSelectedIndex());
    	
    	var st = new StringTokenizer(object, SEPARATOR);
    	
    	if(st.hasMoreElements()) {
    		tfObjectName.setText(st.nextToken());
    		
    		txtDescription.setText(st.nextToken());
    		
    	}
    	
    	deletedObject++;
    	
    }
    
    @FXML
    void handleObjectSelected(MouseEvent event) {
    	
    	btnRemoveObject.setVisible(setVisible(event, lvObject.getSelectionModel().getSelectedItem()));
    	
    }
    
    
    @FXML
    void handleUplaodFile(ActionEvent event) {

    }
    

    @FXML
    void handleSave(ActionEvent event) {
		
    }
    
    @FXML
    void handleFinish(ActionEvent event) {
    	if(tfHuntName.getText().equals("")) {
    		
    		
    		
    	}
    	else {
    		
    	}
    }
    
    
    
    private void cancelTextView() {
    	tfRiddleText.setText("");
    	
    	tfRiddleSolution.setText("");
    	
    	tfClueText1.setText("");
    	
    	tfClueText2.setText("");
    	
    	tfClueText3.setText("");

    }
    
    private boolean setVisible(MouseEvent e, String riddleSelected) {
    	return (e.getEventType() == MouseEvent.MOUSE_CLICKED) && riddleSelected != null;
    	
    }
	@Override
	void start(Object param) {
		
	}
}
