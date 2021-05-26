package logic.control;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import logic.bean.MapBean;
import logic.bean.UploadFileBean;
import logic.bean.ZoneBean;
import logic.enumeration.Type;
import logic.model.dao.MapDao;
import logic.model.entity.Map;
import logic.model.entity.Zone;

public class ManageMapControl {
	
	public void save(String username, MapBean bean) {
		int id = bean.getId();
		String name = bean.getName();
		Image image = bean.getImage();
		List<ZoneBean> zonesBean = bean.getZones();
		
		Map map = new Map(name);
		
		if(id != -1)				map.setId(id);
		if(image != null) 			map.setImage(image);
		if(!zonesBean.isEmpty()) {
			List<Zone> zones = new ArrayList<>();
			for(ZoneBean zb : zonesBean) {
				Type type = null;
				switch(zb.getType()) {
					case "RectangleState":
						type = Type.RECTANGLE;
						break;
					case "OvalState":
						type = Type.OVAL;
						break;
					default:
						type = Type.RECTANGLE;	
				}
				zones.add(new Zone(zb.getName(),
						zb.getStartX(),
						zb.getStartY(),
						zb.getEndX(),
						zb.getEndX(),
						type));
			}
			
			map.setZones(zones);
		}
		
		MapDao dao = new MapDao();
		dao.saveMap(username, map);
		
		
	}
	
	public MapBean loadMap(int id) {
		MapDao dao = new MapDao();
		Map map = dao.getMapById(id);
		
		MapBean bean = new MapBean();
		bean.setId(id);
		bean.setName(map.getName());
		if(map.getImage() != null) {
			bean.setImage(map.getImage());
		}
		if(!map.getZones().isEmpty()){
			List<ZoneBean> zonesBean = new ArrayList<>();
			for(Zone zone : map.getZones()) {
				zonesBean.add(new ZoneBean(zone.getName(),
						zone.getStartX(),
						zone.getStartY(),
						zone.getEndX(),
						zone.getEndY(),
						zone.getType().toString()));
			}
			bean.setZones(zonesBean);
		}
		return bean;
	}
	
	public List<MapBean> getAllMaps(String username){
		MapDao dao = new MapDao();
		List<Map> maps = dao.getMapList(username);
		
		List<MapBean> beans = new ArrayList<>();
		
		for(Map map : maps) {
			MapBean bean = new MapBean();
			bean.setName(map.getName());
			bean.setImage(map.getImage());
			beans.add(bean);
		}
		
		return beans;
	}
	
	public void uploadFile(UploadFileBean bean) {
		UploadFileControl uploadFileControl = new UploadFileControl();
		uploadFileControl.uploadImage(bean.getFile());
	}
	
	
}
