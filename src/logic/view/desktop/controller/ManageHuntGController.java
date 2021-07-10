package logic.view.desktop.controller;


import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
import logic.parser.Parser;
import logic.state.draw.DrawMachine;
import logic.state.draw.states.OvalState;
import logic.state.draw.states.RectangleState;
import logic.view.desktop.controller.item.ItemObjectGController;
import logic.view.desktop.controller.item.ItemRiddleG;

public class ManageHuntGController extends ControllerWithLogin{
	@FXML
    private Label lbRiddle;
	
	@FXML
	private Label lbMap;

	@FXML
	private Label lbObjectImg;
	
    @FXML
    private ImageView ivObject;
	
	@FXML
	private Label lbObjectPath; 
    @FXML
    private TextField tfHuntName;

    @FXML
    private ComboBox<ObjectBean> cmbObject;
    
    @FXML
    private TextField tfRiddleSolution;

    @FXML
    private TextField tfRiddleText;

    @FXML
    private Button btnChooseMap;

    @FXML
    private Button btnUploadFile;

    @FXML
    private TableView<ObjectBean> tbObject;

    @FXML
    private TableColumn<ObjectBean, String> cmName;

    @FXML
    private TableColumn<ObjectBean, String> cmDescription;

    @FXML
    private TableColumn<ObjectBean, ObjectBean> cmObjButtons;

    private ObservableList<ObjectBean> objList = FXCollections.observableArrayList();
    
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
	
    @FXML
    private TableView<RiddleBean> tbRiddle;
    
    private ObservableList<RiddleBean> rdlList = FXCollections.observableArrayList();
    
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
	
	private String filePath = null;
	private int idObject = 0;
	private int idRiddle = 0;

	private static final String NEWRIDDLE = "Add new Riddle";
	private static final String NOWROTE = "Riddle text or solution area's are empty";
	private static final String NONAME = "Object name area's is empty";
	private static final String HUNTNAME = "You must insert the name of the Hunt or at least one riddle to Save";
	private static final String OBJECTNAME = "An object with this name already exist";	
	private static final String RIDDLE = "Riddle ";
	
	
	
	@Override
	protected void start(String arg, Object param) {
		drawMachine = new DrawMachine();
		gcDraw = canvas.getGraphicsContext2D();
        gcDraw.setFill(Color.web("0xeaed91", 0.5));
		
		var manageHuntControl = new ManageHuntControl();
		var idHunt = -1;
		var idMap = -1;
		MapBean mapBean = null;
		try {
			switch(arg) {
				case "map":
					List<?> ids = (List<?>) param;
					idHunt = (Integer) ids.get(0);
					idMap = (Integer) ids.get(1);
					huntBean = manageHuntControl.getHunt(idHunt, getUsername());
					mapBean = new ManageMapControl().getMapById(getUsername(), idMap);
					huntBean.setMap(mapBean);
					setHunt(huntBean);
					break;
				case "hunt":
					idHunt = (int) param;
					huntBean = manageHuntControl.getHunt(idHunt, getUsername());
					setHunt(huntBean);
					break;
				default:
					huntBean.setUsername(getUsername());
					huntBean.setIdHunt(idHunt);
					break;
					
			}
			
		} catch (UsernameNotLoggedException e) {
			showAlert(e.getMessage());
			changeScene(Pages.LOGIN);
		}

		lbRiddle.setText(RIDDLE + rdlList.size());
		lbMap.setVisible(false);
    	cmbObject.setItems(objList);
    	cmbZone.setItems(zoneList);   	
    	tbRiddle.setItems(rdlList);
    	tbObject.setItems(objList);
    	
    	setTable();
    	
	}
	
