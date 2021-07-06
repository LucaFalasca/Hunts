package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import logic.model.dao.HuntDao;

public class TestHunt {
	
	@Test
	public void testGetReviewsByHuntAreNotNull() {
		HuntDao dao = new HuntDao();

		assertNotEquals(null, dao.searchHunt("a"));
	}
	
	@Test
	public void testAvgHuntOnGetHuntById() {
		HuntDao dao = new HuntDao();
		
		assertNotEquals(null, dao.getHuntById(1, "pippo").getAvgRatingHunt());
	}
	
	@Test
	public void testAvgHuntOnGetHuntList() {
		HuntDao dao = new HuntDao();
		
		assertNotEquals(null, dao.getHuntList().get(0).getAvgRatingHunt());
	}
	
	@Test
	public void testAvgHuntOnGetHuntListWithUsername() {
		HuntDao dao = new HuntDao();
		
		assertNotEquals(null, dao.getHuntList("a").get(0).getAvgRatingHunt());
	}
	
	@Test
	public void testAvgHuntOnSearchHunt() {
		HuntDao dao = new HuntDao();
		
		assertNotEquals(null, dao.searchHunt("a").get(0).getAvgRatingHunt());
	}
}
