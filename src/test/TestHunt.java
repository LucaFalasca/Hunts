package test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

import logic.model.dao.HuntDao;

public class TestHunt {
	
	@Test
	public void testGetReviewsByHuntAreNotNull() {
		HuntDao dao = new HuntDao();

		assertNotEquals(null, dao.searchByName("a"));
	}
}
