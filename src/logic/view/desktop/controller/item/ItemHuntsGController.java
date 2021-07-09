package logic.view.desktop.controller.item;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    
    private int idHunt;
    
    public ItemHuntsGController(Pages page, ControllerWithLogin mainController) {
    	super(page, mainController);
    }
    
    @FXML
    void handleStartGame(ActionEvent event) {
    	List<String> list = new ArrayList<>();
    	list.add(String.valueOf(idHunt));
    	list.add(lbHuntCreator.getText());
    	mainController.changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.toString(), list);
    }
    
    @FXML
    void handleMoreInformation(ActionEvent event) {
    	var controller = new HuntInformationGController(Pages.HUNT_INFORMATION, mainController);
    	List <String> item = new ArrayList<>();
    	item.add(String.valueOf(idHunt));
    	item.add(lbHuntCreator.getText());
    	createStage(controller, item, StringHardCode.HUNT_INFORMATION.toString());
    }

	public AnchorPane getBox() {
		return ancHunt;
	}

	@Override
	public void start(String arg, Object param) {
		HuntBean itemBean = (HuntBean) param;
		lbHuntName.setText(itemBean.getHuntName());
		lbHuntCreator.setText(itemBean.getUsername());
		rtHunt.setRating(itemBean.getAvgRating());
		rtHunt.setDisable(true);
		idHunt = itemBean.getIdHunt();
		
	}
    
}
