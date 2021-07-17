package logic.view.desktop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.PlayedHuntBean;
import logic.bean.ReviewBean;
import logic.control.ManageHuntControl;
import logic.control.PlayHuntControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.exception.DatabaseException;
import logic.view.desktop.controller.item.ItemController;
import logic.view.desktop.controller.item.ItemMapGController;
import logic.view.desktop.controller.item.ItemReviewGController;
import javafx.scene.image.Image;

import java.util.List;

import org.controlsfx.control.Rating;

public class HuntInformationGController extends ItemController{
	
    @FXML
    private AnchorPane ancPane;

	@FXML
    private ListView<ReviewBean> lvHuntReview;

    @FXML
    private Label lbHuntName;

    @FXML
    private Rating rtHunt;

    @FXML
    private Label lbIndoor;

    @FXML
    private ImageView imgMap;

    @FXML
    private Canvas canvas;

    @FXML
    private Label lbCreatorName;

    @FXML
    private Button btnStart;
    
    List<?> itemList;

    @FXML
    void handleStart(ActionEvent event) {
    	mainController.changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.toString(), itemList);
    	var stage = (Stage) ancPane.getScene().getWindow();
		stage.close();
    }
    
    public HuntInformationGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
	}

	@Override
	public Parent getBox() {
		return ancPane;
	}

	@Override
	public void start(String arg, Object param) {
		itemList = (List<?>) param; 
		
		var idHunt = Integer.valueOf((String) itemList.get(0));
		
		var username = (String) itemList.get(1);
		
		try {
			var controllerHunt = new ManageHuntControl();
			var controllerPlay = new PlayHuntControl();
			
			var hb = controllerHunt.getHunt(idHunt, username);
			lbHuntName.setText(hb.getHuntName());
			lbCreatorName.setText(hb.getUsername());
			var map = hb.getMap();
			imgMap.setImage(new Image("File:" + map.getImage(), imgMap.getFitWidth(), imgMap.getFitHeight(), false, false));
			rtHunt.setRating(hb.getAvgRating());
			rtHunt.setDisable(true);
			
			ObservableList<ReviewBean> reviewsList = FXCollections.observableArrayList();
			
			reviewsList.setAll(controllerPlay.getReviews(hb));
			
			lvHuntReview.setItems(reviewsList);
			lvHuntReview.setCellFactory(review -> new ListCell<ReviewBean>() {
				@Override
				public void updateItem(ReviewBean itemBean, boolean empty) {
					super.updateItem(itemBean, empty);
					if(itemBean != null) {
						var newItem = new ItemReviewGController(Pages.ITEM_REVIEW, getIstance());
						newItem.start(StringHardCode.REVIEW.toString(),itemBean);
						setGraphic(newItem.getBox());
						
					} else {
						setGraphic(null);
						setText(null);
					}
				}
			});
		}catch(DatabaseException e) {
			showAlert(e.getMessage());
		}
	}

}
