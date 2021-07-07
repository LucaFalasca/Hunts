package logic.model.entity;

public class PlayedHunt {
	 
	private Hunt hunt;
	private double rating;
	private boolean finished;
	
	public Hunt getHunt() {
		return hunt;
	}
	public void setHunt(Hunt hunt) {
		this.hunt = hunt;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating2) {
		this.rating = rating2;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	
}
