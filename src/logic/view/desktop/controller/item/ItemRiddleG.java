package logic.view.desktop.controller.item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import logic.bean.RiddleBean;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.ManageHuntGController;

public class ItemRiddleG  extends ItemController{
	
	private int idRiddle;
	
	private ManageHuntGController controller;
	
    public ItemRiddleG(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		controller = (ManageHuntGController) mainController;
		
	}

	@FXML
    private AnchorPane ancPane;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnDelete;
    
	@Override
	public void setInfo(Object itemBean) {
		var riddle = (RiddleBean) itemBean;
		idRiddle = riddle.getNumRiddle();
		
	}

	@Override
	public Parent getBox() {
		return ancPane;
	}

    @FXML
    void handleDeleteHunt(ActionEvent event) {
    	controller.removeRiddle(idRiddle);
    }

    @FXML
    void handleModifyHunt(ActionEvent event) {
    	controller.modifyRiddle(idRiddle);
    }
}
