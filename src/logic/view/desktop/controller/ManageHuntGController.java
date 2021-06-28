package logic.view.desktop.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.callback.Callback;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import logic.exception.UsernameNotLoggedException;

public class ManageHuntGController extends ControllerWithLogin{
	@FXML
    private Label lbRiddle;

    @FXML
    private TextField tfHuntName;

    @FXML
    private ComboBox<String> cmbObject;

    @FXML
    private TextField tfRiddleSolution;

    @FXML
    private TextField tfRiddleText;

    @FXML
    private Button btnChooseMap;

    @FXML
    private Button btnUploadFile;

    @FXML
    private ListView<String> lvObject;

    private ObservableList<String> objList = FXCollections.observableArrayList();
    
    private ObservableList<RiddleBean> rdlList = FXCollections.observableArrayList();
    
    @FXML
    private Button btnAddRiddle;

    @FXML
    private Button bntFinish;

    @FXML
    private Button btnAddToList;

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
    
    @FXML
    private CheckBox cbPrivate;
    
    private ObservableList<String> zoneList = FXCollections.observableArrayList();
	
    @FXML
    private TableView<RiddleBean> tbRiddle;
    @FXML
    private TableColumn<RiddleBean, String> cmText;
    @FXML
    private TableColumn<RiddleBean, String> cmSolution;
    @FXML
    private TableColumn<RiddleBean, String> cmClue1;
    @FXML
    private TableColumn<RiddleBean, String> cmClue2;
    @FXML
    private TableColumn<RiddleBean, String> cmClue3;
    @FXML
    private TableColumn<RiddleBean, String> cmObject;
    @FXML
    private TableColumn<RiddleBean, String> cmZone;
    @FXML
    private TableColumn<RiddleBean, RiddleBean> cmButtons;
	
	private HuntBean huntBean = new HuntBean();
	private MapBean mapBean = new MapBean();
	
	private int idMap = -1;
	private HashMap<String, String> objectPath = new HashMap<>();
	private HashMap<Integer, String> object = new HashMap<>();
	private HashMap<Integer, String> zone = new HashMap<>();
	private String filePath = null;
	
	private static final String SEPARATOR = "\n";
	private static final String NEWRIDDLE = "Add new Riddle";
	private static final String NOWROTE = "Riddle text or solution area's are empty";
	private static final String NONAME = "Object name area's is empty";
	private static final String HUNTNAME = "You must insert the name of the Hunt or at least one riddle to Save";
	private static final String OBJECTNAME = "An object with this name already exist";
	private static final String RIDDLE = "Riddle ";
	
	@Override
	void start(String arg, Object param) {

		tfComponent.add(tfRiddleText);
		tfComponent.add(tfRiddleSolution);
		tfComponent.add(tfClueText1);
		tfComponent.add(tfClueText2);
		tfComponent.add(tfClueText3);
		
		var manageHuntControl = new ManageHuntControl();
		var id = -1;
		try {
			huntBean.setUsername(getUsername());
			if(arg.equals(StringHardCode.HUNT.getString())) {
				id = (int) param;
				huntBean.setIdHunt(id);
				
				huntBean = manageHuntControl.getHunt(id, getUsername());
				
				setHunt(huntBean);
			} else {
				if(arg.equals(StringHardCode.MAP.getString())) {
					List<?> ids = (List<?>) param;
					id = (Integer) ids.get(0);
					huntBean.setIdHunt(id);
					idMap = (Integer) ids.get(1);
					setMap();
					huntBean = manageHuntControl.getHunt(id, getUsername());
					setHunt(huntBean);
				} else {
					huntBean.setIdHunt(id);
				}
			}
		} catch (UsernameNotLoggedException e) {
			showAlert(StringHardCode.ERRORLOGIN.getString());
			changeScene(Pages.LOGIN);
		}
		
		setTable();
		
		lbRiddle.setText(RIDDLE + rdlList.size());
    	lvObject.setItems(objList);
    	cmbObject.setItems(objList);
    	cmbZone.setItems(zoneList);   	
    	tbRiddle.setItems(rdlList);
    	
	}
	
