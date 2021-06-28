package logic.view.desktop.controller.item;

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
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.ItemController;

public class ItemHuntGController extends ItemController{
	
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
    
    public ItemHuntGController(Pages page, ControllerWithLogin mainController) {
    	super(page, mainController);
    }
    
    @FXML
    void handleDeleteHunt(ActionEvent event) {
    	var controller = new ManageHuntControl();
    	var huntBean = new HuntBean();
    	huntBean.setIdHunt(idHunt);
    	huntBean.setHuntName(lbHuntName.getText());
    	controller.deleteHunt(huntBean);
    	changeScene(Pages.LOGIN);
    }

    @FXML
    void handleModifyHunt(ActionEvent event) {
    	changeScene(Pages.MANAGE_HUNT, StringHardCode.HUNT.getString(), idHunt);
    }

    @FXML
    void handleStartGame(ActionEvent event) {
    	changeScene(Pages.HUNT, null, null);

    }
    
    @Override
    public void setInfo(Object item) {
    	HuntBean itemBean = (HuntBean) item;
    	lbHuntName.setText(itemBean.getHuntName());
    	idHunt = itemBean.getIdHunt();
    }
    
    public AnchorPane getBox() {
    	return ancHunt;
    }

}