	private void setTable() {
		for(TableColumn<RiddleBean, ?> rb : tbRiddle.getColumns()) {
			rb.prefWidthProperty().bind(tbRiddle.widthProperty().multiply(0.125));
		}
	
		
		cmText.setCellValueFactory(new PropertyValueFactory<>("riddle"));
		cmSolution.setCellValueFactory(new PropertyValueFactory<>("solution"));
		cmClue1.setCellValueFactory(new PropertyValueFactory<>("clue1"));
		cmClue2.setCellValueFactory(new PropertyValueFactory<>("clue2"));
		cmClue3.setCellValueFactory(new PropertyValueFactory<>("clue3"));
		cmObject.setCellValueFactory(new PropertyValueFactory<>("objectName"));
		cmZone.setCellValueFactory(new PropertyValueFactory<>("zoneName"));
		
		for(TableColumn<ObjectBean, ?> ob : tbObject.getColumns()) {
			ob.prefWidthProperty().bind(tbObject.widthProperty().multiply(0.332));
			
		}
		
		cmName.setCellValueFactory(new PropertyValueFactory<>("name"));
		cmDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		cmButtons.setCellValueFactory(paramRiddle -> new ReadOnlyObjectWrapper<>(paramRiddle.getValue()));
		cmButtons.setCellFactory(paramRiddle -> new TableCell<RiddleBean, RiddleBean>(){
			@Override
			protected void updateItem(RiddleBean riddleBean, boolean empty) {
				super.updateItem(riddleBean, empty);
				if(riddleBean != null) {
					var gController = new ItemRiddleG(Pages.ITEM_RIDDLE, getIstance());
			    	gController.start(StringHardCode.RIDDLE.toString(),riddleBean);
			    	setGraphic(gController.getBox());
				} else {
					setGraphic(null);
					setText(null);
				}
			}
		});
		
		cmObjButtons.setCellValueFactory(paramObject -> new ReadOnlyObjectWrapper<>(paramObject.getValue()));
		cmObjButtons.setCellFactory(paramObject -> new TableCell<ObjectBean, ObjectBean>(){
			@Override
			protected void updateItem(ObjectBean objectBean, boolean empty) {
				super.updateItem(objectBean, empty);
				if(objectBean != null) {
					var gController = new ItemObjectGController(Pages.ITEM_OBJECT, getIstance());
			    	gController.start(StringHardCode.OBJECT.toString(), objectBean);
			    	setGraphic(gController.getBox());
				} else {
					setGraphic(null);
					setText(null);
				}
			}
		});
		
		
	}
	
