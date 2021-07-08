package logic.view.desktop.controller.item;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.HuntBean;
import logic.bean.PlayedHuntBean;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.HuntInformationGController;
import logic.view.desktop.controller.LeaveReviewGController;

public class ItemGameHistoryGController extends ItemController{
	
    @FXML
    private AnchorPane ancPane;

    @FXML
    private Label lbHuntName;

    @FXML
    private Rating rtHunts;

    @FXML
    private Button btnHuntInformation;
    
    @FXML
    private Button btnLeaveReview;
    
    @FXML
    private CheckBox cbFinish;
    
    private HuntBean huntBean;
    
    private PlayedHuntBean playedHunt;
    
    private ControllerWithLogin istance;
    
	public ItemGameHistoryGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		istance = mainController;
	}

    @FXML
    void handleLeaveReview(ActionEvent event) {
    	var controller = new LeaveReviewGController(Pages.REVIEW, istance);
    	controller.setInfo(playedHunt);
    	var stage = new Stage();
        stage.setTitle("LeaveReview");
        var scene = new Scene(controller.getBox());
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void handleMoreInformation(ActionEvent event) {
    	var controller = new HuntInformationGController(Pages.HUNT_INFORMATION, istance);
    	List <String> item = new ArrayList<>();
    	item.add(String.valueOf(huntBean.getIdHunt()));
    	item.add(huntBean.getHuntName());
    	controller.setInfo(item);
    	var stage = new Stage();
        stage.setTitle("Hunt information");
        var scene = new Scene(controller.getBox());
        stage.setScene(scene);
        stage.showAndWait();
    }

	@Override
	public void setInfo(Object itemBean) {
		playedHunt = (PlayedHuntBean) itemBean;
		huntBean = playedHunt.getPlayedHunt();
		lbHuntName.setText(huntBean.getHuntName());
		rtHunts.setRating(huntBean.getAvgRating());
		cbFinish.setAllowIndeterminate(playedHunt.isFinished());
		cbFinish.setDisable(true);
	}

	@Override
	public Parent getBox() {
		return ancPane;
	}

}
