package logic.control;

import javafx.scene.image.Image;
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
