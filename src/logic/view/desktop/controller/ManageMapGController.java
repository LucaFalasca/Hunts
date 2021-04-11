package logic.view.desktop.controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import logic.bean.MapBean;
import logic.bean.UploadFileBean;
import logic.control.ManageMapControl;
import logic.state.draw.DrawMachine;
import logic.state.draw.states.MarkerState;
import logic.state.draw.states.OvalState;
import logic.state.draw.states.RectangleState;


public class ManageMapGController {
	
    @FXML
    private ImageView ivMap;

    @FXML
    private Button btnUploadFile;

    @FXML
    private Button btnAddMarker;
    
    @FXML
    private Button btnOval;

    @FXML
    private Button btnRect;

    @FXML
    private Button btnCleanAll;

    @FXML
    private Button btnSave;
    
    @FXML
    private Canvas canvasTemp;

    @FXML
    private Canvas canvasDraw;
    
    private GraphicsContext gcDraw;
    
    private GraphicsContext gcTemp;
    
    private boolean onDrawing;
    
    private DrawMachine drawMachine;
    
    private double startX;
    
    private double startY;
    
    private int idMap = -1;
    
    
    
    @FXML
    void initialize() {
        drawMachine = new DrawMachine(RectangleState.getInstance());
        gcDraw = canvasDraw.getGraphicsContext2D();
        gcTemp = canvasTemp.getGraphicsContext2D();
        gcDraw.setFill(Color.web("0xeaed91", 0.5));
        gcTemp.setFill(Color.web("0x61823e", 0.5));
        onDrawing = false;
    }
    
    @FXML
    void handleUploadFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Choose Image");
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Image", "*.png", "*.jpg", "*.jpeg", "*.gif")/*,
    			new ExtensionFilter("All", "*.*")*/
    			);
    	File selectedFile = fileChooser.showOpenDialog(btnUploadFile.getScene().getWindow());
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
    		System.out.println("moved");
    		double endX = event.getX();
        	double endY = event.getY();
        	
        	drawMachine.draw(gcTemp, startX, startY, endX, endY);
    	}
    }

    @FXML
    void handlePressedOnMap(MouseEvent event) {
    	System.out.println("pressed");
    	startX = event.getX();
    	startY = event.getY();
    	onDrawing = true;
    }

    @FXML
    void handleReleasedOnMap(MouseEvent event) {
    	System.out.println("released");
    	double endX = event.getX();
    	double endY = event.getY();
    	
    	drawMachine.draw(gcDraw, startX, startY, endX, endY);
    	drawMachine.clean(gcTemp);
    	onDrawing = false;
    }
    
    @FXML
    void handleCleanAll(ActionEvent event) {
    	drawMachine.clean(gcDraw);
    }
    @FXML
    void handleSave(ActionEvent event) {
    	ManageMapControl control = new ManageMapControl();
    	
    	if(idMap == -1) {
    		idMap = control.requestId();
    	}
    	MapBean bean = new MapBean(idMap, getMapName(), canvasDraw);
    	if(getImage() != null) {
    		bean.setImage(getImage());
    	}
    	control.save(bean);
    }
    
    @FXML
    void handleRect(ActionEvent event) {
    	drawMachine.setState(RectangleState.getInstance());
    }

    @FXML
    void handleOval(ActionEvent event) {
    	drawMachine.setState(OvalState.getInstance());
    }
    

    @FXML
    void handleAddMarker(ActionEvent event) {
    	drawMachine.setState(MarkerState.getInstance());
    }
    
    private String getMapName() {
		return "example_map_name";
    	
    }
    
    private Image getImage() {
		return ivMap.getImage();
    	
    }
}
