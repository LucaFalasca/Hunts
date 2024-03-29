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
import logic.exception.DatabaseException;
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

	public LeaveReviewGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
	}
	

    @FXML
    void handleLeaveReview(ActionEvent event) {
    	try {
	    	var controller = new PlayHuntControl();
	    	var review = new ReviewBean();
	    	
			review.setUsername(mainController.getUsername());
			review.setReviewText(txtReview.getText());
			review.setVote(rtHunt.getRating());
			review.setReviewDate(LocalDate.now());
			review.setIdHunt(playedHuntBean.getPlayedHunt().getIdHunt());
	    	controller.addReview(review);
	    	
	    	var stage = (Stage) ancReview.getScene().getWindow();
	    	changeScene(Pages.PROFILE);
	    	stage.close();
    	}catch(DatabaseException e) {
			showAlert(e.getMessage());
		}
    }
    
	@Override
	public Parent getBox() {
		return ancReview;
	}


	@Override
	public void start(String arg, Object param) {
		playedHuntBean = (PlayedHuntBean) param;
		lbHuntName.setText(playedHuntBean.getPlayedHunt().getHuntName());
		
	}

}