	private void setTable() {
		for(var i = 0; i < tbRiddle.getColumns().size(); i++) {
			tbRiddle.getColumns().get(i).prefWidthProperty().bind(tbRiddle.widthProperty().multiply(0.125));
		}
		
		cmText.setCellValueFactory(new PropertyValueFactory<>("riddle"));
		cmSolution.setCellValueFactory(new PropertyValueFactory<>("solution"));
		cmClue1.setCellValueFactory(new PropertyValueFactory<>("clue1"));
		cmClue2.setCellValueFactory(new PropertyValueFactory<>("clue2"));
		cmClue3.setCellValueFactory(new PropertyValueFactory<>("clue3"));
		cmObject.setCellValueFactory(new PropertyValueFactory<>("objectName"));
		cmZone.setCellValueFactory(new PropertyValueFactory<>("zoneName"));
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
			//rdlList.add(s);
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
    void handleAddRiddle(ActionEvent event) {
    	
    	
    	if(!(tfRiddleText.getText().equals("")) && !(tfRiddleSolution.getText().equals(""))){
    		
    		var nome = tfRiddleText.getText();
    		
    		var solution = tfRiddleSolution.getText();
    		
    		var clue1 = tfClueText1.getText();
    		
    		var clue2 = tfClueText2.getText();
    		
    		var clue3 = tfClueText3.getText();
    		
    		var objName = cmbObject.getSelectionModel().getSelectedItem();
    		
    		if(objName != null) {
    			var it = objName.lines().iterator();
	    		if(it.hasNext())
	    			objName = it.next();
    		}
    		
    		var zoneName = cmbZone.getSelectionModel().getSelectedItem();
    		
    		var temp = new RiddleBean(rdlList.size(),
    								  nome, solution, clue1, clue2, clue3,
    								  objName, zoneName);
    		
    		
    		cmButtons.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    		cmButtons.setCellFactory(param -> new TableCell<RiddleBean, RiddleBean>(){
    			@Override
    			protected void updateItem(RiddleBean riddleBean, boolean empty) {
    				super.updateItem(riddleBean, empty);
    				if(riddleBean != null) {
    					var gController = new ItemModifyRemoveG(Pages.MODIFYREMOVE, getIstance());
    			    	gController.setInfo(riddleBean);
    			    	setGraphic(gController.getBox());
    				} else {
    					setGraphic(null);
    				}
    			}
    		});
    		
    		rdlList.add(temp);
    		
    		lbRiddle.setText(RIDDLE + (rdlList.size()));
    		
    		btnAddRiddle.setText(NEWRIDDLE);
    		
    		cancelTextView();
    		
    		
    	} else {
    		showAlert(NOWROTE);
    	}
    }
    
    public void removeRiddle(int index) {
    	rdlList.remove(index);
    }
    
    public void modifyRiddle(int index) {
    	var rb = rdlList.get(index);
    	
    	lbRiddle.setText(RIDDLE + rb.getNumRiddle());
    	tfRiddleText.setText(rb.getRiddle());
    	tfRiddleSolution.setText(rb.getSolution());
    	tfClueText1.setText(rb.getClue1());
    	tfClueText2.setText(rb.getClue2());
    	tfClueText3.setText(rb.getClue3());
    	
    	var objName = rb.getObjectName();
    	if(objName != null) {
    		cmbObject.setValue(objName);
    	}
    	
    	var zoneName = rb.getZoneName();
    	
    	if(zoneName != null) {
    		cmbZone.setValue(zoneName);
    	}
    	
    	rdlList.remove(index);
    }
    
    
    
    @FXML
    void handleCreateMap(ActionEvent event) {
    	huntBean.setIdHunt(save());
		changeScene(Pages.MANAGE_MAP, "hunt", huntBean.getIdHunt());
    	
    }

    @FXML
    void handleChooseMap(ActionEvent event)  {
    	
    	var mpc = new ManageMapControl();
		var controllerChoose = new ChooseMapGController(Pages.CHOOSE_MAP, this);
		List<MapBean> mapsList = null;
    	
    	try {
			mapsList = mpc.getAllMaps(getUsername());
			controllerChoose.setInfo(mapsList);
		} catch (UsernameNotLoggedException e) {
			showAlert(StringHardCode.ERRORLOGIN.getString());
			changeScene(Pages.LOGIN);
		}
    	
		var stage = new Stage();
        stage.setTitle("Choose a map");
        var scene = new Scene(controllerChoose.getBox());
        stage.setScene(scene);
        stage.showAndWait();
        if(idMap != -1)
        	setMap();
		
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
    			showAlert(OBJECTNAME);
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
			showAlert(NONAME);
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
    		showAlert(StringHardCode.ERRORSELECTED.getString());
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
    		showAlert(StringHardCode.ERRORSELECTED.getString());
    	}
    	
    	
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
			showAlert(StringHardCode.FILE.getString());
		}
		
		
        
		
    }
    
    @FXML
    void handleSave(ActionEvent event) {
    	if(rdlList.isEmpty())
    		showAlert(HUNTNAME);
    	else {
    		huntBean.setIdHunt(save());
    	}
    }
    
    @FXML
    void handleFinish(ActionEvent event) {
    	if(tfHuntName.getText().equals("") && rdlList.isEmpty()) {
    		showAlert(HUNTNAME);
    	} else {
    		save();
			changeScene(Pages.MAIN_MENU);
    	}
    }
    
    
    
    private void cancelTextView() {
    	for(var i = 0; i < tfComponent.size(); i++)
    		tfComponent.get(i).setText("");
    	
    	cmbObject.setValue("");
    	cmbZone.setValue("");

    }
    
    public void setIdMap(int idMap) {
    	this.idMap = idMap;
    }
    
	
	private int save() {
		List<ObjectBean> objectBean = new ArrayList<>();
		List<RiddleBean> riddleBean = new ArrayList<>();
		
		var manageHuntControl = new ManageHuntControl();
		for(var i = 0; i < rdlList.size(); i++) {
			
			/*var it = rdlList.get(i).lines().iterator();
    		
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
			*/
		}
		huntBean.setHuntName(tfHuntName.getText());
		huntBean.setMap(mapBean);
		huntBean.setRiddle(riddleBean);
		huntBean.setObject(objectBean);
		
		return manageHuntControl.saveHunt(huntBean);
		
	}
	
}
