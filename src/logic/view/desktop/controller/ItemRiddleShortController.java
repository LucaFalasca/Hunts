package logic.view.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import logic.bean.RiddleBean;
import logic.enumeration.Pages;

public class ItemRiddleShortController extends ItemController{
	
	protected ItemRiddleShortController(Pages page, ControllerWithLogin mainController) {
		super(page, mainController);
		// TODO Auto-generated constructor stub
	}

    @FXML
    private AnchorPane root;
    
	@FXML
    private CheckBox cbDone;

    @FXML
    private Label lbRiddle;

    @FXML
    private Label lbZone;

	@Override
	public void setInfo(Object itemBean) {
		RiddleBean bean = (RiddleBean) itemBean;
		lbRiddle.setText(bean.getRiddle());
		if(bean.getZoneName() != null)
			lbZone.setText(bean.getZoneName());
		else
			lbZone.setVisible(false);
	}

	@Override
	public Parent getBox() {
		return root;
	}
}
