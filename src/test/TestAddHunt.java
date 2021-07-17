package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.bean.HuntBean;
import logic.bean.RiddleBean;
import logic.control.ManageHuntControl;
import logic.exception.DatabaseException;

public class TestAddHunt {
	
	//Andrea Paolo Mancuso's test
	@Test
	public void addHunt() throws DatabaseException {
		var hb = new HuntBean();
		var rb = new RiddleBean();
		List<RiddleBean> rbList = new ArrayList<>();
		var controller = new ManageHuntControl();
		
		rb.setRiddle("Dove si trova il forno");
		rb.setSolution("Salotto");
		rbList.add(rb);
		
		hb.setHuntName("Test");
		hb.setUsername("pippo");
		hb.setPrivate(false);
		hb.setIdHunt(-1);
		hb.setMap(null);
		hb.setAvgRating(2);
		hb.setRiddle(rbList);
		hb.setObject(null);
		
		assertNotEquals(-1, controller.saveHunt(hb));
		
		controller.deleteHunt(hb);
	}

}
