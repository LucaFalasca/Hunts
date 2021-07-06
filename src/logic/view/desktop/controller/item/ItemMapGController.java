package logic.view.desktop.controller.item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.MapBean;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.view.desktop.controller.ControllerWithLogin;

public class ItemMapGController extends ItemController{
	
    @FXML
    private AnchorPane ancMapPane;

    @FXML
    private Label lbMapName;

    @FXML
    private Button bntModifyMap;

    @FXML
    private Button btnDeleteMap;
    
    private int idMap;
    private String creatorName;
    

    public ItemMapGController(Pages page, ControllerWithLogin mainController){
    	super(page, mainController);
    }
    
    @FXML
    void handleDeleteMap(ActionEvent event) {
    	var controllerMap = new ManageMapControl();
    	controllerMap.deleteMap(idMap, creatorName);
    	changeScene(Pages.MAIN_MENU);
    }

    @FXML
    void handleModifyMap(ActionEvent event) {
    	changeScene(Pages.MANAGE_MAP, StringHardCode.MAP.toString(), idMap);
    }

    @Override
	public void setInfo(Object item) {
    	MapBean itemBean = (MapBean)item;
		lbMapName.setText(itemBean.getName());
		idMap = itemBean.getId();
		creatorName = itemBean.getCreatorName();
	}

    @Override
	public AnchorPane getBox() {
		return ancMapPane;
	}
	
}
