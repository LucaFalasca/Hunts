package logic.view.desktop.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.MapBean;
import logic.bean.ZoneBean;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.exception.DatabaseException;
import logic.exception.LoadFileFailed;
import logic.parser.Parser;
import logic.state.draw.DrawMachine;
import logic.state.draw.states.MarkerState;
import logic.state.draw.states.OvalState;
import logic.state.draw.states.RectangleState;
import logic.view.desktop.controller.item.ItemZoneGController;

public class ManageMapGController extends ControllerWithLogin{
	
    @FXML
    private ImageView ivMap;
    @FXML
    private Canvas canvasTemp;
    @FXML
    private Canvas canvasDraw;
    @FXML
    private TextField tfMapName;
    @FXML
    private ChoiceBox<String> cbShape;
    
    private static final String RECTANGLE = "Rectangular";
    private static final String OVAL = "Oval";
    
    private ObservableList<String> cbList = FXCollections.observableArrayList(RECTANGLE, OVAL);
    @FXML
    private ListView<ZoneBean> lvZones;
    
    
    
    private GraphicsContext gcDraw;
    private GraphicsContext gcTemp;
    private DrawMachine drawMachine;
    private boolean onDrawing;
    private double startX;
    private double startY;
    private int idMap = -1;
    private ObservableList<ZoneBean> zones = FXCollections.observableArrayList();
    private String pathImage;
    private int idHuntComeback = -1;
	private double canvasWidth;
	private double canvasHeight;
    
    
    @Override
	protected void start(String arg, Object param) {
    	cbShape.setValue(RECTANGLE);
    	cbShape.setItems(cbList);
    	cbShape.setOnAction(e -> {
    		if(cbShape.getValue().equals(OVAL)) {
    			drawMachine.setState(OvalState.getInstance());
    		} else {
    			drawMachine.setState(RectangleState.getInstance());
    		}
    	});
    	lvZones.setItems(zones);
    	lvZones.setCellFactory(p -> new ListCell<ZoneBean>(){
    		@Override
    	    protected void updateItem(ZoneBean item, boolean empty) {
    	        super.updateItem(item, empty);

    	        if (empty || item == null) {
    	           setText(null);
    	           setGraphic(null);
    	        } else {
    	        	var newItem = new ItemZoneGController(Pages.ITEM_ZONE, getIstance());
					newItem.start(StringHardCode.MAP.toString(),item);
					setGraphic(newItem.getBox());
    	        }
    	    }
    	});
    	if(arg.equals(StringHardCode.MAP.toString())) {
			if(param != null) {
				var par = (int) param;
				setMap(par);
				
    		} 
    	} else if(arg.equals(StringHardCode.HUNT.toString()) && param != null){
    		idHuntComeback = (int) param;
		}
	}
    
    private void setMap(int par) {
    	try {
	    	var controller = new ManageMapControl();
			var map = controller.getMapById(getUsername(), par);
			idMap = map.getId();
			if(map.getImage() != null) {
				pathImage = map.getImage();
				setImageByPath(pathImage);
			}
			tfMapName.setText(map.getName());
			if(map.getZones() != null) {
				zones.setAll(map.getZones());
				for(ZoneBean zone : zones) {
					if(zone.getShape().equals(OVAL)) {
						drawMachine.setState(OvalState.getInstance());
					} else {
						drawMachine.setState(RectangleState.getInstance());
					}
					double x1 = Parser.parseFromPercent(zone.getX1(), canvasWidth);
					double x2 = Parser.parseFromPercent(zone.getX2(), canvasWidth);
					double y1 = Parser.parseFromPercent(zone.getY1(), canvasHeight);
					double y2 = Parser.parseFromPercent(zone.getY2(), canvasHeight);
					drawMachine.draw(gcDraw, x1, y1, x2, y2);
				}
			}
    	} catch(DatabaseException e) {
    		showAlert(e.getMessage());
    	}
    }
    
