package logic.view.desktop.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import logic.bean.MapBean;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.exception.UsernameNotLoggedException;

public class ChooseMapGController extends ControllerWithLogin{
	
	private ObservableList<String> mapList = FXCollections.observableArrayList();
    
    @FXML
    private ListView<String> lvMap;

    @FXML
    private Button btnChooseAMap;
    
    Alert alert = new Alert(AlertType.ERROR, "Error", ButtonType.CLOSE);
    

	@Override
	void start(String arg, Object param) {
		List<MapBean> mapsList = null;
    	
    	var mpc = new ManageMapControl();
    	
    	try {
			mapsList = mpc.getAllMaps(getUsername());
			for(var i = 0; i < mapList.size(); i++) {
	    		this.mapList.add(mapsList.get(i).getName());
	    	}
		} catch (UsernameNotLoggedException e) {
			showAlert(StringHardCode.ERRORLOGIN.getString());
			changeScene(Pages.LOGIN);
			
		}
    	
    	
    	lvMap.setItems(this.mapList);
		
	}
	
    @FXML
    void handleReturnName(ActionEvent event) {
    	var manageHunt = new ManageHuntGController();
    	
    	int index = lvMap.getSelectionModel().getSelectedIndex();
    	
    	if(index != -1) {
    		manageHunt.setIdMap(index);
    		var stage = (Stage) btnChooseAMap.getScene().getWindow();
    		stage.close();
    	} else {
    		showAlert(StringHardCode.ERRORSELECTED.getString());
    	}

    }


    
}
