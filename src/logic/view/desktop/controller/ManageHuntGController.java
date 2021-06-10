package logic.view.desktop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.ObjectBean;
import logic.bean.RiddleBean;
import logic.control.ManageHuntControl;
import logic.control.ManageMapControl;
import logic.control.UploadFileControl;
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
    private Button btnRemoveObject;

    @FXML
    private Button btnModifyRiddle;

    @FXML
    private Button btnCreateMap;

    @FXML
    private TextField tfClueText1;
    
    @FXML
    private TextField tfClueText2;

    @FXML
    private TextField tfClueText3;
    
    private List<TextField> tfClueText = new ArrayList<>();


    @FXML
    private TextField tfObjectName;

    @FXML
    private Label lbRiddleError;
    

    @FXML
    private TextArea txtDescription;
    
    @FXML
    private Button btnSave;
    
    @FXML
    private ComboBox<String> cmbZone;
    
    @FXML
    private ImageView imgMap;
    
    @FXML
    private Label lbMapName;
    
    private ObservableList<String> zoneList = FXCollections.observableArrayList();
	
	
	private HuntBean huntBean = new HuntBean();
	private MapBean mapBean = new MapBean();
	
	private String riddle;
	private int idMap = -1;
	private HashMap<String, String> objectPath = new HashMap<>();
	private String filePath = null;
	
	private static final String SEPARATOR = "; ";
	private static final String ERRORSELECTED = "You must selected an item from the List";
	private static final String EMPTY = "empty";
	
	Alert alert = new Alert(AlertType.ERROR, "Error", ButtonType.CLOSE);
	
	@Override
	void start(String arg, Object param) {
		var manageHuntControl = new ManageHuntControl();
		try {
			huntBean.setUsername(getUsername());
		} catch (UsernameNotLoggedException e) {
			alert.setContentText("Error with Login");
			try {
				changeScene(Pages.LOGIN, null, null);
			} catch (PageNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if(arg != null) {
			if(arg.equals("hunt")) {
				huntBean.setIdHunt((int) param);
			} else {
				List<?> ids = (List<?>) param;
				huntBean.setIdHunt((Integer) ids.get(0));
				idMap = (Integer) ids.get(1);
				setMap();
			}
			huntBean = manageHuntControl.getHunt(huntBean);
		} else {
			huntBean.setIdHunt(-1);
		}
		
		lbRiddle.setText("Riddle " + rdlList.size());
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
    		
    		for(var i = 0; i < tfClueText.size(); i++) {
    			riddle = riddle.concat(isEmpty(tfClueText.get(i).getText()));
    		}
    		
    		riddle = riddle.concat(isEmpty(cmbObject.getSelectionModel().getSelectedItem()));
    		
    		riddle = riddle.concat(isEmpty(cmbZone.getSelectionModel().getSelectedItem()));

    			
    		rdlList.add(riddle);
    		
    		lbRiddle.setText("Riddle " + (rdlList.size()));
    		
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
    
    private String isEmpty(String content) {
    	if(content != null && !(content.equals("")))
    		return content + SEPARATOR;
    	else
    		return EMPTY + SEPARATOR;
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
    			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleModifyRiddle(ActionEvent event) {
    	
    	int index = lvRiddle.getSelectionModel().getSelectedIndex();
    	
    	if(index != -1) {
    		
    		riddle = lvRiddle.getItems().get(index);
    		
    		setLabel(lbRiddleError, "", false);
    		
    		var st = new StringTokenizer(riddle, SEPARATOR);
    		
    		if(st.hasMoreElements()) {
    			tfRiddleText.setText(st.nextToken());
    			
    			tfRiddleSolution.setText(st.nextToken());
    			
    			for(var i = 0; i < tfClueText.size(); i++)
    				tfClueText.get(i).setText(isTokenEmpty(st.nextToken()));
    			
    			cmbObject.setValue(isTokenEmpty(st.nextToken()));
    			
    			cmbZone.setValue(isTokenEmpty(st.nextToken()));
    			
    			lvRiddle.getItems().remove(index);
    		} else {
        		setLabel(lbRiddleError, ERRORSELECTED, true);
    		}
 
    	} else {
    		
    		setLabel(lbRiddleError, ERRORSELECTED, true);
    	}
    	
    }
    
    private String isTokenEmpty(String content) {
    	if(content.equals(EMPTY))
    		return "";
    	else
    		return content; 
    }
    
    @FXML
    void handleCreateMap(ActionEvent event) throws PageNotFoundException {
    	huntBean.setIdHunt(save());
    	changeScene(Pages.MANAGE_MAP, "hunt", huntBean.getIdHunt());
    	
    }

    @FXML
    void handleChooseMap(ActionEvent event) throws PageNotFoundException {
    	
    	Parent root;
        try {
			root = FXMLLoader.load(getClass().getResource(Pages.CHOOSE_MAP.getPath()));
			var stage = new Stage();
	        stage.setTitle("Choose a map");
	        stage.setScene(new Scene(root, stage.getHeight(), stage.getWidth()));
	        stage.showAndWait();
	        if(idMap != -1)
	        	setMap();
		} catch (IOException e) {
    		alert.setContentText("Try again");
    		alert.showAndWait();
		}
        
    	
    }
    
    private void setMap() {
    		
		var mpc = new ManageMapControl();
		
		mapBean = mpc.getMapById(huntBean.getUsername(), idMap);
		
		
		var img = new Image("file:" + mapBean.getImage(), imgMap.getFitWidth(), imgMap.getFitHeight(), false, false);
    	imgMap.setImage(img);
    	btnCreateMap.setVisible(false);
    	btnChooseMap.setText("Choose Another Map");
    	lbMapName.setVisible(true);
		lbMapName.setText(mapBean.getName());
		for(var i = 0; i < mapBean.getZones().size(); i++)
			zoneList.add(mapBean.getZones().get(i).getNameZone());
		
	
    }

    @FXML
    void handleAddObject(ActionEvent event) {
    	
    	String objectName = tfObjectName.getText();
    	var flag = false;
   
    	try {
    		
    		if(!(objectName.equals(""))) {
	    		for(var i = 0; i < lvObject.getItems().size(); i++){
	    			if(objectName.equals(lvObject.getItems().get(i))) {
	    				flag = true;
	    			}
	    		}

	    		if(flag) {
	    			setLabel(lbErrorObjName, "An object with this name already exist", true);
	    		}else {
	    			setLabel(lbErrorObjName, "", false);
	    			if(filePath != null) {
	    				objectPath.put(objectName, filePath);
	    				filePath = null;
	    				btnUploadFile.setText("Upload File");
	    			}
	    			objectName = objectName.concat(SEPARATOR + txtDescription.getText());
	    			objList.add(objectName);
	    		}
	    		
	    		 
    		} else {
				setLabel(lbErrorObjName, "Insert a valid Name", true);
	    	}
    	}
		catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    private void setLabel(Label lb, String content, Boolean vis) {
		lb.setText(content);
		if(lb.isVisible() == vis)
			lb.setVisible(vis);
    }
    
    @FXML
    void handleRemoveObject(ActionEvent event) {
    	
    	int index = lvObject.getSelectionModel().getSelectedIndex();
    	
    	String tempName = lvObject.getSelectionModel().getSelectedItem();
    	
    	var st = new StringTokenizer(tempName, SEPARATOR);
    	
    	if(st.hasMoreElements()) {
    		tempName = st.nextToken();
			objectPath.remove(tempName);
    	}
    	objList.remove(index);
    	
    	
    }
    
    @FXML
    void handleModifyObject(ActionEvent event) {
    	
    	String object = lvObject.getSelectionModel().getSelectedItem();
    	
    	objList.remove(lvObject.getSelectionModel().getSelectedIndex());
    	
    	var st = new StringTokenizer(object, SEPARATOR);
    	
    	if(st.hasMoreElements()) {
    		tfObjectName.setText(st.nextToken());
    		
    		txtDescription.setText(isTokenEmpty(st.nextToken()));
    		
    		if(objectPath.containsKey(tfObjectName.getText())){
    			objectPath.remove(tfObjectName.getText());
    			btnUploadFile.setText("Change File");
    		}
    	}
    	
    	
    }
    
    @FXML
    void handleObjectSelected(MouseEvent event) {
    	
    	btnRemoveObject.setVisible(setVisible(event, lvObject.getSelectionModel().getSelectedItem()));
    	
    }
    
    
    @FXML
    void handleUplaodFile(ActionEvent event) {
    	var fileChooser = new FileChooser();
    	var uploadFileControl = new UploadFileControl();
    	fileChooser.setTitle("Choose Image");
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")
    			);
    	
    	var file = fileChooser.showOpenDialog(null);
		filePath = uploadFileControl.uploadFile(file);
		
		btnUploadFile.setText("Change File");
		
    }
    
    @FXML
    void handleSave(MouseEvent event) {
    	save();
    }
    
    @FXML
    void handleFinish(ActionEvent event) throws PageNotFoundException {
    	if(tfHuntName.getText().equals("")) {
    		save();
    		changeScene(Pages.MAIN_MENU);
    		
    	}
    	else {
    		alert.setContentText("Inserisci il nome dell'Hunt");
    		alert.showAndWait();
    	}
    }
    
    
    
    private void cancelTextView() {
    	tfRiddleText.setText("");
    	
    	tfRiddleSolution.setText("");
    	
    	for(var i = 0; i < tfClueText.size(); i++)
    		tfClueText.get(i).setText("");

    }
    
    private boolean setVisible(MouseEvent e, String riddleSelected) {
    	return (e.getEventType() == MouseEvent.MOUSE_CLICKED) && riddleSelected != null;
    	
    }
    
    public void setIdMap(int idMap) {
    	this.idMap = idMap;
    }
    
	
	private int save() {
		
		List<ObjectBean> objectBean = new ArrayList<>();
		List<RiddleBean> riddleBean = new ArrayList<>();
		
		var manageHuntControl = new ManageHuntControl();
		int idHunt = -1;
		for(var i = 0; i < rdlList.size(); i++) {
			var st = new StringTokenizer(rdlList.get(i), SEPARATOR);
    		
    		if(st.hasMoreElements()) {
    			var rb = new RiddleBean();
    			
    			rb.setRiddle(st.nextToken());
    			
    			rb.setSolution(st.nextToken());
    			
    			for(var j = 0; j < tfClueText.size(); j++) 
    				rb.setClueElement(j, isTokenEmpty(st.nextToken()));
    			
    			rb.setObjectName(isTokenEmpty(st.nextToken()));
    			
    			rb.setZoneName(isTokenEmpty(st.nextToken()));
    			
    			riddleBean.add(rb);
    		}
    		
		}
		for(var i = 0; i < objList.size(); i++) {
			var st = new StringTokenizer(objList.get(i),SEPARATOR);
			if(st.hasMoreElements()) {
				var name = st.nextToken();
				
				var desc = st.nextToken();
				
				var path = objectPath.get(name);
				
				var or = new ObjectBean(name, desc, path);
				
				
				objectBean.add(or);
			}
			
		}
		
		huntBean.setHuntName(tfHuntName.getText());
		huntBean.setMap(mapBean);
		huntBean.setRiddle(riddleBean);
		huntBean.setObject(objectBean);
		
		idHunt = manageHuntControl.saveHunt(huntBean);
		
		return idHunt;
		
	}

}
