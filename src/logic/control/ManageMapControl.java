package logic.control;

import logic.bean.UploadFileBean;

public class ManageMapControl {

	
	public void renameMap() {
		
	}
	
	public void addZone() {
		
	}
	
	public void addMarker() {
		
	}
	
	public void deleteMarker() {
		
	}
	
	public void loadMap() {
		
	}
	
	public void loadMapGoogle() {
		
	}
	
	public void createMap() {
		
	}
	
	public void uploadFile(UploadFileBean bean) {
		UploadFileControl uploadFileControl = new UploadFileControl();
		uploadFileControl.uploadImage(bean.getFile());
	}
	
	
}
