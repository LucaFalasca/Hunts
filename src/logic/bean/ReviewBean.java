package logic.bean;

import java.time.LocalDate;

public class ReviewBean {
	
	private int numReview;
	private String username;
	private int vote;
	private String reviewText;
	private LocalDate reviewDate;
	
	public int getNumReview() {
		return numReview;
	}
	public void setNumReview(int numReview) {
		this.numReview = numReview;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	
	public LocalDate getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	
	
}
