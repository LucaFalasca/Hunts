package logic.view.desktop.controller;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.control.ManageHuntControl;
import logic.control.ManageMapControl;
import logic.control.PlayHuntControl;
import logic.enumeration.Pages;
import logic.exception.UsernameNotLoggedException;
import logic.view.desktop.controller.item.ItemHuntGController;
import logic.view.desktop.controller.item.ItemHuntsGController;
import logic.view.desktop.controller.item.ItemMapGController;

public class MainMenuGController extends ControllerWithLogin{
	
    @FXML
    private TextField tfSearchName;
    
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
    private ListView<MapBean> lvMaps;
    
    @FXML
    private ListView<HuntBean> lvMyHunts;
    
    @FXML
    private ListView<HuntBean> lvHunts;
    
    @FXML
    private AnchorPane apHunts;
    
    @FXML
    private AnchorPane apMaps;
    
    @FXML
    private Button btnSearch;
    
    private List<HuntBean> huntBeans = null;
	ObservableList<HuntBean> huntsList = FXCollections.observableArrayList();

    @Override
	void start(String arg, Object param) {
    	
		var controllerHunts = new ManageHuntControl();
    	huntBeans = controllerHunts.getAllHunts();
		if(huntBeans != null) {
			huntsList.addAll(huntBeans);
			lvHunts.setItems(huntsList);
			lvHunts.setCellFactory(hunt -> new ListCell<HuntBean>() {
				@Override
				public void updateItem(HuntBean itemBean, boolean empty) {
					super.updateItem(itemBean, empty);
					if(itemBean != null) {
						var newItem = new ItemHuntsGController(Pages.ITEM_HUNTS, getIstance());
						newItem.setInfo(itemBean);
						setGraphic(newItem.getBox());
						
					}
				}
			});
		}
		if(isLogged()) {
    		List<MapBean> mapBeans = null;
    		ObservableList<MapBean> mapsList = FXCollections.observableArrayList();
    		ObservableList<HuntBean> huntList = FXCollections.observableArrayList();
			var controllerMaps = new ManageMapControl();
    		try {
				mapBeans = controllerMaps.getAllMaps(getUsername());
				huntBeans = controllerHunts.getAllHunts(getUsername());
			} catch (UsernameNotLoggedException e) {
				showAlert(e.getMessage());
				changeScene(Pages.LOGIN);
			}
    		
    		if(mapBeans != null) {
    			apMaps.setDisable(false);
				mapsList.addAll(mapBeans);
				lvMaps.setItems(mapsList);
				lvMaps.setCellFactory(map -> new ListCell<MapBean>() {
					@Override
					public void updateItem(MapBean itemBean, boolean empty) {
						super.updateItem(itemBean, empty);
						if(itemBean != null) {
							var newItem = new ItemMapGController(Pages.ITEM_MAP, getIstance());
							newItem.setInfo(itemBean);
							setGraphic(newItem.getBox());
							
						}
					}
				});
    		}
			
			if(huntBeans != null) {
				apHunts.setDisable(false);
				huntList.addAll(huntBeans);
				lvMyHunts.setItems(huntList);
				lvMyHunts.setCellFactory(hunt -> new ListCell<HuntBean>() {
					@Override
					public void updateItem(HuntBean itemBean, boolean empty) {
						super.updateItem(itemBean, empty);
						if(itemBean != null) {
							var newItem = new ItemHuntGController(Pages.ITEM_HUNT, getIstance());
							newItem.setInfo(itemBean);
							setGraphic(newItem.getBox());
							
						}
					}
				});
			}
			
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
    
    @FXML
    void handleSearch(ActionEvent event) {
    	var controller = new PlayHuntControl();
    	var searchName = tfSearchName.getText();
    	
    	huntBeans = controller.getHuntsBySearch(searchName);
    	
    	huntsList.setAll(huntBeans);
    	
    }
    

}

