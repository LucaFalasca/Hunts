package logic.view.desktop.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import logic.bean.AnswerBean;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.RiddleBean;
import logic.bean.ZoneBean;
import logic.control.ManageHuntControl;
import logic.control.PlayHuntControl;
import logic.enumeration.Pages;
import logic.enumeration.StringHardCode;
import logic.parser.Parser;
import logic.state.draw.DrawMachine;
import logic.state.draw.states.OvalState;
import logic.state.draw.states.RectangleState;
import logic.view.desktop.controller.item.ItemRiddleShortController;

public class PlayHuntGController extends ControllerWithLogin{

	@FXML
    private ImageView ivMap;

    @FXML
    private Canvas canvasDraw;
    private GraphicsContext gcDraw;
    private DrawMachine drawMachine;
    
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

    private List<Label> lbClues = new ArrayList<>();
	
    private ObservableList<RiddleBean> riddleList = FXCollections.observableArrayList();
    
    RiddleBean currentRiddle;
    
	@Override
	protected void start(String arg, Object param) {
		drawMachine = new DrawMachine();
		gcDraw = canvasDraw.getGraphicsContext2D();
        gcDraw.setFill(Color.web("0xeaed91", 0.5));
		
		lbClues.add(lbClue1);
		lbClues.add(lbClue2);
		lbClues.add(lbClue3);
		
		if(arg != null && arg.equals(StringHardCode.HUNT.toString())) {
				List<?> par = (List<?>) param;
				var controller = new ManageHuntControl();
				var id = Integer.valueOf((String) par.get(0));
				String username = (String) par.get(1);
				
				HuntBean hunt = controller.getHunt(id, username);
				MapBean map = hunt.getMap();
				setMap(map);
				riddleList.setAll(hunt.getRiddle());
			
		}
		lvRiddle.setItems(riddleList);
		lvRiddle.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<RiddleBean>() {
        	
			@Override
			public void changed(ObservableValue<? extends RiddleBean> arg0, RiddleBean arg1, RiddleBean arg2) {
				boxAnswer.setVisible(true);
				lbDomanda.setText(arg2.getRiddle());
				currentRiddle = arg2;
				if(arg2.getClue() != null) {
					for(var i = 0; i < arg2.getClue().size(); i++)
						lbClues.get(i).setText(arg2.getClueElement(i));
					for(var i = 0; i < arg2.getClueUsed(); i++){
						lbClues.get(i).setVisible(true);
					}
				}
			}
          });
		
		lvRiddle.setCellFactory(hunt -> new ListCell<RiddleBean>() {
			@Override
			public void updateItem(RiddleBean itemBean, boolean empty) {
				super.updateItem(itemBean, empty);
				if(itemBean != null) {
					var newItem = new ItemRiddleShortController(Pages.ITEM_RIDDLE_SHORT, getIstance());
					newItem.start(StringHardCode.RIDDLE.toString(),itemBean);
					setGraphic(newItem.getBox());
				}
			}
			
			
		});
	}
	
	private void setMap(MapBean map) {
		if(map != null) {
			setImageByPath(map.getImage());
			if(map.getZones() != null) {
				for(ZoneBean zone : map.getZones()) {
					if(zone.getShape().equals("Oval")) {
						drawMachine.setState(OvalState.getInstance());
					} else {
						drawMachine.setState(RectangleState.getInstance());
					}
					double x1 = Parser.parseFromPercent(zone.getX1(), canvasDraw.getWidth());
					double x2 = Parser.parseFromPercent(zone.getX2(), canvasDraw.getWidth());
					double y1 = Parser.parseFromPercent(zone.getY1(), canvasDraw.getHeight());
					double y2 = Parser.parseFromPercent(zone.getY2(), canvasDraw.getHeight());
					drawMachine.draw(gcDraw, x1, y1, x2, y2);
				}
			}
		}
    }
	
	
	
	private void setImageByPath(String path) {
    	var img = new Image("file:" + path, ivMap.getFitWidth(), ivMap.getFitHeight(), false, false);
    	ivMap.setImage(img);
    }

	@FXML
    void handleHelp(ActionEvent event) {
		int used = currentRiddle.getClueUsed();
		if(used < 3) {
			lbClues.get(used).setVisible(true);
			currentRiddle.setClueUsed(++used);
		}
    }

    @FXML
    void handleAnswer(ActionEvent event) {
    	var controller = new PlayHuntControl();
    	var bean = new AnswerBean();
    	bean.setRiddleAnswer(currentRiddle.getSolution());
    	bean.setUserAnswer(tfRisposta.getText());
    	if(controller.answer(bean)) {
    		riddleList.get(lvRiddle.getSelectionModel().getSelectedIndex()).setCompleted();
    		lvRiddle.refresh();
    		if(huntIsCompleted()) {
    			//Partita finita
    		}
		}else {
    		//sbagliato
    	}
	}

    private boolean huntIsCompleted() {
    	for(RiddleBean riddle: riddleList) {
    		if(!riddle.isCompleted())	return false;
    	}
    	return true;
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
