package logic.view.desktop.controller;

import java.time.LocalDate;

import org.controlsfx.control.Rating;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.PlayedHuntBean;
import logic.bean.ReviewBean;
import logic.control.PlayHuntControl;
import logic.enumeration.Pages;
import logic.exception.UsernameNotLoggedException;
import logic.view.desktop.controller.item.ItemController;

public class LeaveReviewGController extends ItemController{
	
    @FXML
    private Button btnLeaveReview;
    
    @FXML
    private TextArea txtReview;

    @FXML
    private Rating rtHunt;

    @FXML
    private Label lbHuntName;
    
    @FXML
    private AnchorPane ancReview;
    
    private PlayedHuntBean playedHuntBean;
    
    private ControllerWithLogin mainController;

	public LeaveReviewGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		this.mainController = mainController;
	}
	

    @FXML
    void handleLeaveReview(ActionEvent event) {
    	var controller = new PlayHuntControl();
    	
    	var review = new ReviewBean();
    	
    	try {
			review.setUsername(mainController.getUsername());
			review.setReviewText(txtReview.getText());
			review.setVote(rtHunt.getRating());
			review.setReviewDate(LocalDate.now());
			review.setIdHunt(playedHuntBean.getPlayedHunt().getIdHunt());
		} catch (UsernameNotLoggedException e) {
			mainController.showAlert(e.getMessage());
			changeScene(Pages.MAIN_MENU);
		}
    	
    	controller.addReview(review);
    	
    	var stage = (Stage) ancReview.getScene().getWindow();
    	stage.close();
    	
    }

    
	@Override
	public void setInfo(Object itemBean) {
		playedHuntBean = (PlayedHuntBean) itemBean;
		lbHuntName.setText(playedHuntBean.getPlayedHunt().getHuntName());
	}

	@Override
	public Parent getBox() {
		return ancReview;
	}

}
