package logic.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import logic.exception.LoadFileFailed;



public class UploadFileControl {

	public String uploadFile(File file) throws LoadFileFailed {
		String url;
		
		try {
			url = "uploads\\" + file.getName();
			saveFile(url, Files.readAllBytes(file.toPath()));
			saveFile("WebContent\\" + url, Files.readAllBytes(file.toPath()));
		} catch (IOException | NullPointerException e ) {
			throw new LoadFileFailed();
		}
		
		return url;
	}
	
	private void saveFile(String location, byte[] data) throws LoadFileFailed {
		var file = new File(location);
		
		try (var outputStream = new FileOutputStream(file.getAbsolutePath())){
			outputStream.write(data);
		} catch (IOException e) {

			e.printStackTrace();
			throw new LoadFileFailed();
		} 
	}
	
}
