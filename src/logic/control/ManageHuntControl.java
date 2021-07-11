package logic.control;

import java.util.ArrayList;
import java.util.List;

import logic.bean.HuntBean;
import logic.bean.MapBean;
import logic.bean.ObjectBean;
import logic.bean.RiddleBean;
import logic.bean.ZoneBean;
import logic.model.dao.HuntDao;
import logic.model.entity.RealObject;
import logic.model.entity.Riddle;
import logic.model.entity.Zone;
import logic.model.entity.Hunt;
import logic.model.entity.Map;

public class ManageHuntControl {
	
	public int saveHunt(HuntBean huntBean) {
		var huntDao = new HuntDao();
		
		var hunt = new Hunt();
		
		var riddle = new ArrayList<Riddle>();
		
		var object = new ArrayList<RealObject>();
		
		hunt.setHuntName(huntBean.getHuntName());
		hunt.setIdHunt(huntBean.getIdHunt());
		hunt.setCreatorName(huntBean.getUsername());
		
		for(var i = 0; i < huntBean.getRiddle().size(); i++) {
			var rid = new Riddle();
			rid.setnRiddle(i);
			rid.setRiddleText(huntBean.getRiddle().get(i).getRiddle());
			rid.setSolutionText(huntBean.getRiddle().get(i).getSolution());
			for(var j = 0; j < huntBean.getRiddle().get(i).getClue().size(); j++)
				rid.setClueListElement(j, huntBean.getRiddle().get(i).getClueElement(j));
			rid.setReward(huntBean.getRiddle().get(i).getObjectName());
			rid.setZone(huntBean.getRiddle().get(i).getZoneName());
		
			riddle.add(rid);
		}
		
		hunt.setRiddleList(riddle);
		
		for(var i = 0; i < huntBean.getObject().size(); i++) {
			var obj = new RealObject();
			
			obj.setName(huntBean.getObject().get(i).getName());
			
			obj.setDescription(huntBean.getObject().get(i).getDescription());
			
			obj.setPath(huntBean.getObject().get(i).getPath());
			
			object.add(obj);
		}
		
		hunt.setObjectList(object);
		
		var map = new Map();
		if(huntBean.getMap() != null) {
			map.setId(huntBean.getMap().getId());
			map.setName(huntBean.getMap().getName());
			map.setImagePath(huntBean.getMap().getImage());
			
		} else {
			map.setId(-1);
		}
		
		hunt.setMap(map);
		
		hunt.setVisible(huntBean.getPrivate());
		
		return huntDao.saveHunt(hunt);
	}

	public HuntBean getHunt(int idHunt, String username) {
		var huntDao = new HuntDao();
		var huntBean = new HuntBean();
		List<Riddle> riddleList;
		List<RealObject> objectList;
		List<Zone> zoneList;
		
		List<RiddleBean> riddleBean = new ArrayList<>();
		List<ObjectBean> objectBean = new ArrayList<>();
		List<ZoneBean> zoneBean = new ArrayList<>();
		
		var hunt = huntDao.getHuntById(idHunt, username);
		
		huntBean.setIdHunt(hunt.getIdHunt());
		huntBean.setHuntName(hunt.getHuntName());
		huntBean.setUsername(username);
		
		riddleList = hunt.getRiddleList();
		for(var i = 0; i < riddleList.size(); i++) {
			var rb = new RiddleBean();
			rb.setNumRiddle(i);
			rb.setRiddle(riddleList.get(i).getRiddleText());
			rb.setSolution(riddleList.get(i).getSolutionText());
			for(var j = 0; j < riddleList.get(i).getClueList().size(); j++ ) {
				rb.setClueElement(j, riddleList.get(i).getClueListElement(j));
			}
			rb.setObjectName(riddleList.get(i).getReward());
			rb.setZoneName(riddleList.get(i).getZone());
		
			riddleBean.add(rb);
		}
		
		huntBean.setRiddle(riddleBean);
		
		objectList = hunt.getObjectList();
		
		for(var i = 0; i < objectList.size(); i++) {
			var ob = new ObjectBean(i, objectList.get(i).getName(), objectList.get(i).getDescription(), objectList.get(i).getPath());
			
			
			objectBean.add(ob);
		}
		
		huntBean.setObject(objectBean);
		
		var map = new MapBean();
		if(hunt.getMap() != null) {
			map.setId(hunt.getMap().getId());
			map.setName(hunt.getMap().getName());
			map.setImage(hunt.getMap().getImagePath());
			zoneList = hunt.getMap().getZones();
			for(var i = 0; i < zoneList.size(); i++) {
				var zone = new ZoneBean(zoneList.get(i).getName(), zoneList.get(i).getStartX(), zoneList.get(i).getStartY(), 
										zoneList.get(i).getEndX(), zoneList.get(i).getEndY(), zoneList.get(i).getType().toString());
				
				zoneBean.add(zone);
			}
			map.setZones(zoneBean);
		} else {
			map.setId(-1);
		}

		huntBean.setMap(map);
		huntBean.setPrivate(hunt.isVisible());
		
		return huntBean;
	}

	public List<HuntBean> getAllHunts(String username) {
		List<HuntBean> huntBeans = new ArrayList<>();
		var huntDao = new HuntDao();
		List<Hunt> hunts;
		
		if(username == null)
			hunts = huntDao.getHuntList();
		else {
			hunts = huntDao.getHuntList(username);
		}
		
		for(var i = 0; i < hunts.size(); i++) {
			var hunt = hunts.get(i);
			var huntBean = new HuntBean();
			
			huntBean.setHuntName(hunt.getHuntName());
			huntBean.setIdHunt(hunt.getIdHunt());
			huntBean.setUsername(hunt.getCreatorName());
			huntBean.setPrivate(hunt.isVisible());
			
			huntBeans.add(huntBean);
			
		}
		return huntBeans;
	}
	
	public List<HuntBean> getAllHunts(){
		return getAllHunts(null);
	}
	
	public void deleteHunt(HuntBean huntBean) {
		var huntDao = new HuntDao();
		var hunt = new Hunt();
		hunt.setCreatorName(huntBean.getHuntName());
		hunt.setIdHunt(huntBean.getIdHunt());
		huntDao.removeHunt(hunt);
	}
	
}
