package logic.control;

import java.util.ArrayList;
import java.util.List;

import logic.bean.AnswerBean;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.PlayedHuntBean;
import logic.bean.ReviewBean;
import logic.bean.ZoneBean;
import logic.model.dao.HuntDao;
import logic.model.dao.PlayHuntDao;
import logic.model.dao.ReviewDao;
import logic.model.entity.Hunt;
import logic.model.entity.PlayedHunt;
import logic.model.entity.Review;
import logic.model.entity.Zone;

public class PlayHuntControl {

	public boolean answer(AnswerBean bean) {
		String trueAnswer = bean.getRiddleAnswer();
		String userAnswer = bean.getUserAnswer();
		
		if(trueAnswer.toLowerCase().equals(userAnswer.toLowerCase())) return true;
		
		return false;
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
		
		review.setId(-1);
		review.setRating(reviewBean.getVote());
		review.setDate(reviewBean.getReviewDate());
		review.setReviewer(reviewBean.getUsername());
		review.setText(reviewBean.getReviewText());
		
		reviewDao.saveReview(review.getId(), review.getReviewer(), idHunt, review.getRating(), review.getText(), review.getDate());
	}

	
}
