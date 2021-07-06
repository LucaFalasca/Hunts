package logic.view.desktop.controller.item;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.HuntBean;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.HuntInformationGController;

public class ItemHuntsGController extends ItemController{
    @FXML
    private AnchorPane ancHunt;

    @FXML
    private Label lbHuntName;

    @FXML
    private Button bntStart;

    @FXML
    private Label lbHuntCreator;

    @FXML
    private Button btnHuntInformation;
    
    @FXML
    private Rating rtHunt;
    
    private ControllerWithLogin istance; 
    
    private int idHunt;
    
    public ItemHuntsGController(Pages page, ControllerWithLogin mainController) {
    	super(page, mainController);
    	istance = mainController;
    }
    
    @FXML
    void handleStartGame(ActionEvent event) {
    	List<String> list = new ArrayList<>();
    	list.add(String.valueOf(idHunt));
    	list.add(lbHuntCreator.getText());
    	changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.getString(), list);
    }
    
    @FXML
    void handleMoreInformation(ActionEvent event) {
    	var controller = new HuntInformationGController(Pages.HUNT_INFORMATION, istance);
    	List <String> item = new ArrayList<>();
    	item.add(String.valueOf(idHunt));
    	item.add(lbHuntCreator.getText());
    	controller.setInfo(item);
    	var stage = new Stage();
        stage.setTitle("Hunt information");
        var scene = new Scene(controller.getBox());
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @Override
	public void setInfo(Object item) {
		HuntBean itemBean = (HuntBean) item;
		lbHuntName.setText(itemBean.getHuntName());
		lbHuntCreator.setText(itemBean.getUsername());
		rtHunt.setRating(itemBean.getAvgRating());
		rtHunt.setDisable(true);
		idHunt = itemBean.getIdHunt();
	}

 

	public AnchorPane getBox() {
		return ancHunt;
	}
    
}
