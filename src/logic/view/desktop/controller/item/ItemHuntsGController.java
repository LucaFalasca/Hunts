package logic.view.desktop.controller.item;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.view.desktop.controller.ControllerWithLogin;

public class ItemHuntsGController extends ItemController{
    @FXML
    private AnchorPane ancHunt;

    @FXML
    private Label lbHuntName;

    @FXML
    private Button bntStart;

    @FXML
    private Label lbHuntCreator;

    private int idHunt;
    
    public ItemHuntsGController(Pages page, ControllerWithLogin mainController) {
    	super(page, mainController);
    }
    
    @FXML
    void handleStartGame(ActionEvent event) {
    	List<String> list = new ArrayList<>();
    	list.add(String.valueOf(idHunt));
    	list.add(lbHuntCreator.getText());
    	changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.getString(), list);
    }

    @Override
	public void setInfo(Object item) {
		
		HuntBean itemBean = (HuntBean) item;
		lbHuntName.setText(itemBean.getHuntName());
		lbHuntCreator.setText(itemBean.getUsername());
		idHunt = itemBean.getIdHunt();
	}

	public AnchorPane getBox() {
		return ancHunt;
	}
    
}
