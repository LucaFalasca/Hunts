package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import logic.model.Database;
import logic.model.entity.Hunt;
import logic.model.entity.Review;

public class ReviewDao {

	
	public int saveReview(Review review, int hunt) {
		return saveReview(review.getId(), 
				review.getReviewer(), 
				hunt, 
				review.getRating(),
				review.getText(),
				review.getDate());
	}
	
	public List<Review> getReviewsByHunt(Hunt hunt){
		return getReviewsByHunt(hunt.getIdHunt());
	}
	
	public List<Review> getReviewsByHunt(int idHunt){
		var conn = Database.getConnection();
		
		List<Review> reviews = new ArrayList<>();
		try(CallableStatement stmt = conn.prepareCall("call get_reviews_by_hunt(?);")) {
			
			//Input Param
			stmt.setInt(1, idHunt);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				var rs = stmt.getResultSet();
				while (rs.next()) {
					
					var id = rs.getInt(1);
			        var user = rs.getString(2);
			        
			        var rating = rs.getInt(4);
			        var text = rs.getString(5);
			        var date = rs.getDate(6);
			        
			        var review = new Review();
			        review.setId(id);
			        review.setReviewer(user);
			        review.setRating(rating);
			        review.setText(text);
			        review.setDate(date.toLocalDate());
			        
			        reviews.add(review);
			      }
				haveResult = stmt.getMoreResults();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return reviews;
	}
	
	private int saveReview(int idReview, String user, int idHunt, int rating, String text, LocalDate date) {
		var conn = Database.getConnection();
		var id = -1;
		
		try (CallableStatement stmt = conn.prepareCall("call save_review(?, ?, ?, ?, ?, ?)")){
			
			//Input param
			if(idReview != -1)
				stmt.setInt(1, idReview);
			stmt.setString(2, user);
			stmt.setInt(3, idHunt);
			stmt.setInt(4, rating);
			if(text != null)
				stmt.setString(5, text);
			else
				stmt.setNull(5, java.sql.Types.VARCHAR);
			stmt.setDate(6, Date.valueOf(date));
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
			//Output param
			id = stmt.getInt(1);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
