package logic.view.desktop.controller.item;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.RiddleBean;
import logic.enumeration.Pages;
import logic.view.desktop.controller.ControllerWithLogin;

public class ItemRiddleShortController extends ItemController{

    @FXML
    private AnchorPane root;
    
	@FXML
    private CheckBox cbDone;

    @FXML
    private Label lbRiddle;

    @FXML
    private Label lbZone;

	@Override
	public Parent getBox() {
		return root;
	}
	
	public ItemRiddleShortController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
	}

	@Override
	public void start(String arg, Object param) {
		RiddleBean bean = (RiddleBean) param;
		lbRiddle.setText(bean.getRiddle());
		if(bean.getZoneName() != null)
			lbZone.setText(bean.getZoneName());
		else
			lbZone.setVisible(false);
		
		cbDone.setSelected(bean.isCompleted());
		
	}
}
