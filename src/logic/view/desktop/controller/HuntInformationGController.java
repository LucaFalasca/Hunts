package logic.view.desktop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.bean.HuntBean;
import logic.control.ManageHuntControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.view.desktop.controller.item.ItemController;
import javafx.scene.image.Image;

import java.util.List;

import org.controlsfx.control.Rating;

public class HuntInformationGController extends ItemController{
	
    @FXML
    private AnchorPane ancPane;

	@FXML
    private ListView<HuntBean> lvHuntReview;

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
    
    ControllerWithLogin mainController;
    
    List<?> itemList;

    @FXML
    void handleStart(ActionEvent event) {
    	changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.toString(), itemList);
    	var stage = (Stage) ancPane.getScene().getWindow();
		stage.close();
    }
    
    public HuntInformationGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		this.mainController = mainController;
	}

	@Override
	public void setInfo(Object item) {
		itemList = (List<?>) item; 
		
		var temp = (String) itemList.get(0);
		
		var idHunt = Integer.valueOf(temp);
		
		var username = (String) itemList.get(1);
		
		var controller = new ManageHuntControl();
		
		var hb = controller.getHunt(idHunt, username);
		lbHuntName.setText(hb.getHuntName());
		lbCreatorName.setText(hb.getUsername());
		var map = hb.getMap();
		imgMap.setImage(new Image("File:" + map.getImage(), imgMap.getFitWidth(), imgMap.getFitHeight(), false, false));
		rtHunt.setRating(hb.getAvgRating());
		rtHunt.setDisable(true);
		
		
	}

	@Override
	public Parent getBox() {
		return ancPane;
	}

}
