package logic.view.desktop.controller.item;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    private ControllerWithLogin istance;
    
	public ItemHuntRatingGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		istance = mainController;
	}
	

    @FXML
    void handleModifyHunt(ActionEvent event) {
    	changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.toString(), idHunt);
    }

    @FXML
    void handleMoreInformation(ActionEvent event) {
    	var controller = new HuntInformationGController(Pages.HUNT_INFORMATION, istance);
    	List <String> item = new ArrayList<>();
    	item.add(String.valueOf(idHunt));
    	item.add(lbNameHunt.getText());
    	controller.setInfo(item);
    	var stage = new Stage();
        stage.setTitle("Hunt information");
        var scene = new Scene(controller.getBox());
        stage.setScene(scene);
        stage.showAndWait();
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
	public void setInfo(Object itemBean) {
		var huntBean = (HuntBean) itemBean;
		lbNameHunt.setText(huntBean.getHuntName());
		idHunt = huntBean.getIdHunt();
		rtHunt.setRating(huntBean.getAvgRating());
		
	}

	@Override
	public Parent getBox() {
		return ancHunt;
	}

}
