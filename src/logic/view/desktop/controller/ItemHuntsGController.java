package logic.view.desktop.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.enumeration.Pages;

public class ItemHuntsGController extends ControllerWithLogin{
    @FXML
    private AnchorPane ancHunt;

    @FXML
    private Label lbHuntName;

    @FXML
    private Button bntStart;

    @FXML
    private Label lbHuntCreator;

    private int idHunt;
    
    public ItemHuntsGController() {
    	var fxmlLoader = new FXMLLoader(getClass().getResource(Pages.ITEM_HUNTS.getPath()));
        fxmlLoader.setController(this);
        
        try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleStartGame(ActionEvent event) {
    	changeScene(Pages.HUNT, null, idHunt);
    }

	public void setInfo(HuntBean itemBean) {
		lbHuntName.setText(itemBean.getHuntName());
		lbHuntCreator.setText(itemBean.getUsername());
		idHunt = itemBean.getIdHunt();
	}

	public AnchorPane getBox() {
		return ancHunt;
	}

	@Override
	void start(String arg, Object param) {
		// TODO Auto-generated method stub
		
	}
    
}
