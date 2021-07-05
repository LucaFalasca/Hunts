package logic.view.desktop.controller.item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.ZoneBean;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;
import logic.view.desktop.controller.ManageMapGController;

public class ItemZoneGController extends ItemController{
	
	@FXML
    private AnchorPane ancZonePane;

    @FXML
    private Label lbZoneName;

    @FXML
    private Button bntModifyZone;

    @FXML
    private Button btnDeleteZone;
    
    private ZoneBean zoneBean;
    
    private ManageMapGController manageMap;
    
    public ItemZoneGController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		manageMap = (ManageMapGController) mainController;
	}
    
    @FXML
    void handleDeleteZone(ActionEvent event) {
    	manageMap.remove(zoneBean);
    }

    @Override
	public void setInfo(Object itemBean) {
		zoneBean = (ZoneBean) itemBean;
		lbZoneName.setText(zoneBean.getNameZone());
		
	}

	@Override
	public Parent getBox() {
		return ancZonePane;
	}

}