	private void setHunt(HuntBean huntBean) {
		
		List<ObjectBean> objectBean;
		List<RiddleBean> riddleBean;
		tfHuntName.setText(huntBean.getHuntName());
		riddleBean = huntBean.getRiddle();
		rdlList.setAll(riddleBean);
		
		
		objectBean = huntBean.getObject();
		objList.setAll(objectBean);
		
		
		if(huntBean.getMap() != null)
			setMap(huntBean.getMap());
		
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
    		
    		var zoneName = cmbZone.getSelectionModel().getSelectedItem();
    		
    		var temp = new RiddleBean(idRiddle, nome, solution, clue1, clue2, clue3, zoneName);
    		
    		if(objName != null) {
    			temp.setObjectName(objName.getName());
    		} else {
    			temp.setObjectName(null);
    		}
    		
    		idRiddle++;
    		
    		rdlList.add(temp);
    		
    		lbRiddle.setText(RIDDLE + (rdlList.size()));
    		
    		btnAddRiddle.setText(NEWRIDDLE);
    		
    		cancelTextView();
    		
    		
    	} else {
    		showAlert(NOWROTE);
    	}
    }
    
    public void removeRiddle(int index) {
    	for(RiddleBean rb : rdlList) {
    		if(index == rb.getNumRiddle()) {
    			rdlList.remove(rb);
    			break;
    		}
    	}
    }
    
    public void modifyRiddle(int index) {
    	for(RiddleBean rb : rdlList) {
    		if(index == rb.getNumRiddle()) {
    			lbRiddle.setText(RIDDLE + rb.getNumRiddle());
    	    	tfRiddleText.setText(rb.getRiddle());
    	    	tfRiddleSolution.setText(rb.getSolution());
    	    	tfClueText1.setText(rb.getClue1());
    	    	tfClueText2.setText(rb.getClue2());
    	    	tfClueText3.setText(rb.getClue3());
    	    	
    	    	var objName = rb.getObjectName();
    	    	
    	    	for(ObjectBean objBean : objList) {
    	    		if(objBean.getName().equals(objName))
    	    			cmbObject.setValue(objBean);
    	    	}
    	    	
    	    	var zoneName = rb.getZoneName();
    	    	
    	    	if(zoneName != null) {
    	    		cmbZone.setValue(zoneName);
    	    	}
    	    	
    	    	rdlList.remove(rb);
    	    	break;
    		}
    	}
    }
    
    
    
    @FXML
    void handleCreateMap(ActionEvent event) {
    	huntBean.setIdHunt(save());
		changeScene(Pages.MANAGE_MAP, StringHardCode.HUNT.toString(), huntBean.getIdHunt());
    	
    }

    @FXML
    void handleChooseMap(ActionEvent event)  {
    	
    	var mpc = new ManageMapControl();
		var controller = new ChooseMapGController(Pages.CHOOSE_MAP, getIstance());
		List<MapBean> mapsList = null;
    	
    	try {
			mapsList = mpc.getAllMaps(getUsername());
		} catch (UsernameNotLoggedException e) {
			showAlert(e.getMessage());
			changeScene(Pages.LOGIN);
		}
    	
    	createStage(controller, mapsList, "Choose a map");
        
    }

    
    
    private void setMap(MapBean map) {
    	imgMap.setImage(null);
    	huntBean.setMap(map);
    	if(map.getZones() != null) {
	    	for(ZoneBean zb : map.getZones())
	    		zoneList.add(zb.getNameZone());
    	}
    	lbMap.setVisible(true);
    	lbMap.setText(map.getName());
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
				double x1 = Parser.parseFromPercent(zone.getX1(), canvas.getWidth());
				double x2 = Parser.parseFromPercent(zone.getX2(), canvas.getWidth());
				double y1 = Parser.parseFromPercent(zone.getY1(), canvas.getHeight());
				double y2 = Parser.parseFromPercent(zone.getY2(), canvas.getHeight());
				drawMachine.draw(gcDraw, x1, y1, x2, y2);
			}
		}
    }
    
    public void setIdMap(int idMap) {
    	var mpc = new ManageMapControl();
    	MapBean mapBean = null;
    	if(idMap != -1) {
			try {
				mapBean = mpc.getMapById(getUsername(), idMap);
				if(mapBean != null) {
					huntBean.setMap(mapBean);
					setMap(mapBean);
				}
			} catch (UsernameNotLoggedException e) {
				showAlert(e.getMessage());
				changeScene(Pages.LOGIN);
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
    			var ob = new ObjectBean(idObject, objName, objDescription, filePath);
    			idObject++;
    			objList.add(ob);
    			
    			if(filePath != null) {
    				filePath = null;
    			}
    			lbObjectImg.setVisible(false);
    			ivObject.setImage(null);
    			ivObject.setVisible(false);
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
			if(objName.equals(objBean.getName())) {
				flag = true;
			}
		}
		return flag;
    }

    public void removeObject(int index) {
    	String objName = null;
    	for(ObjectBean ob : objList) {
    		if(ob.getIdObject() == index) {
    			objName = ob.getName();
    			objList.remove(ob);
    			break;
    		}
    	}
    	findObjectInRiddle(objName);
    }
    
    public void modifyObject(int index) {
    	String objName = null;
    	for(ObjectBean ob : objList) {
    		if(ob.getIdObject() == index) {
		    	tfObjectName.setText(ob.getName());
		    	txtDescription.setText(ob.getDescription());
		    	objName = ob.getName();
		    	objList.remove(ob);
		    	if(ob.getPath() != null) {
		    		filePath = ob.getPath();
		    		lbObjectImg.setVisible(true);
					ivObject.setVisible(true);
					ivObject.setImage(new Image("File:" + filePath, ivObject.getFitWidth(), ivObject.getFitHeight(), false, false));
		    	}
		    	break;
    		}
    	}
    	findObjectInRiddle(objName);
    }
    
    private void findObjectInRiddle(String objName) {
    	for(RiddleBean rb : rdlList) {
    		if(rb.getObjectName().equals(objName)) {
    			rdlList.remove(rb);
    			break;
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
			lbObjectImg.setVisible(true);
			ivObject.setVisible(true);
			ivObject.setImage(new Image("File:" + filePath, ivObject.getFitWidth(), ivObject.getFitHeight(), false, false));
		} catch (LoadFileFailed e) {
			showAlert(e.getMessage());
		}
		
    }
    
    @FXML
    void handleSave(ActionEvent event) {
    	if(rdlList.isEmpty())
    		showAlert(HUNTNAME);
    	else {
    		huntBean.setIdHunt(save());
    		huntBean.setPrivate(true);
    	}
    }

    @FXML
    void handleSaveExit(ActionEvent event) {
    	saveAndExit(true);
    		
    }

    @FXML
    void handleSavePublish(ActionEvent event) {
    	saveAndExit(false);
    }
    
    private void saveAndExit(boolean visible) {
    	if(tfHuntName.getText().equals("") || rdlList.isEmpty()) {
    		showAlert(HUNTNAME);
    	} else {
    		huntBean.setPrivate(visible);
    		save();
			changeScene(Pages.MAIN_MENU, null, null);
    	}
    }

	private int save() {
		List<ObjectBean> objectBean = new ArrayList<>();
		List<RiddleBean> riddleBean = new ArrayList<>();
		
		var manageHuntControl = new ManageHuntControl();
		
		huntBean.setHuntName(tfHuntName.getText());
		for(RiddleBean rb : rdlList) 
			riddleBean.add(rb);
			
		for(ObjectBean ob : objList)
			objectBean.add(ob);
		
		huntBean.setRiddle(riddleBean);
		huntBean.setObject(objectBean);
		
		
		return manageHuntControl.saveHunt(huntBean);
		
	}
    
	private void cancelTextView() {

		tfRiddleText.setText("");
		tfRiddleSolution.setText("");
		tfClueText1.setText("");
		tfClueText2.setText("");
		tfClueText3.setText("");
    	
    	cmbObject.setValue(null);
       	cmbZone.setValue("");

    }
	
	
    
	
}
