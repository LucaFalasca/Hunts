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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.control.ManageHuntControl;
import logic.control.ManageMapControl;
import logic.control.PlayHuntControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
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

    @FXML
    private Button btnDeleteSearch;
    
    @FXML
    private ImageView imgCancel;
    
	ObservableList<HuntBean> huntsList = FXCollections.observableArrayList();
	
	ManageHuntControl controller = new ManageHuntControl();
    @Override
	protected void start(String arg, Object param) {
    	huntsList.addAll(controller.getAllHunts());
		lvHunts.setItems(huntsList);
		
		if(isLogged()) {
    		ObservableList<MapBean> mapsList = FXCollections.observableArrayList();
    		ObservableList<HuntBean> huntList = FXCollections.observableArrayList();
			var controllerMaps = new ManageMapControl();
    		try {
    			
    			apMaps.setDisable(false);
    			mapsList.addAll(controllerMaps.getAllMaps(getUsername()));
    			lvMaps.setItems(mapsList);
    			

				apHunts.setDisable(false);
				huntList.addAll(controller.getAllHunts(getUsername()));
    			lvMyHunts.setItems(huntList);
				
			} catch (UsernameNotLoggedException e) {
				showAlert(e.getMessage());
				changeScene(Pages.LOGIN);
			}
    		
    		
			
			
			
		}
		
		setCells();
		
	}
    
    private void setCells() {
    	lvHunts.setCellFactory(hunt -> new ListCell<HuntBean>() {
			@Override
			public void updateItem(HuntBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemHuntsGController(Pages.ITEM_HUNTS, getIstance());
					newItem.start(StringHardCode.HUNT.toString(),itemBean);
					setGraphic(newItem.getBox());
					
				} else {
					setGraphic(null);
					setText(null);
				}
			}
		});
    	
    	lvMaps.setCellFactory(map -> new ListCell<MapBean>() {
			@Override
			public void updateItem(MapBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemMapGController(Pages.ITEM_MAP, getIstance());
					newItem.start(StringHardCode.MAP.toString(),itemBean);
					setGraphic(newItem.getBox());
					
				} else {
					setGraphic(null);
					setText(null);
				}
			}
		});
    	
    	lvMyHunts.setCellFactory(hunts -> new ListCell<HuntBean>() {
			@Override
			public void updateItem(HuntBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemHuntGController(Pages.ITEM_HUNT, getIstance());
					newItem.start(StringHardCode.HUNT.toString(), itemBean);
					setGraphic(newItem.getBox());
					
				}  else {
					setGraphic(null);
					setText(null);
				}
			}
		});
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
    	var searchName = tfSearchName.getText();
    	
    	if(searchName.equals("")) {
    		remove();
    	}
    	else {
    		var controllerPlay = new PlayHuntControl();
			huntsList.setAll(controllerPlay.getHuntsBySearch(searchName));
    	}
    }
    
    @FXML
    void handleRemoveSearch(MouseEvent event) {
    	remove();
    }
    
    private void remove() {
    	huntsList.setAll(controller.getAllHunts());
    	tfSearchName.setText("");
    }
}

