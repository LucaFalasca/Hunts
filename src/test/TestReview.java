package test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;

import org.junit.Test;

import logic.exception.DatabaseException;
import logic.model.dao.ReviewDao;
import logic.model.entity.Review;

//Test Mancuso
public class TestReview {

	@Test
	public void testSaveReview() throws DatabaseException {
		var review = new Review();
		review.setReviewer("pippo");
		review.setText("Bella Mappa!");
		review.setRating(4);
		review.setDate(LocalDate.now());
		
		var dao = new ReviewDao();
		
		assertNotEquals(-1, dao.saveReview(review, 1));
	}
	
	@Test
	public void testGetReviewsByHuntAreNotNull() throws DatabaseException {
		var dao = new ReviewDao();

		assertNotEquals(null, dao.getReviewsByHunt(1));
	}
	
	@Test
	public void testGetReviewsByHuntAreNotEmpty() throws DatabaseException {
		var dao = new ReviewDao();

		var result = dao.getReviewsByHunt(1);
		assertFalse(result.isEmpty());
	}
}
