package logic.view.desktop.controller.item;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.control.ManageHuntControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.HuntInformationGController;

public class ItemHuntRatingGController extends ItemController{
	
    @FXML
    private AnchorPane ancHunt;

    @FXML
    private Label lbNameHunt;

    @FXML
    private Button btnModify;

    @FXML
    private Button bntStartHunt;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInformation;
    
    @FXML
    private Rating rtHunt;
    
    private int idHunt;

	public ItemHuntRatingGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
	}
	

    @FXML
    void handleModifyHunt(ActionEvent event) {
    	changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.toString(), idHunt);
    }

    @FXML
    void handleMoreInformation(ActionEvent event) {
    	var controller = new HuntInformationGController(Pages.HUNT_INFORMATION, mainController);
    	List <String> item = new ArrayList<>();
    	item.add(String.valueOf(idHunt));
    	item.add(lbNameHunt.getText());
    	createStage(controller, item, StringHardCode.HUNT_INFORMATION.toString());
    }

    @FXML
    void handleStartGame(ActionEvent event) {
    	changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.toString(), idHunt);
    }
    
    @FXML
    void handleDeleteHunt(ActionEvent event) {
    	var controller = new ManageHuntControl();
    	var huntBean = new HuntBean();
    	huntBean.setIdHunt(idHunt);
    	huntBean.setHuntName(lbNameHunt.getText());
    	controller.deleteHunt(huntBean);
    	changeScene(Pages.PROFILE);
    }

	@Override
	public Parent getBox() {
		return ancHunt;
	}


	@Override
	public void start(String arg, Object param) {
		var huntBean = (HuntBean) param;
		lbNameHunt.setText(huntBean.getHuntName());
		idHunt = huntBean.getIdHunt();
		rtHunt.setRating(huntBean.getAvgRating());
		rtHunt.setDisable(true);
		
	}

}
