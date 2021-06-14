package logic.view.desktop.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
import logic.enumeration.StringHardCode;
import logic.exception.LoadFileFailed;
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
    
    private List<TextField> tfComponent = new ArrayList<>();

    @FXML
    private TextField tfObjectName;
    

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
	
	private int idMap = -1;
	private HashMap<String, String> objectPath = new HashMap<>();
	private HashMap<Integer, String> object = new HashMap<>();
	private HashMap<Integer, String> zone = new HashMap<>();
	private String filePath = null;
	
	private static final String SEPARATOR = "\n";
	private static final String ERRORSELECTED = "You must selected an item from the List";
	private static final String NEWRIDDLE = "Add new Riddle";
	private static final String NOWROTE = "Riddle text or solution area's are empty";
	private static final String NONAME = "Object name area's is empty";
	private static final String HUNTNAME = "You must insert the name of the Hunt or at least one riddle to Save";
	private static final String OBJECTNAME = "An object with this name already exist";
	Alert alert = new Alert(AlertType.ERROR, "Error", ButtonType.CLOSE);
	
	
	@Override
	void start(String arg, Object param) {
		
		tfComponent.add(tfRiddleText);
		tfComponent.add(tfRiddleSolution);
		tfComponent.add(tfClueText1);
		tfComponent.add(tfClueText2);
		tfComponent.add(tfClueText3);
		var manageHuntControl = new ManageHuntControl();
		
		try {
			huntBean.setUsername(getUsername());
		} catch (UsernameNotLoggedException e) {
			
			errorAlert(StringHardCode.ERRORLOGIN.returnString());
			try {
				changeScene(Pages.LOGIN, null, null);
			} catch (PageNotFoundException e1) {
				errorAlert(StringHardCode.ERROR.returnString());
			}
		}
		switch(arg) {
			case "hunt":
				huntBean.setIdHunt((int) param);
				huntBean = manageHuntControl.getHunt(huntBean);
				
				setHunt(huntBean);
				break;
				
			case "map":
				List<?> ids = (List<?>) param;
				huntBean.setIdHunt((Integer) ids.get(0));
				idMap = (Integer) ids.get(1);
				setMap();
				huntBean = manageHuntControl.getHunt(huntBean);
				
				setHunt(huntBean);
				break;
			default:
				huntBean.setIdHunt(-1);
		}
		
		lbRiddle.setText("Riddle " + rdlList.size());
    	lvObject.setItems(objList);
    	lvRiddle.setItems(rdlList);
    	cmbObject.setItems(objList);
    	cmbZone.setItems(zoneList);
    	
    	
	}
	
	private void setHunt(HuntBean huntBean) {
		
		List<ObjectBean> objectBean;
		List<RiddleBean> riddleBean;
		tfHuntName.setText(huntBean.getHuntName());
		riddleBean = huntBean.getRiddle();
		
		for(var i = 0; i < riddleBean.size(); i++) {
			var rb = riddleBean.get(i);
			var s = "";
			s = s.concat(rb.getRiddle() + SEPARATOR);
			s = s.concat(rb.getSolution()+ SEPARATOR);
			
			for(var j = 0; j < rb.getClue().size(); j++) {
				s = s.concat((rb.getClueElement(j)) + SEPARATOR);
			}
			
			object.put(i, rb.getObjectName());
			zone.put(i, rb.getZoneName());
			rdlList.add(s);
		}
		
		
		objectBean = huntBean.getObject();
		for(var i = 0; i < objectBean.size(); i++) {
			var obj = objectBean.get(i);
			
			var s = String.format("%s%n%s", obj.getObject(), obj.getDescription());
			objList.add(s);
			if(!(obj.getPath().equals(""))) {
				objectPath.put(obj.getObject(), obj.getPath());
			}
		}
	}
    
	
    @FXML
    void handlerAddRiddle(ActionEvent event) {
    	
    	
    	if(!(tfRiddleText.getText().equals("")) && !(tfRiddleSolution.getText().equals(""))){
    		
    		var temp = "";
    		
    		for(var i = 0; i < tfComponent.size(); i++) {
    			if(!(tfComponent.get(i).getText().equals("")))
    				temp = temp.concat(tfComponent.get(i).getText() + SEPARATOR);
    		}
    		var it = cmbObject.getSelectionModel().getSelectedItem().lines().iterator();
    		if(it.hasNext())
    			object.put(rdlList.size(), it.next());
    		else
    			object.put(rdlList.size(), null);
    		zone.put(rdlList.size(), cmbZone.getSelectionModel().getSelectedItem());
    		
    		rdlList.add(temp);
    		
    		lbRiddle.setText("Riddle " + (rdlList.size()));
    		
    		btnAddRiddle.setText(NEWRIDDLE);
    		
    		cancelTextView();
    		
    		
    	} else {
    		errorAlert(NOWROTE);
    		
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
    		
    		if(index != -1) {
    			rdlList.remove(index);
    			object.remove(index);
    			zone.remove(index);
    		} else {
    			errorAlert(ERRORSELECTED);
    		}
    			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleModifyRiddle(ActionEvent event) {
    	
    	int index = lvRiddle.getSelectionModel().getSelectedIndex();
    	
    	if(index != -1) {
    		
    		var i = 0;
    		
    		var riddle = lvRiddle.getItems().get(index);
    		
    		var rid = riddle.lines().iterator();
    		
    		while(rid.hasNext()) {
    			tfComponent.get(i).setText(rid.next());
    			i++;
    		}
    		
    		cmbObject.setValue(object.get(index));
    		cmbZone.setValue(zone.get(index));
    		
    		rdlList.remove(index);
 
    	} else {
    		
    		errorAlert(ERRORSELECTED);
    	}
    	
    }
    
    
    
    @FXML
    void handleCreateMap(ActionEvent event) {
    	huntBean.setIdHunt(save());
    	try {
			changeScene(Pages.MANAGE_MAP, "hunt", huntBean.getIdHunt());
		} catch (PageNotFoundException e) {
			errorAlert(StringHardCode.ERROR.returnString());
		}
    	
    }

    @FXML
    void handleChooseMap(ActionEvent event)  {
    	
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
    		errorAlert(StringHardCode.ERROR.returnString());
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
    		
		if(!(objectName.equals(""))) {
    		for(var i = 0; i < lvObject.getItems().size(); i++){
    			var it = lvObject.getItems().get(i).lines().iterator();
    			if(it.hasNext() && objectName.equals(it.next())) {
    				flag = true;
    				break;
    			}
    		}

    		if(flag) {
    			errorAlert(OBJECTNAME);
    		}else {
    			
    			if(filePath != null) {
    				objectPath.put(objectName, filePath);
    				filePath = null;
    				btnUploadFile.setText("Upload File");
    			}
    			objectName = objectName.concat(SEPARATOR + txtDescription.getText());
    			objList.add(objectName);
    		}
    		
    		 tfObjectName.setText("");
    		 txtDescription.setText("");
		} else {
			errorAlert(NONAME);
    	}
    }

    @FXML
    void handleRemoveObject(ActionEvent event) {
    	
    	int index = lvObject.getSelectionModel().getSelectedIndex();
    	
    	if(index == -1) {
	    	var it = lvObject.getSelectionModel().getSelectedItem().lines().iterator();
	    	
	    	if(it.hasNext()) {
				objectPath.remove(it.next());
	    	}
	    	
	    	objList.remove(index);
    	} else {
    		errorAlert(ERRORSELECTED);
    	}
    	
    	
    }
    
    @FXML
    void handleModifyObject(ActionEvent event) {
    	
    	
    	var index = lvObject.getSelectionModel().getSelectedIndex();
    	if(index != -1) {
	    	
	    	var it = lvObject.getSelectionModel().getSelectedItem().lines().iterator();
	    	
	    	if(it.hasNext()) {
	    		tfObjectName.setText(it.next());
	    		
	    		txtDescription.setText(it.next());
	    		
	    		if(objectPath.containsKey(tfObjectName.getText())){
	    			objectPath.remove(tfObjectName.getText());
	    			btnUploadFile.setText("Change File");
	    		}
	    	}
	    	objList.remove(index);
    	} else {
    		errorAlert(ERRORSELECTED);
    	}
    	
    	
    }
    
    @FXML
    void handleObjectSelected(MouseEvent event) {
    	
    	btnRemoveObject.setVisible(setVisible(event, lvObject.getSelectionModel().getSelectedItem()));
    	
    }
    
    
    @FXML
    void handleUploadFile(ActionEvent event) {
    	var fileChooser = new FileChooser();
    	var uploadFileControl = new UploadFileControl();
    	
    	fileChooser.setTitle("Choose Image");
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")
    			);
    	
    	var file = fileChooser.showOpenDialog(btnUploadFile.getScene().getWindow());
		try {
			filePath = uploadFileControl.uploadFile(file);
			btnUploadFile.setText("Change File");
		} catch (LoadFileFailed e) {
			btnUploadFile.setText("Upload File");
			errorAlert(StringHardCode.FILE.returnString());
		}
		
		
        
		
    }
    
    @FXML
    void handleSave(ActionEvent event) {
    	if(rdlList.isEmpty())
    		huntBean.setIdHunt(save());
    	else
    		errorAlert(HUNTNAME);
    }
    
    @FXML
    void handleFinish(ActionEvent event) {
    	if(tfHuntName.getText().equals("") && rdlList.isEmpty()) {
    		save();
    		try {
				changeScene(Pages.MAIN_MENU);
			} catch (PageNotFoundException e) {
				errorAlert(StringHardCode.ERROR.returnString());
			}
    		
    	}
    	else {
    		alert.setContentText(HUNTNAME);
    		alert.showAndWait();
    	}
    }
    
    
    
    private void cancelTextView() {
    	for(var i = 0; i < tfComponent.size(); i++)
    		tfComponent.get(i).setText("");

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
		for(var i = 0; i < rdlList.size(); i++) {
			
			var it = rdlList.get(i).lines().iterator();
    		
    		if(it.hasNext()) {
    			var rb = new RiddleBean();
    			
    			rb.setRiddle(it.next());
    			
    			rb.setSolution(it.next());
    		
    			var j = 0;
    			while(it.hasNext()) {
    				rb.setClueElement(j, (it.next()));
    				j++;
    			}
    			
    			rb.setObjectName(object.get(i));
    			rb.setZoneName(zone.get(i));
    			
    			riddleBean.add(rb);
    		}
    		
		}
		for(var i = 0; i < objList.size(); i++) {
			var it = objList.get(i).lines().iterator();
			if(it.hasNext()) {
				var name = it.next();
				
				var desc = it.next();
				
				var path = objectPath.get(name);
				
				var or = new ObjectBean(name, desc, path);
				objectBean.add(or);
			}
			
		}
		huntBean.setHuntName(tfHuntName.getText());
		huntBean.setMap(mapBean);
		huntBean.setRiddle(riddleBean);
		huntBean.setObject(objectBean);
		
		return manageHuntControl.saveHunt(huntBean);
		
	}
	
	private void errorAlert(String cont) {
		alert.setContentText(cont);
		alert.showAndWait();
	}
	
}
