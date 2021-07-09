package logic.view.desktop.controller.item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.bean.ObjectBean;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.ManageHuntGController;

public class ItemObjectGController extends ItemController{

    @FXML
    private AnchorPane ancPane;
    
    @FXML
    private Label lbObjDesc;

    @FXML
    private Label lbObjectName;

    @FXML
    private ImageView imgObject;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDelete;
    
    int idObject;
    
    private ManageHuntGController controller;
    
	public ItemObjectGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		controller = (ManageHuntGController) mainController;
	}
	
    @FXML
    void handleDeleteHunt(ActionEvent event) {
    	controller.removeObject(idObject);
    }

    @FXML
    void handleModifyHunt(ActionEvent event) {
    	controller.modifyObject(idObject);
    }

	@Override
	public Parent getBox() {
		return ancPane;
	}

	@Override
	public void start(String arg, Object param) {
		var objectBean = (ObjectBean) param;
		idObject = objectBean.getIdObject();
		
	}
	

}
