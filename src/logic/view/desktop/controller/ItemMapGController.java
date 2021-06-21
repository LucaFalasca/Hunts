package logic.view.desktop.controller;



import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.MapBean;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;

public class ItemMapGController{
	
    @FXML
    private AnchorPane ancMapPane;

    @FXML
    private Label lbMapName;

    @FXML
    private Button bntModifyMap;

    @FXML
    private Button btnDeleteMap;
    
    private int idMap;
    private String creatorName;
    
    private ControllerWithLogin mainController;

    public ItemMapGController(){
    	var fxmlLoader = new FXMLLoader(getClass().getResource(Pages.ITEM_MAP.getPath()));
        fxmlLoader.setController(this);
        
        
        try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
    
    @FXML
    void handleDeleteMap(ActionEvent event) {
    	var controllerMap = new ManageMapControl();
    	controllerMap.deleteMap(idMap, creatorName);
    }

    @FXML
    void handleModifyMap(ActionEvent event) {
    	mainController.changeScene(Pages.MANAGE_MAP, "map", idMap);
    }

	public void setInfo(MapBean itemBean, ControllerWithLogin mainController) {
		lbMapName.setText(itemBean.getName());
		idMap = itemBean.getId();	
		creatorName = itemBean.getCreatorName();
		this.mainController = mainController;
	}

	public AnchorPane getBox() {
		return ancMapPane;
	}
	
}
