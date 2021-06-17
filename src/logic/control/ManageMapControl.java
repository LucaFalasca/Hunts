package logic.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import logic.bean.MapBean;
import logic.bean.ZoneBean;
import logic.enumeration.Type;
import logic.exception.LoadFileFailed;
import logic.model.dao.MapDao;
import logic.model.entity.Map;
import logic.model.entity.Zone;

public class ManageMapControl {
	
	public int save(String username, MapBean bean) {
		int id = bean.getId();
		String name = bean.getName();
		String imagePath = bean.getImage();
		List<ZoneBean> zonesBean = bean.getZones();
		
		var map = new Map();
		map.setName(name);
		if(id != -1)				map.setId(id);
		if(imagePath != null) 			map.setImagePath(imagePath);
		if(!zonesBean.isEmpty()) {
			List<Zone> zones = new ArrayList<>();
			for(ZoneBean zb : zonesBean) {
				Type type = null;
				switch(zb.getShape()) {
					case "RectangleState":
						type = Type.RECTANGLE;
						break;
					case "OvalState":
						type = Type.OVAL;
						break;
					default:
						type = Type.RECTANGLE;	
				}
				zones.add(new Zone(zb.getNameZone(),
						zb.getX1(),
						zb.getY1(),
						zb.getX2(),
						zb.getX2(),
						type));
			}
			
			map.setZones(zones);
		}
		var dao = new MapDao();
		return dao.saveMap(username, map);
	}
	
	public MapBean getMapById(String username, int id) {
		var dao = new MapDao();
		var map = dao.getMapById(username, id);
		
		var bean = new MapBean();
		bean.setId(id);
		bean.setName(map.getName());
		if(map.getImagePath() != null) {
			bean.setImage(map.getImagePath());
		}
		if(!(map.getZones() == null  || map.getZones().isEmpty())){
			List<ZoneBean> zonesBean = new ArrayList<>();
			for(Zone zone : map.getZones()) {
				zonesBean.add(new ZoneBean(zone.getName(),
						zone.getStartX(),
						zone.getStartY(),
						zone.getEndX(),
						zone.getEndY(),
						zone.getType().name()));
			}
			bean.setZones(zonesBean);
		}
		return bean;
	}
	
	public List<MapBean> getAllMaps(String username){
		var dao = new MapDao();
		List<Map> maps = dao.getMapList(username);
		
		List<MapBean> beans = new ArrayList<>();
		
		for(Map map : maps) {
			var bean = new MapBean();
			bean.setId(map.getId());
			bean.setName(map.getName());
			bean.setCreatorName(username);
			bean.setImage(map.getImagePath());
			beans.add(bean);
		}
		
		return beans;
	}

	
	public String uploadFile(File file) throws LoadFileFailed {
		var uploadFileControl = new UploadFileControl();
		return uploadFileControl.uploadFile(file);
	}
	
	public void deleteMap(int id, String username) {
		var dao = new MapDao();
		dao.deleteMap(id, username);
	}
	
}
