package logic.view.desktop.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.MapBean;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.view.desktop.controller.item.ItemController;

public class ChooseMapGController extends ItemController{
	
    @FXML
    private AnchorPane ancChooseMap;
    private ObservableList<String> mapList = FXCollections.observableArrayList();
    private List<MapBean> maps = new ArrayList<>();
    private ManageHuntGController controller;
    
    @FXML
    private ListView<String> lvMap;

    @FXML
    private Button btnChooseAMap;
    
    protected ChooseMapGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		controller = (ManageHuntGController) mainController;
	}
	
    @FXML
    void handleReturnName(ActionEvent event) {
    	int index = lvMap.getSelectionModel().getSelectedIndex();
    	
    	if(index != -1) {
    		controller.setIdMap(maps.get(index).getId());
    		var stage = (Stage) ancChooseMap.getScene().getWindow();
    		stage.close();
    	} else {
    		var alert = new Alert(AlertType.ERROR);
    		alert.setContentText(StringHardCode.ERRORSELECTED.getString());
    		alert.showAndWait();
    	}

    }

	@Override
	public void setInfo(Object itemBean) {
		List<?> mapsList = (List<?>)itemBean;
		for(Object map : mapsList) {
    		this.mapList.add(((MapBean)map).getName());
    		this.maps.add((MapBean)map);
    	}
		lvMap.setItems(this.mapList);
	}

	@Override
	public Parent getBox() {
		return ancChooseMap;
	}


    
}
