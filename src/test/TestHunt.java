package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import logic.control.ManageHuntControl;
import logic.control.PlayHuntControl;
import logic.exception.DatabaseException;
import logic.model.dao.HuntDao;

public class TestHunt {
	
	@Test
	public void testGetReviewsByHuntAreNotNull() throws DatabaseException{
		var dao = new HuntDao();

		assertNotEquals(null, dao.searchHunt("a"));
	}
	
	@Test
	public void testAvgHuntOnGetHuntById() throws DatabaseException {
		var dao = new HuntDao();
		
		assertNotEquals(null, dao.getHuntById(1, "pippo").getAvgRatingHunt());
	}
	
	@Test
	public void testAvgHuntOnGetHuntList() throws DatabaseException{
		var dao = new HuntDao();
		
		assertNotEquals(null, dao.getHuntList().get(0).getAvgRatingHunt());
	}
	
	@Test
	public void testAvgHuntOnGetHuntListWithUsername() throws DatabaseException{
		var dao = new HuntDao();
		
		assertNotEquals(null, dao.getHuntList("a").get(0).getAvgRatingHunt());
	}
	
	@Test
	public void testAvgHuntOnSearchHunt() throws DatabaseException{
		var dao = new HuntDao();
		
		assertNotEquals(null, dao.searchHunt("a").get(0).getAvgRatingHunt());
	}
	
}
