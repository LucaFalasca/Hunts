package logic.bean;

public class PlayedHuntBean {
	private HuntBean playedHunt;
	private double reviewRating;
	private boolean isFinished;
	
	public boolean isFinished() {
		return isFinished;
	}
	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}
	public HuntBean getPlayedHunt() {
		return playedHunt;
	}
	public void setPlayedHunt(HuntBean playedHunt) {
		this.playedHunt = playedHunt;
	}
	public double getReviewRating() {
		return reviewRating;
	}
	public void setReviewRating(double d) {
		this.reviewRating = d;
	}
	
	
}
