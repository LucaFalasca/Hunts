package logic.control;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import logic.bean.AnswerBean;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.PlayedHuntBean;
import logic.bean.ReviewBean;
import logic.bean.RiddleBean;
import logic.model.dao.HuntDao;
import logic.model.dao.PlayHuntDao;
import logic.model.dao.ReviewDao;
import logic.model.entity.Hunt;
import logic.model.entity.PlayedHunt;
import logic.model.entity.Review;

public class PlayHuntControl {

	public boolean answer(AnswerBean bean) {
		String trueAnswer = bean.getRiddleAnswer().toLowerCase();
		String userAnswer = bean.getUserAnswer().toLowerCase();
		
		return trueAnswer.equals(userAnswer);
		
	}

	public List<HuntBean> getHuntsBySearch(String searchName) {
		var huntDao = new HuntDao();
		List<HuntBean> huntsBean = new ArrayList<>();
		
		var hunts = huntDao.searchHunt(searchName);
		
		for(Hunt hunt: hunts) {
			var hb = new HuntBean();
			var map = new MapBean();
			
			hb.setUsername(hunt.getCreatorName());
			hb.setHuntName(hunt.getHuntName());
			hb.setIdHunt(hunt.getIdHunt());
			if(hunt.getMap() != null) {
				map.setId(hunt.getMap().getId());
				hb.setMap(map);
			}
			huntsBean.add(hb);
		}
		return huntsBean;
		
	}
	
	public List<PlayedHuntBean> getPlayedHunt(String username) {
		var playedDao = new PlayHuntDao();
		
		List<PlayedHuntBean> playedHuntBean = new ArrayList<>();
		
		var playedHunts  = playedDao.getPlayedHunt(username);
		
		for(PlayedHunt playedHunt : playedHunts) {
			var playedBean = new PlayedHuntBean();
			var hunt = playedHunt.getHunt();
			var hb = new HuntBean();
			hb.setIdHunt(hunt.getIdHunt());
			hb.setHuntName(hunt.getHuntName());
			hb.setUsername(hunt.getCreatorName());
			hb.setAvgRating(hunt.getAvgRatingHunt());
			if(hunt.getMap() != null) {
				var map = new MapBean();
				map.setId(hunt.getMap().getId());
				hb.setMap(map);
			}
			
			playedBean.setPlayedHunt(hb);
			playedBean.setReviewRating(playedHunt.getRating());
			playedBean.setFinished(playedHunt.isFinished());
			playedHuntBean.add(playedBean);
		}
		return playedHuntBean;
	}
	
	public void addReview(ReviewBean reviewBean) {
		var reviewDao = new ReviewDao();
		var review = new Review();
		var idHunt = reviewBean.getIdHunt();
		var flag = true;
		
		review.setReviewer(reviewBean.getUsername());
		
		var addedReview = reviewDao.getReviewsByHunt(idHunt);
		
		for(Review rev : addedReview) {
			if(review.getReviewer().equals(rev.getReviewer()) && idHunt == rev.getId()) {
				review = rev;
				flag = false;
			}
		}
		
		if(flag)
			review.setId(-1);
		
		review.setRating(reviewBean.getVote());
		review.setDate(reviewBean.getReviewDate());
		review.setText(reviewBean.getReviewText());
		
		reviewDao.saveReview(review.getId(), review.getReviewer(), idHunt, review.getRating(), review.getText(), review.getDate());
	}
	
	
	public List<ReviewBean> getReviews(HuntBean huntBean) {
		var reviewDao = new ReviewDao();
		
		var hunt = new Hunt();
		List<ReviewBean> reviewBeans = new ArrayList<>();
		
		hunt.setIdHunt(huntBean.getIdHunt());

		var reviews = reviewDao.getReviewsByHunt(hunt);
		
		for(Review review : reviews) {
			var reviewBean = new ReviewBean();
			reviewBean.setIdHunt(huntBean.getIdHunt());
			reviewBean.setNumReview(review.getId());
			reviewBean.setReviewDate(review.getDate());
			reviewBean.setReviewText(review.getText());
			reviewBean.setUsername(review.getReviewer());
			reviewBean.setVote(review.getRating());
			
			reviewBeans.add(reviewBean);
			
		}
		
		return reviewBeans;
	}

	public ReviewBean getReview(HuntBean huntBean) {
		List<ReviewBean> reviews = getReviews(huntBean);
		for(ReviewBean review: reviews) {
			if(review.getUsername().equals(huntBean.getUsername())) {
				return review;
			}
		}
		return null;
	}

	public void setHuntAsPlayed(int idHunt, String player) {
		PlayHuntDao dao = new PlayHuntDao();
		dao.setHuntAsPlayed(idHunt, player, LocalDate.now());
	}
	
	public void finishHunt(int idHunt, String player, List<RiddleBean> riddles) {
		
		PlayHuntDao dao = new PlayHuntDao();
		dao.setHuntAsFinished(idHunt, player);
	}
	
	public boolean isRiddlesCompleted(List<RiddleBean> riddles) {
		for(RiddleBean riddle: riddles) {
			if(!riddle.isCompleted()) {
				return false;
			}
		}
		return true;
	}
}
