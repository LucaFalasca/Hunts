package logic.view.desktop.controller.item;

import org.controlsfx.control.Rating;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.bean.ReviewBean;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;

public class ItemReviewGController extends ItemController{
	
    @FXML
    private AnchorPane ancPane;

    @FXML
    private ImageView imgUserProfile;

    @FXML
    private Canvas canvas;

    @FXML
    private Label lbUsername;

    @FXML
    private Rating rtHunt;

    @FXML
    private Label lbReviewText;
    
    @FXML
    private Label lbDate;
    
	public ItemReviewGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
	}

	@Override
	public Parent getBox() {
		return ancPane;
	}

	@Override
	public void start(String arg, Object param) {
		var review = (ReviewBean) param;
		rtHunt.setRating(review.getVote());
		lbUsername.setText(review.getUsername());
		lbReviewText.setText(review.getReviewText());
		lbDate.setText(review.getReviewDate().toString());
		
	}

}
