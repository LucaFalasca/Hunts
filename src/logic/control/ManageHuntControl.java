package logic.control;

import java.util.List;

import logic.bean.HuntBean;
import logic.model.dao.HuntDao;
import logic.model.entity.Object;
import logic.bean.ObjectBean;
import logic.model.dao.ObjectDao;
import logic.model.entity.Riddle;
import logic.model.entity.Zone;
import logic.model.dao.RiddleDao;
import logic.model.entity.Clue;
import logic.model.entity.Hunt;

public class ManageHuntControl {
	
	public void addRiddle(ObjectBean objectBean, HuntBean huntBean, String nameZone) {
	
		RiddleDao riddleDao = new RiddleDao();
		
		Object object = new Object();
		
		Hunt hunt = new Hunt();
		
		Zone zone = new Zone();
		
		Riddle riddle = new Riddle();
		
		Clue clue = new Clue();
		
		hunt.setIdHunt(huntBean.getIdHunt());
		
		riddle.setnRiddle(huntBean.getnRiddle());
		
		riddle.setRiddleText(huntBean.getRiddleText());
		
		riddle.setSolutionText(huntBean.getSolutionText());
		
		riddle.setClueList(huntBean.getClueList());
		
		riddle.setReward(objectBean.getObject());
		
		object.setName(objectBean.getObject());
		
		zone.setName(nameZone);
		
		riddleDao.addRiddle(riddle, object, hunt, zone);
		
		for(int i = 0; i < huntBean.getClueList().size(); i++) {
			clue.setClueIndex(i);
			clue.setText(huntBean.getClueListElement(i));
			riddleDao.addClueToRiddle(clue, hunt, riddle);
		}
	}
	
	public HuntBean modifyRiddle(HuntBean huntBean) {
		
		Hunt hunt = new Hunt();
		
		RiddleDao riddleDao = new RiddleDao();
		
		hunt.setIdHunt(huntBean.getIdHunt());
		
		Riddle riddle = new Riddle();
		
		riddle.setnRiddle(huntBean.getnRiddle());
		
		riddle = riddleDao.getRiddleById(hunt, riddle);
		
		huntBean.setnRiddle(riddle.getnRiddle());
		
		huntBean.setRiddleText(riddle.getRiddleText());
		
		huntBean.setSolutionText(riddle.getSolutionText());
		
		huntBean.setClueList(riddle.getClueList());
		
		return huntBean;
		
	}
	
	public void deleteRiddle(HuntBean huntBean) {
		
		Hunt hunt = new Hunt();
		
		Riddle riddle = new Riddle();
		
		hunt.setIdHunt(huntBean.getIdHunt());
		
		riddle.setnRiddle(huntBean.getnRiddle());
		
		RiddleDao riddleDao = new RiddleDao();
		
		riddleDao.deleteRiddleById(hunt, riddle);
	}
	
	public void addObject(ObjectBean objectBean, HuntBean huntBean) {
		
		Object object = new Object();
		
		Hunt hunt = new Hunt();
		
		Zone zone = new Zone();
		
		hunt.setIdHunt(huntBean.getIdHunt());
		
		object.setName(objectBean.getObject());
		
		object.setDescription(objectBean.getDescription());
		
		object.setPath(objectBean.getPath());
		
		zone.setName(huntBean.getNameZone());
		
		ObjectDao objectDao = new ObjectDao();
		
		objectDao.addObject(object, hunt, zone);
	
	}

	public HuntBean removeObject(ObjectBean objectBean, HuntBean huntBean) {
		
		ObjectDao objectDao = new ObjectDao();
		
		Object object = new Object();
		
		Hunt hunt = new Hunt();
		
		object.setName(objectBean.getObject());
		
		hunt.setIdHunt(huntBean.getIdHunt());
		
		List<Integer> indexList = objectDao.getObjectInRiddle(object, hunt);
		
		huntBean.setIndexList(indexList);
		
		return huntBean;
	}
	
	public ObjectBean modifyObject(ObjectBean objectBean, HuntBean huntBean) {
				
		ObjectDao objectDao = new ObjectDao();
		
		Object object = new Object();
		
		object = objectDao.getObjectByName(object, huntBean.getIdHunt());
		
		objectBean.setDescription(object.getDescription());
		
		objectBean.setPath(object.getPath());
		
		return objectBean;

	}

	public boolean getHuntByName() {
		return true;
	}
	
	public boolean addHunt() {		
		return true;
	}
	
	public boolean removeHunt() {
		return true;
	}

	public int createHunt() {
		
		HuntDao huntDao = new HuntDao();
		
		return huntDao.createHunt();
		
	}
	
}
