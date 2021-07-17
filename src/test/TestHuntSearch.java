package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import logic.control.PlayHuntControl;
import logic.exception.DatabaseException;

public class TestHuntSearch {

	@Test
	public void testSearchHunt() throws DatabaseException {
		var controller = new PlayHuntControl();
		
		assertNotEquals(null, controller.getHuntsBySearch("pippo"));
	}
}
