package logic.view.desktop.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.RiddleBean;
import logic.bean.ZoneBean;
import logic.control.ManageHuntControl;
import logic.control.ManageMapControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.exception.UsernameNotLoggedException;
import logic.state.draw.states.OvalState;
import logic.state.draw.states.RectangleState;
import logic.view.desktop.controller.item.ItemRiddleShortController;

public class PlayHuntGController extends ControllerWithLogin{

	@FXML
    private ImageView ivMap;

    @FXML
    private Canvas canvasDraw;

    @FXML
    private ListView<RiddleBean> lvRiddle;

    @FXML
    private AnchorPane boxAnswer;

    @FXML
    private Label lbDomanda;

    @FXML
    private TextField tfRisposta;

    @FXML
    private Label lbClue1;

    @FXML
    private Label lbClue2;

    @FXML
    private Label lbClue3;

    private ObservableList<RiddleBean> riddleList = FXCollections.observableArrayList();
    
	@Override
	void start(String arg, Object param) {
		if(arg != null) {
			if(arg.equals(StringHardCode.HUNT.getString())) {
				List<?> par = (List<?>) param;
				ManageHuntControl controller = new ManageHuntControl();
				int id = Integer.valueOf((String) par.get(0));
				String username = (String) par.get(1);
				
				HuntBean hunt = controller.getHunt(id, username);
				MapBean map = hunt.getMap();
				setMap(map);
				riddleList.setAll(hunt.getRiddle());
			}
		}
		lvRiddle.setItems(riddleList);
		lvRiddle.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<RiddleBean>() {
        	
			@Override
			public void changed(ObservableValue<? extends RiddleBean> arg0, RiddleBean arg1, RiddleBean arg2) {
				boxAnswer.setVisible(true);
				lbDomanda.setText(arg2.getRiddle());
				if(arg2.getClue() != null) {
					lbClue1.setText(arg2.getClue1());
					lbClue2.setText(arg2.getClue2());
					lbClue3.setText(arg2.getClue3());
				}
			}
          });
		
		lvRiddle.setCellFactory(hunt -> new ListCell<RiddleBean>() {
			@Override
			public void updateItem(RiddleBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemRiddleShortController(Pages.ITEM_RIDDLE_SHORT, getIstance());
					newItem.setInfo(itemBean);
					setGraphic(newItem.getBox());
				}
			}
		});
	}
	
	private void setMap(MapBean map) {
		if(map != null)
			setImageByPath(map.getImage());
    }
	
	
	
	private void setImageByPath(String path) {
    	var img = new Image("file:" + path, ivMap.getFitWidth(), ivMap.getFitHeight(), false, false);
    	ivMap.setImage(img);
    }

	@FXML
    void handleHelp(ActionEvent event) {

    }

    @FXML
    void handleAnswer(ActionEvent event) {

    }

    @FXML
    void handleMovedOnMap(MouseEvent event) {

    }

    @FXML
    void handlePressedOnMap(MouseEvent event) {

    }

    @FXML
    void handleReleasedOnMap(MouseEvent event) {

    }
    
    @FXML
    void handleSelectRiddle(ActionEvent event) {
    	
    }
}
