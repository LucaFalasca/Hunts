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
import javafx.scene.control.Alert.AlertType;
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
import logic.exception.DatabaseException;
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
    
    private MapBean map;
    private HuntBean hunt;
    
	@Override
	protected void start(String arg, Object param) {
		drawMachine = new DrawMachine();
		gcDraw = canvasDraw.getGraphicsContext2D();
        gcDraw.setFill(Color.web("0xeaed91", 0.5));
		
		lbClues.add(lbClue1);
		lbClues.add(lbClue2);
		lbClues.add(lbClue3);
		
		if(arg != null && arg.equals(StringHardCode.HUNT.toString())) {
				try {
					List<?> par = (List<?>) param;
					var controller = new ManageHuntControl();
					var id = Integer.valueOf((String) par.get(0));
					String username = (String) par.get(1);
					
					var huntBean = controller.getHunt(id, username);
					this.hunt = huntBean;
					var mapBean = huntBean.getMap();
					setMap(mapBean);
					riddleList.setAll(huntBean.getRiddle());
				}catch(DatabaseException e) {
					showAlert(e.getMessage());
				}
			
		}
		lvRiddle.setItems(riddleList);
		lvRiddle.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<RiddleBean>() {
        	
			@Override
			public void changed(ObservableValue<? extends RiddleBean> arg0, RiddleBean arg1, RiddleBean arg2) {
				change(arg2);
			}
          });
		
		lvRiddle.setCellFactory(huntTemp -> new ListCell<RiddleBean>() {
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
		if(isLogged()) {
			try {
				var playHuntControl = new PlayHuntControl();
				playHuntControl.setHuntAsPlayed(hunt.getIdHunt(), getUsername());
			}catch(DatabaseException e) {
				showAlert(e.getMessage());
			}
		}
	}
	
	private void setMap(MapBean map) {
		if(map != null) {
			this.map = map;
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
	
	private void change(RiddleBean riddle) {
		boxAnswer.setVisible(true);
		lbDomanda.setText(riddle.getRiddle());
		currentRiddle = riddle;
		if(riddle.getClue() != null) {
			for(var i = 0; i < riddle.getClue().size(); i++)
				lbClues.get(i).setText(riddle.getClueElement(i));
			for(var i = 0; i < riddle.getClueUsed(); i++){
				lbClues.get(i).setVisible(true);
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
    	try {
	    	var controller = new PlayHuntControl();
	    	var bean = new AnswerBean();
	    	bean.setRiddleAnswer(currentRiddle.getSolution());
	    	bean.setUserAnswer(tfRisposta.getText());
	    	if(controller.answer(bean)) {
	    		riddleList.get(lvRiddle.getSelectionModel().getSelectedIndex()).setCompleted();
	    		lvRiddle.refresh();
	    		if(controller.isRiddlesCompleted(riddleList)) {
	    			showAlert(AlertType.INFORMATION, "Victory!", "You have finished the Hunt");
	    			if(isLogged()) {
						controller.finishHunt(hunt.getIdHunt(), getUsername(), riddleList);
	    			}
	    			changeScene(Pages.MAIN_MENU);
	    		}
			}else {
	    		showAlert(AlertType.INFORMATION, "Wrong", "Wrong answer");
	    	}
    	}catch(DatabaseException e) {
			showAlert(e.getMessage());
		}
	}

    
    @FXML
    void handleClickOnMap(MouseEvent event) {
		double x = event.getX();
		double y = event.getY();
		
		if(thereIsInAZoneRange(x, y, x, y)) {
			for(ZoneBean zone: map.getZones()) {
				double x1 = Parser.parseFromPercent(zone.getX1(), canvasDraw.getWidth());
				double x2 = Parser.parseFromPercent(zone.getX2(), canvasDraw.getWidth());
				double y1 = Parser.parseFromPercent(zone.getY1(), canvasDraw.getHeight());
				double y2 = Parser.parseFromPercent(zone.getY2(), canvasDraw.getHeight());
				if(isBetween(x, x1, x2) && isBetween(y, y1, y2)) {
					//Need other development
				}
			}
		}
	}
    
    private boolean thereIsInAZoneRange(double x1, double y1, double x2,  double y2) {
    	double rangeX = Math.abs(x2 - x1);
    	double rangeY = Math.abs(y2 - y1);
    	for(ZoneBean zone : map.getZones()) {
    		double xz1;
    		double xz2;
    		double yz1;
    		double yz2;
    		
    		xz1 = Parser.parseFromPercent(zone.getX1(), canvasDraw.getWidth());
    		xz2 = Parser.parseFromPercent(zone.getX2(), canvasDraw.getWidth());
    		yz1 = Parser.parseFromPercent(zone.getY1(), canvasDraw.getHeight());
    		yz2 = Parser.parseFromPercent(zone.getY2(), canvasDraw.getHeight());
    		if(		isBetween(x1, xz1, xz2) && isBetween(y1, yz1, yz2)  || 
    				isBetween(x1, xz1, xz2) && isBetween(y1 + rangeY, yz1, yz2) ||
    				isBetween(x1 + rangeX, xz1, xz2) && isBetween(y1, yz1, yz2) ||
    				isBetween(x1 + rangeX, xz1, xz2) && isBetween(y1 + rangeY, yz1, yz2)) {
    			
    			return true;
    		}
    	}
    	return false;
    }
    
    //Controlla se un numero è compreso tra i due
    private boolean isBetween(double x, double y1, double y2) {
    	if(y2 > y1)	return x < y2 && x > y1;
    	else return x < y1 && x > y2;
    }
    

}
