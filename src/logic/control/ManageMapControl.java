package logic.control;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import logic.bean.MapBean;
import logic.bean.UploadFileBean;
import logic.model.dao.MapDao;
import logic.model.entity.Map;

public class ManageMapControl {

	public int requestId() {
		MapDao dao = new MapDao();
		int lastIndex = dao.getLastIndex();
		return lastIndex + 1;
	}
	
	public void save(MapBean bean) {
		int id = bean.getId();
		String name = bean.getName();
		Image image = bean.getImage();
		Canvas canvas = bean.getCanvas();
		
		Map map = new Map();
		map.setName(name);
		map.setImage(image);
		
		MapDao dao = new MapDao();
		dao.saveMap(id, map);
	}
	
	public MapBean loadMap(int id) {
		MapDao dao = new MapDao();
		Map map = dao.getMapById(id);
		
		MapBean bean = new MapBean();
		return bean;
	}
	
	public void uploadFile(UploadFileBean bean) {
		UploadFileControl uploadFileControl = new UploadFileControl();
		uploadFileControl.uploadImage(bean.getFile());
	}
	
	
}
