package logic.view.desktop.controller;

import java.io.File;
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
import logic.bean.UploadFileBean;
import logic.bean.ZoneBean;
import logic.control.ManageMapControl;
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
    

    private TextField tfNameMap;
    private GraphicsContext gcDraw, gcTemp;
    private DrawMachine drawMachine;
    private boolean onDrawing;
    private double startX, startY;
    private int idMap = -1;
    private List<ZoneBean> zones;
    
    @FXML
    void initialize() {
        drawMachine = new DrawMachine(RectangleState.getInstance());
        gcDraw = canvasDraw.getGraphicsContext2D();
        gcTemp = canvasTemp.getGraphicsContext2D();
        gcDraw.setFill(Color.web("0xeaed91", 0.5));
        gcTemp.setFill(Color.web("0x61823e", 0.5));
        onDrawing = false;
        zones = new ArrayList<ZoneBean>();
    }
    
    
    @FXML
    void handleUploadFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Choose Image");
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")/*,
    			new ExtensionFilter("All", "*.*")*/
    			);
    	File selectedFile = fileChooser.showOpenDialog(ivMap.getScene().getWindow());
    	String pathFile = selectedFile.getPath();
    	
    	Image img = new Image("file:" + pathFile, ivMap.getFitWidth(), ivMap.getFitHeight(), false, false);
    	ivMap.setImage(img);
    	
    	ManageMapControl controller = new ManageMapControl();
    	UploadFileBean bean = new UploadFileBean();
    	bean.setFile(selectedFile);
    	controller.uploadFile(bean);
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
    	ManageMapControl control = new ManageMapControl();
    	
    	MapBean bean = new MapBean(idMap, getMapName(), zones);
    	if(getImage() != null) {
    		bean.setImage(getImage());
    	}
    	control.save(bean);
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
		return tfNameMap.getText();
    }
    
    
    private Image getImage() {
		return ivMap.getImage();
    	
    }
    
    
    private boolean thereIsInAZoneRange(double x1, double y1, double x2,  double y2) {
    	double rangeX = Math.abs(x2 - x1);
    	double rangeY = Math.abs(y2 - y1);
    	for(ZoneBean zone : zones) {
    		double xz1, xz2, yz1, yz2;
    		xz1 = zone.getStartX();
    		xz2 = zone.getEndX();
    		yz1 = zone.getStartY();
    		yz2 = zone.getEndY();
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


	@Override
	void start() {
		this.ivMap.requestFocus();
		
	}
}
