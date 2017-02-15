package gft.ddba.calendar.model;


public class FileModel {
	private  String fileName;

	public FileModel(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "FileModel [fileName=" + fileName + "]";
	}

}
