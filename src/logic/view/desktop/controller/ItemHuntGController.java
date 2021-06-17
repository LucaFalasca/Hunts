package logic.view.desktop.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.control.ManageHuntControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;

public class ItemHuntGController extends ControllerWithLogin{
	
    @FXML
    private AnchorPane ancHunt;
    
    @FXML
    private Label lbHuntName;

    @FXML
    private Button btnModifyHunt;

    @FXML
    private Button bntStart;

    @FXML
    private Button btnDeleteHunt;

    @FXML
    private Label lbHuntCreator;
    
    private int idHunt;
    
    public ItemHuntGController() {
    	var fxmlLoader = new FXMLLoader(getClass().getResource(Pages.ITEM_MAP.getPath()));
        fxmlLoader.setController(this);
        
        try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void handleDeleteHunt(ActionEvent event) {
    	var controller = new ManageHuntControl();
    	var huntBean = new HuntBean();
    	huntBean.setIdHunt(idHunt);
    	huntBean.setHuntName(lbHuntName.getText());
    	controller.deleteHunt(huntBean);
    }

    @FXML
    void handleModifyHunt(ActionEvent event) {
    	changeScene(Pages.MANAGE_HUNT, StringHardCode.HUNT.getString(), idHunt);
    }

    @FXML
    void handleStartGame(ActionEvent event) {
    	changeScene(Pages.HUNT, null, null);

    }
    
    public void setItem(HuntBean huntBean) {
    	lbHuntName.setText(huntBean.getHuntName());
    	idHunt = huntBean.getIdHunt();
    }
    
    public AnchorPane getBox() {
    	return ancHunt;
    }

	@Override
	void start(String arg, Object param) {
		//need to choose what to do with this method
		
	}


}
