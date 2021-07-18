package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;

import org.junit.Test;

import logic.exception.DatabaseException;
import logic.model.dao.PlayHuntDao;

//Test Falasca
public class TestPlayedHunt {

	static final String USERNAME = "pippo";
	
	@Test
	public void testGetPlayedHunt() throws DatabaseException {
		var dao = new PlayHuntDao();
		
		var playedHunts = dao.getPlayedHunt(USERNAME);
		var playedHunt = playedHunts.get(0);
		
		var hunt = playedHunt.getHunt();
		assertNotNull(hunt);
		assertNotNull(hunt.getCreatorName());
		assertTrue(hunt.getIdHunt() > 0);
		assertNotNull(playedHunt.isFinished());
		
		var rating = playedHunt.getRating();
		assertTrue(rating >= 0 && rating <= 5);
	}
	
	@Test
	public void testSetHuntAsPlayed() {
		var dao = new PlayHuntDao();
		
		assertDoesNotThrow(() -> dao.setHuntAsPlayed(1, USERNAME, LocalDate.now()));
	}
	
	@Test
	public void testSetHuntAsFinished() {
		var dao = new PlayHuntDao();
		
		assertDoesNotThrow(() -> dao.setHuntAsFinished(1, USERNAME));
	}
}