    @FXML
    void initialize() {
        drawMachine = new DrawMachine(RectangleState.getInstance());
        gcDraw = canvasDraw.getGraphicsContext2D();
        gcTemp = canvasTemp.getGraphicsContext2D();
        gcDraw.setFill(Color.web("0xeaed91", 0.5));
        gcTemp.setFill(Color.web("0x61823e", 0.5));
        onDrawing = false;
        
        canvasHeight = canvasDraw.getHeight();
        canvasWidth = canvasDraw.getWidth();
    }
    
    
    @FXML
    void handleUploadFile(ActionEvent event){
    	try {
	    	var fileChooser = new FileChooser();
	    	fileChooser.setTitle("Choose Image");
	    	fileChooser.getExtensionFilters().addAll(
	    			new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")
	    			);
	    	var selectedFile = fileChooser.showOpenDialog(ivMap.getScene().getWindow());
	    	var controller = new ManageMapControl();
	    	
				pathImage = controller.uploadFile(selectedFile);
				setImageByPath(pathImage);
    	
		} catch (LoadFileFailed e) {
			showAlert(e.getMessage());
		} catch(DatabaseException e1) {
    		showAlert(e1.getMessage());
    	}
    	
    	
    }
    
    private void setImageByPath(String path) {
    	var img = new Image("file:" + path, ivMap.getFitWidth(), ivMap.getFitHeight(), false, false);
    	ivMap.setImage(img);
    }
    
    
    @FXML
    void handleMovedOnMap(MouseEvent event) {
		if(event.getButton() == MouseButton.PRIMARY) {
    		drawMachine.clean(gcTemp);
        	if(onDrawing) {
        		double endX = event.getX();
            	double endY = event.getY();
            	
            	drawMachine.draw(gcTemp, startX, startY, endX, endY);   	
        	}
    	}
    	
    }
    

    @FXML
    void handlePressedOnMap(MouseEvent event) {
    	if(event.getButton() == MouseButton.PRIMARY) {
    		startX = event.getX();
        	startY = event.getY();
        	onDrawing = true;
    	}
    	
    }
    

    @FXML
    void handleReleasedOnMap(MouseEvent event) {
    	double endX = event.getX();
    	double endY = event.getY();
    	
    	switch(event.getButton()) {
    	case PRIMARY:
        	if(!thereIsInAZoneRange(startX, startY, endX, endY)) {
        		var zoneName = chooseZoneName();
        		double x1 = Parser.parseToPercent(startX, canvasWidth);
				double y1 = Parser.parseToPercent(startY, canvasHeight);
				double x2 = Parser.parseToPercent(endX, canvasWidth);
				double y2 = Parser.parseToPercent(endY, canvasHeight);
        		var zone = new ZoneBean(zoneName, x1, y1, x2, y2, drawMachine.toString());
    	    	zones.add(zone);
    	    	drawMachine.draw(gcDraw, startX, startY, endX, endY);
        	}
        	
        	drawMachine.clean(gcTemp);
        	onDrawing = false;
    		break;
    	case SECONDARY:
    		if(thereIsInAZoneRange(endX, endY, endX, endY)) {
    			for(ZoneBean zone: zones) {
    				double x1 = Parser.parseFromPercent(zone.getX1(), canvasWidth);
    				double x2 = Parser.parseFromPercent(zone.getX2(), canvasWidth);
    				double y1 = Parser.parseFromPercent(zone.getY1(), canvasHeight);
    				double y2 = Parser.parseFromPercent(zone.getY2(), canvasHeight);
    				if(isBetween(endX, x1, x2) && isBetween(endY, y1, y2)) {
    					remove(zone);
    					break;
    				}
    			}
    		}
    		break;
		default:
    	}
    }
    
    private String chooseZoneName() {
    	String name = null;
    	var td = new TextInputDialog();
    	td.setContentText("Insert zone name");
    	td.setHeaderText("Zone name");
    	td.showAndWait();
    	name = td.getEditor().getText();
    	if(name.equals("")) 
    		name = "Zona " + (zones.size() + 1);
    	for(ZoneBean zone : zones) {
    		if(zone.getNameZone().equals(name)){
    			name = name.concat("(1)");
    		}
    	}
    	return name;
    }
    
