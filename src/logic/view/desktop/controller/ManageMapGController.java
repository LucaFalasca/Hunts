package logic.view.desktop.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.MapBean;
import logic.bean.ZoneBean;
import logic.control.ManageMapControl;
import logic.exception.UsernameNotLoggedException;
import logic.state.draw.DrawMachine;
import logic.state.draw.states.MarkerState;
import logic.state.draw.states.OvalState;
import logic.state.draw.states.RectangleState;

public class ManageMapGController extends ControllerWithLogin{
	
    @FXML
    private ImageView ivMap;
    @FXML
    private Canvas canvasTemp;
    @FXML
    private Canvas canvasDraw;
    @FXML
    private TextField tfMapName;
    
    private GraphicsContext gcDraw;
    private GraphicsContext gcTemp;
    private DrawMachine drawMachine;
    private boolean onDrawing;
    private double startX;
    private double startY;
    private int idMap = -1;
    private List<ZoneBean> zones;
    private String pathImage;
    
    @Override
	void start(String arg, Object param) {
		if(param != null) {
			var par = (int) param;
			var controller = new ManageMapControl();
			try {
				var map = controller.getMapById(getUsername(), par);
				idMap = map.getId();
				if(map.getImage() != null) {
					setImageByPath(map.getImage());
				}
				tfMapName.setText(map.getName());
				if(map.getZones() != null) {
					zones = map.getZones();
					for(ZoneBean zone : zones) {
						switch(zone.getShape()) {
							case "RECTANGLE": drawMachine.setState(RectangleState.getInstance());
								break;
							case "OVAL" : drawMachine.setState(OvalState.getInstance());
								break;
							default:
								drawMachine.setState(RectangleState.getInstance());
						}
						
						drawMachine.draw(gcDraw, zone.getX1(), zone.getY1(), zone.getX2(), zone.getY2());
					}
				}
				
			} catch (UsernameNotLoggedException e) {
				e.printStackTrace();
			}
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
        zones = new ArrayList<>();
    }
    
    
    @FXML
    void handleUploadFile(ActionEvent event) {
    	var fileChooser = new FileChooser();
    	fileChooser.setTitle("Choose Image");
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")/*,
    			new ExtensionFilter("All", "*.*")*/
    			);
    	var selectedFile = fileChooser.showOpenDialog(ivMap.getScene().getWindow());
    	var controller = new ManageMapControl();
    	pathImage = controller.uploadFile(selectedFile);
    	
    	setImageByPath(pathImage);
    }
    
    private void setImageByPath(String path) {
    	var img = new Image("file:" + path, ivMap.getFitWidth(), ivMap.getFitHeight(), false, false);
    	ivMap.setImage(img);
    }
    
    
    @FXML
    void handleMovedOnMap(MouseEvent event) {
    	drawMachine.clean(gcTemp);
    	if(onDrawing) {
    		double endX = event.getX();
        	double endY = event.getY();
        	
        	drawMachine.draw(gcTemp, startX, startY, endX, endY);
        	
    	}
    }
    

    @FXML
    void handlePressedOnMap(MouseEvent event) {
    	startX = event.getX();
    	startY = event.getY();
    	onDrawing = true;
    }
    

    @FXML
    void handleReleasedOnMap(MouseEvent event) {
    	double endX = event.getX();
    	double endY = event.getY();
    	
    	if(!thereIsInAZoneRange(startX, startY, endX, endY)) {
	    	drawMachine.draw(gcDraw, startX, startY, endX, endY);
	    	zones.add(new ZoneBean("TestNome", startX, startY, endX, endY, drawMachine.toString()));
    	}
    	
    	drawMachine.clean(gcTemp);
    	onDrawing = false;
    }

    
    @FXML
    void handleCleanAll(ActionEvent event) {
    	drawMachine.clean(gcDraw);
    	zones.clear();
    }
    
    
    //Save
    @FXML
    void handleSave(ActionEvent event) {
    	var control = new ManageMapControl();
    	var bean = new MapBean(idMap, getMapName(), zones);
    	if(pathImage != null) {
    		bean.setImage(pathImage);
    	}
    	try {
			idMap = control.save(getUsername(), bean);
		} catch (UsernameNotLoggedException e) {
			e.printStackTrace();
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
    		
    		xz1 = zone.getX1();
    		xz2 = zone.getX2();
    		yz1 = zone.getY1();
    		yz2 = zone.getY2();
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


	
}
