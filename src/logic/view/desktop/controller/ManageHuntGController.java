package logic.view.desktop.controller;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.ObjectBean;
import logic.bean.RiddleBean;
import logic.bean.ZoneBean;
import logic.control.ManageHuntControl;
import logic.control.ManageMapControl;
import logic.control.UploadFileControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.exception.LoadFileFailed;
import logic.exception.UsernameNotLoggedException;
import logic.state.draw.DrawMachine;
import logic.state.draw.states.OvalState;
import logic.state.draw.states.RectangleState;
import logic.view.desktop.controller.item.ItemObjectGController;
import logic.view.desktop.controller.item.ItemRiddleG;

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
    private ListView<ObjectBean> lvObject;

    private ObservableList<ObjectBean> objList = FXCollections.observableArrayList();
    
    private ObservableList<String> cmbObjList = FXCollections.observableArrayList();
    
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
    
    @FXML
    private Canvas canvas;

    private GraphicsContext gcDraw;
    private DrawMachine drawMachine;
	
    
	private HuntBean huntBean = new HuntBean();
	private MapBean mapBean = new MapBean();
	
	private int idMap = -1;
	private String filePath = null;
	private int idObject = 0;
	private int idRiddle = 0;

	private static final String NEWRIDDLE = "Add new Riddle";
	private static final String NOWROTE = "Riddle text or solution area's are empty";
	private static final String NONAME = "Object name area's is empty";
	private static final String HUNTNAME = "You must insert the name of the Hunt or at least one riddle to Save";
	private static final String OBJECTNAME = "An object with this name already exist";	private static final String RIDDLE = "Riddle ";
	
	
	
	@Override
	void start(String arg, Object param) {
		drawMachine = new DrawMachine();
		gcDraw = canvas.getGraphicsContext2D();
        gcDraw.setFill(Color.web("0xeaed91", 0.5));
		
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
				
				huntBean = manageHuntControl.getHunt(id, getUsername());
				
				setHunt(huntBean);
				setMap(huntBean.getMap());
			} else {
				if(arg.equals(StringHardCode.MAP.getString())) {
					List<?> ids = (List<?>) param;
					id = (Integer) ids.get(0);
					idMap = (Integer) ids.get(1);
					MapBean mapBean = new ManageMapControl().getMapById(getUsername(), idMap);
					huntBean = manageHuntControl.getHunt(id, getUsername());
					huntBean.setMap(mapBean);
					setMap(mapBean);
					setHunt(huntBean);
				}
			}
		} catch (UsernameNotLoggedException e) {
			showAlert(StringHardCode.ERRORLOGIN.getString());
			changeScene(Pages.LOGIN);
		}
		huntBean.setIdHunt(id);
		setIdMap(id);
		
		setTable();
		
		lbRiddle.setText(RIDDLE + rdlList.size());
    	lvObject.setItems(objList);
    	cmbObject.setItems(cmbObjList);
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
		rdlList.setAll(riddleBean);
		objectBean = huntBean.getObject();
		objList.setAll(objectBean);
		
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
    		
    		var temp = new RiddleBean(idRiddle,
    								  nome, solution, clue1, clue2, clue3,
    								  objName, zoneName);
    		
    		idRiddle++;
    		
    		cmButtons.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
    		cmButtons.setCellFactory(param -> new TableCell<RiddleBean, RiddleBean>(){
    			@Override
    			protected void updateItem(RiddleBean riddleBean, boolean empty) {
    				super.updateItem(riddleBean, empty);
    				if(riddleBean != null) {
    					var gController = new ItemRiddleG(Pages.ITEM_RIDDLE, getIstance());
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
    	for(var i = 0; i < rdlList.size(); i++) {
    		if(index == rdlList.get(i).getNumRiddle()) {
    			rdlList.remove(i);
    			break;
    		}
    	}
    }
    
    public void modifyRiddle(int index) {
    	RiddleBean rb = null;
    	var i = 0;
    	for(i = 0; i < rdlList.size(); i++) {
    		if(index == rdlList.get(i).getNumRiddle()) {
    			rb = rdlList.get(i);
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
    	    	
    	    	rdlList.remove(i);
    		}
    	}
    }
    
    
    
    @FXML
    void handleCreateMap(ActionEvent event) {
    	huntBean.setIdHunt(save());
		changeScene(Pages.MANAGE_MAP, StringHardCode.HUNT.getString(), huntBean.getIdHunt());
    	
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
			try {
				setMap(mpc.getMapById(getUsername(), idMap));

			} catch (UsernameNotLoggedException e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    
    
    private void setMap(MapBean map) {
    	huntBean.setMap(map);
		if(map.getImage() != null) {
			var img = new Image("file:" + map.getImage(), imgMap.getFitWidth(), imgMap.getFitHeight(), false, false);
			imgMap.setImage(img);
		}
		if(map.getZones() != null) {
			for(ZoneBean zone : map.getZones()) {
				if(zone.getShape().equals("Oval")) {
					drawMachine.setState(OvalState.getInstance());
				} else {
					drawMachine.setState(RectangleState.getInstance());
				}
				drawMachine.draw(gcDraw, zone.getX1(), zone.getY1(), zone.getX2(), zone.getY2());
			}
		}
    }

    @FXML
    void handleAddObject(ActionEvent event) {
    	
    	var objName = tfObjectName.getText();
    		
		if(!(objName.equals(""))) {
			

    		if(isThere(objName)) {
    			showAlert(OBJECTNAME);
    		}else {
    			
    			var objDescription = txtDescription.getText();
    			cmbObjList.add(objName);
    			var ob = new ObjectBean(idObject, objName, objDescription, filePath);
    			idObject++;
    			objList.add(ob);
    			
    			lvObject.setCellFactory(obj -> new ListCell<ObjectBean>() {
					@Override
					public void updateItem(ObjectBean itemBean, boolean empty) {
						super.updateItem(itemBean, empty);
						if(itemBean != null) {
							var newItem = new ItemObjectGController(Pages.ITEM_OBJECT, getIstance());
							newItem.setInfo(itemBean);
							setGraphic(newItem.getBox());
							
						}
					}
				});
    			
    			if(filePath != null) {
    				filePath = null;
    				btnUploadFile.setText("Upload File");
    			}
    		}
    		tfObjectName.setText("");
    		txtDescription.setText("");
		} else {
			showAlert(NONAME);
    	}
    }
    
    private boolean isThere(String objName) {
    	var flag = false;
		for(ObjectBean objBean: objList){
			if(objName.equals(objBean.getObject())) {
				flag = true;
			}
		}
		return flag;
    }

    public void removeObject(int index) {
    	String objName = null;
    	for(var i = 0; i < objList.size(); i++) {
    		if(objList.get(i).getIdObject() == index) {
    			objName = objList.get(i).getObject();
    			objList.remove(i);
    			break;
    		}
    	}
    	if(objName != null) {
			for(var i = 0; i < cmbObjList.size(); i++) {
				if(objName.equals(cmbObjList.get(i))) {
					cmbObjList.remove(i);
				}
			}
    	}
    }
    
    public void modifyObject(int index) {
    	ObjectBean obj = null;
    	String objName = null;
    	for(var i = 0; i < objList.size(); i++) {
   
    		if(objList.get(i).getIdObject() == index) {
    			objName = objList.get(i).getObject();
    			obj = objList.get(i);
		    	tfObjectName.setText(obj.getObject());
		    	txtDescription.setText(obj.getDescription());
		    	if(obj.getPath() != null) {
		    		btnUploadFile.setText("Change File");
		    	}
		    	
		    	objList.remove(i);
		    	break;
    		}
    	}
    	if(objName != null) {
			for(var i = 0; i < cmbObjList.size(); i++) {
				if(objName.equals(cmbObjList.get(i))) {
					cmbObjList.remove(i);
				}
			}
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
    	mapBean.setId(idMap);
    }
    
	
	private int save() {
		List<ObjectBean> objectBean = new ArrayList<>();
		List<RiddleBean> riddleBean = new ArrayList<>();
		
		var manageHuntControl = new ManageHuntControl();
		
		huntBean.setHuntName(tfHuntName.getText());
		for(var i = 0; i < rdlList.size(); i++) 
			riddleBean.add(rdlList.get(i));
			
			
		for(var i = 0; i < objList.size(); i++)
			objectBean.add(objList.get(i));
		
		huntBean.setRiddle(riddleBean);
		huntBean.setObject(objectBean);
		huntBean.setMap(mapBean);
		
		
		return manageHuntControl.saveHunt(huntBean);
		
	}
	
}
