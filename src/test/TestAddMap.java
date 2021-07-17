package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import logic.bean.MapBean;
import logic.control.ManageMapControl;

public class TestAddMap {
	
	//Andrea Paolo Mancuso's Test
	@Test
	public void testAddMap() {
		var map = new MapBean();
		var controller = new ManageMapControl();
		
		map.setId(-1);
		map.setName("test");
		map.setCreatorName("pippo");
		map.setImage(null);
		map.setZones(null);
		
		assertNotEquals(-1, controller.save("pippo", map));
		controller.deleteMap(map.getId(), "pippo");
	}

}