    @FXML
    void handleCleanAll(ActionEvent event) {
    	drawMachine.clean(gcDraw);
    	zones.clear();
    }
    
    void clean(ZoneBean zone) {
        var oldState = drawMachine.getCurrentState();    
        
        if(zone.getShape().equals(OVAL)) {
			drawMachine.setState(OvalState.getInstance());
		} else {
			drawMachine.setState(RectangleState.getInstance());
        }
        
        double x1 = Parser.parseFromPercent(zone.getX1(), canvasWidth);
		double x2 = Parser.parseFromPercent(zone.getX2(), canvasWidth);
		double y1 = Parser.parseFromPercent(zone.getY1(), canvasHeight);
		double y2 = Parser.parseFromPercent(zone.getY2(), canvasHeight);
        drawMachine.clean(gcDraw, x1, y1, x2, y2);
        
        drawMachine.setState(oldState);
    }
    
    
    //Save
    @FXML
    void handleSave(ActionEvent event) {
    	try {
	    	var control = new ManageMapControl();
	    	var bean = new MapBean(idMap, getMapName(), zones);
	    	if(pathImage != null) {
	    		bean.setImage(pathImage);
	    	}
			idMap = control.save(getUsername(), bean);
    	} catch(DatabaseException e) {
    		showAlert(e.getMessage());
    	}
    }
    
    @FXML
    void handleSaveExit(ActionEvent event) {
    	if(tfMapName.getText().equals("")) {
    		showAlert("Insert Map Name");
    	} else {
	    	handleSave(event);
	    	
	    	List<Integer> l = new ArrayList<>();
	    	l.add(idHuntComeback);
	    	l.add(idMap);
	    	
	    	if(idHuntComeback != -1) {
				changeScene(Pages.MANAGE_HUNT, StringHardCode.MAP.toString(), l);
	    	}
	    	else {
	    		changeScene(Pages.MAIN_MENU);
	    	}
    	}
    }
    
    //New Zone in Rectangle mode
    @FXML
    void handleRect(ActionEvent event) {
    	drawMachine.setState(RectangleState.getInstance());
    }

    //New Zone in Oval mode
    
    @FXML
    void handleOval(ActionEvent event) {
    	drawMachine.setState(OvalState.getInstance());
    }
    
    //New Marker
    
    @FXML
    void handleAddMarker(ActionEvent event) {
    	drawMachine.setState(MarkerState.getInstance());
    }
    
    
    private String getMapName() {
		return tfMapName.getText();
    }
    
    
    private boolean thereIsInAZoneRange(double x1, double y1, double x2,  double y2) {
    	double rangeX = Math.abs(x2 - x1);
    	double rangeY = Math.abs(y2 - y1);
    	for(ZoneBean zone : zones) {
    		double xz1;
    		double xz2;
    		double yz1;
    		double yz2;
    		
    		xz1 = Parser.parseFromPercent(zone.getX1(), canvasWidth);
    		xz2 = Parser.parseFromPercent(zone.getX2(), canvasWidth);
    		yz1 = Parser.parseFromPercent(zone.getY1(), canvasHeight);
    		yz2 = Parser.parseFromPercent(zone.getY2(), canvasHeight);
    		if(		isBetween(x1, xz1, xz2) && isBetween(y1, yz1, yz2)  || 
    				isBetween(x1, xz1, xz2) && isBetween(y1 + rangeY, yz1, yz2) ||
    				isBetween(x1 + rangeX, xz1, xz2) && isBetween(y1, yz1, yz2) ||
    				isBetween(x1 + rangeX, xz1, xz2) && isBetween(y1 + rangeY, yz1, yz2)) {
    			
    			return true;
    		}
    	}
    	return false;
    }
    
    //Controlla se un numero è compreso tra i due
    private boolean isBetween(double x, double y1, double y2) {
    	if(y2 > y1)	return x < y2 && x > y1;
    	else return x < y1 && x > y2;
    }

	public void remove(ZoneBean zone) {
		clean(zone);
		zones.remove(zone);
		
	}


	
}
