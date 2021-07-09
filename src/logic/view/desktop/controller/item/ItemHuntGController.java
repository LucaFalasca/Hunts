package logic.view.desktop.controller.item;


import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    
    private int idHunt;
    
    private String username;
    
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
    	changeScene(Pages.MAIN_MENU);
    }

    @FXML
    void handleModifyHunt(ActionEvent event) {
    	changeScene(Pages.MANAGE_HUNT, StringHardCode.HUNT.toString(), idHunt);
    }

    @FXML
    void handleStartGame(ActionEvent event) {
    	List<String> param = new ArrayList<>();
    	param.add(String.valueOf(idHunt));
    	param.add(username);
    	changeScene(Pages.PLAY_HUNT, StringHardCode.HUNT.toString(), param);

    }
    
    @FXML
    void handleMoreInformation(ActionEvent event) {
    	var controller = new HuntInformationGController(Pages.HUNT_INFORMATION, mainController);
    	List <String> item = new ArrayList<>();
    	item.add(String.valueOf(idHunt));
    	item.add(username);
    	controller.start(StringHardCode.HUNT.toString(), item);
    	var stage = new Stage();
        stage.setTitle("Hunt information");
        var scene = new Scene(controller.getBox());
        stage.setScene(scene);
        stage.showAndWait();
    }

    
    public AnchorPane getBox() {
    	return ancHunt;
    }

	@Override
	public void start(String arg, Object param) {
		HuntBean itemBean = (HuntBean) param;
    	lbHuntName.setText(itemBean.getHuntName());
    	idHunt = itemBean.getIdHunt();
    	username = itemBean.getUsername();
		
	}

}
