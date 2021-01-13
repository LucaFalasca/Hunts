package logic.control;

import logic.bean.ManageHuntBean;
import logic.model.entity.Hunt;

public class ManageHuntControl {
	
	public void addHunt(ManageHuntBean huntBean) {
		Hunt hunt = new Hunt();
		
		hunt.setHuntName(huntBean.getHuntName());
		
		hunt.setRiddleList(huntBean.getRiddleList());
		
		hunt.setSolutionList(huntBean.getSolutionList());
		
		hunt.setClueList(huntBean.getClueList());
		
		hunt.setObjectList(huntBean.getObjectList());
		
		
	}
	
	
}
