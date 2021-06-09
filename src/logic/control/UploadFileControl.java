package logic.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;



public class UploadFileControl {

	public String uploadFile(File file) {
		String url = "uploads\\" + file.getName();
		
		try {
			saveFile(url, Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return url;
	}
	
	private void saveFile(String location, byte[] data) throws IOException {
		File file = new File(location);
		FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
		outputStream.write(data);
		outputStream.close();
	}
	
}
