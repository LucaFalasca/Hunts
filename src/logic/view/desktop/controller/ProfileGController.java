package logic.view.desktop.controller;

import org.controlsfx.control.Rating;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.PlayedHuntBean;
import logic.control.ManageHuntControl;
import logic.control.ManageMapControl;
import logic.control.PlayHuntControl;
import logic.control.UserControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.exception.UsernameNotLoggedException;
import logic.view.desktop.controller.item.ItemGameHistoryGController;
import logic.view.desktop.controller.item.ItemHuntRatingGController;
import logic.view.desktop.controller.item.ItemMapGController;

public class ProfileGController extends ControllerWithLogin{
    @FXML
    private Rating rtAvgHunts;

    @FXML
    private Label lbUserName;

    @FXML
    private ImageView imgUser;

    @FXML
    private Canvas canvas;

    @FXML
    private ListView<PlayedHuntBean> lvGameHistory;
    ObservableList<PlayedHuntBean> historyHunt = FXCollections.observableArrayList();

    @FXML
    private AnchorPane apHunts;

    @FXML
    private ListView<HuntBean> lvMyHunts;
    ObservableList<HuntBean> huntsList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane apMaps;

    @FXML
    private ListView<MapBean> lvMaps;
    ObservableList<MapBean> mapsList = FXCollections.observableArrayList();

	@Override
	protected void start(String arg, Object param) {
		var huntController = new ManageHuntControl();
		var userController = new UserControl();
		var playController = new PlayHuntControl();
		var mapController = new ManageMapControl();
		try {
			huntsList.setAll(huntController.getAllHunts(getUsername()));
			lbUserName.setText(getUsername());
			historyHunt.setAll(playController.getPlayedHunt(getUsername()));
			mapsList.setAll(mapController.getAllMaps(getUsername()));
		} catch (UsernameNotLoggedException e) {
			showAlert(e.getMessage());
			changeScene(Pages.LOGIN);
		}
		
		rtAvgHunts.setRating(userController.calculateAvgRate(huntsList));
		rtAvgHunts.setDisable(false);
		setListView();
		
	}
	
	private void setListView() {
		lvMaps.setItems(mapsList);
		lvMaps.setCellFactory(map -> new ListCell<MapBean>() {
			@Override
			public void updateItem(MapBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemMapGController(Pages.ITEM_MAP, getIstance());
					newItem.start(StringHardCode.MAP.toString(), itemBean);
					setGraphic(newItem.getBox());
					
				} else {
					setGraphic(null);
					setText(null);
				}
			}
		});
		
		lvMyHunts.setItems(huntsList);
		lvMyHunts.setCellFactory(hunts -> new ListCell<HuntBean>(){
			@Override
			public void updateItem(HuntBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemHuntRatingGController(Pages.ITEM_HUNT_RATING, getIstance());
					newItem.start(StringHardCode.HUNT.toString(), itemBean);
					setGraphic(newItem.getBox());
					
				} else {
					setGraphic(null);
					setText(null);
				}
			}
		});
		
		lvGameHistory.setItems(historyHunt);
		lvGameHistory.setCellFactory(hunts -> new ListCell<PlayedHuntBean>(){
			@Override
			public void updateItem(PlayedHuntBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemGameHistoryGController(Pages.ITEM_GAME_HISTORY, getIstance());
					newItem.start(StringHardCode.PLAYEDHUNT.toString(),itemBean);
					setGraphic(newItem.getBox());
					
				} else {
					setGraphic(null);
					setText(null);
				}
			}
		});
		
		
	}
	
	
}

