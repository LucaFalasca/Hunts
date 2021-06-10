package logic.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import logic.exception.LoadFileFailed;



public class UploadFileControl {

	public String uploadFile(File file) throws LoadFileFailed {
		String url = "uploads\\" + file.getName();
		
		try {
			saveFile(url, Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			throw new LoadFileFailed();
		}
		
		return url;
	}
	
	private void saveFile(String location, byte[] data) throws LoadFileFailed {
		var file = new File(location);
		
		try (var outputStream = new FileOutputStream(file.getAbsolutePath())){
			outputStream.write(data);
		} catch (IOException e) {
			throw new LoadFileFailed();
		} 
	}
	
}
