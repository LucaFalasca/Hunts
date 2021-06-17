package logic.view.desktop.controller;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import logic.bean.MapBean;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;
import logic.exception.UsernameNotLoggedException;

public class MainMenuGController extends ControllerWithLogin{

    @FXML
    private Button btnHuntCreate;

    @FXML
    private Button btnCreateMap;
    
    @FXML
    private Label lbUsername;
    
    @FXML
    private ToolBar btnHome;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private ListView<AnchorPane> lvMaps;
    private ObservableList<AnchorPane> mapsList = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane apHunts;
    
    @FXML
    private AnchorPane apMaps;
    
    @Override
	void start(String arg, Object param) {
		if(isLogged()) {
			try {
				apMaps.setDisable(false);
				lvMaps.setItems(mapsList);
				
				var controllerMaps = new ManageMapControl();
				List<MapBean> mapBeans = controllerMaps.getAllMaps(getUsername());
				
				for(MapBean mapBean : mapBeans) {
					Pane pane = null;
					pane = FXMLLoader.load(getClass().getResource("/logic/view/desktop/layout/ItemMapList.fxml"));
					
					Label label = (Label) pane.getChildren().get(0);
					Button buttonEdit = (Button) pane.getChildren().get(1);
					buttonEdit.setOnAction(e -> editMapButton(mapBean.getId()));
					
					Button buttonDelete = (Button) pane.getChildren().get(2);
					buttonDelete.setOnAction(e -> deleteMapButton(mapBean.getId()));
					
					label.setText(mapBean.getName());
					mapsList.add((AnchorPane) pane);
				}
				
				
			} catch (UsernameNotLoggedException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
    
    private void editMapButton(int id) {
    	changeScene(Pages.MANAGE_MAP, "map", id);
		
    }
    
    private void deleteMapButton(int id) {
    	var controllerMaps = new ManageMapControl();
    	try {
	    	controllerMaps.deleteMap(id, getUsername());
			changeScene(Pages.MAIN_MENU);
    	} catch (UsernameNotLoggedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void handleManageHunt(ActionEvent event) {
		changeScene(Pages.MANAGE_HUNT);
    }
    

    @FXML
    void handleCreateMap(ActionEvent event) {
		changeScene(Pages.MANAGE_MAP);
    }
    
    @FXML
    void handleLogin(ActionEvent event) {
    	changeScene(Pages.LOGIN);
		
    }
    

}

