package logic.control;

import java.util.ArrayList;
import java.util.List;

import logic.bean.AnswerBean;
import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.model.dao.HuntDao;
import logic.model.entity.Hunt;

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

	
}
