package logic.view.desktop.controller;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import logic.bean.MapBean;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;
import logic.exception.PageNotFoundException;
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
    private ListView<String> lvMaps;
    private ObservableList<String> mapsList = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane apHunts;
    
    @FXML
    private AnchorPane apMaps;
    
    @Override
	void start() {
		if(isLogged()) {
			try {
				lbUsername.setText(getUsername());
				lbUsername.setVisible(true);
				btnLogin.setVisible(false);
				
				apMaps.setDisable(false);
				lvMaps.setItems(mapsList);
				
				ManageMapControl controllerMaps = new ManageMapControl();
				List<MapBean> mapBeans = controllerMaps.getAllMaps(getUsername());
				
				for(MapBean mapBean : mapBeans) {
					mapsList.add(mapBean.getName());
				}
				
				
			} catch (UsernameNotLoggedException e) {
				e.printStackTrace();
			}
		}
	}
    
    @FXML
    void initialize() {
    	
    }
    
    
    @FXML
    void handleManageHunt(ActionEvent event) {
    	try {
			changeScene(Pages.MANAGE_HUNT, true);
		} catch (PageNotFoundException e) {
			System.out.println("");
			e.printStackTrace();
		}
    }
    

    @FXML
    void handleCreateMap(ActionEvent event) {
    	try {
			changeScene(Pages.MANAGE_MAP, false);
		} catch (PageNotFoundException e) {
			System.out.println("beh");
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleLogin(ActionEvent event) {
    	try {
			changeScene(Pages.LOGIN, false);
		} catch (PageNotFoundException e) {
			System.out.println("beh");
			e.printStackTrace();
		}
    }

}

