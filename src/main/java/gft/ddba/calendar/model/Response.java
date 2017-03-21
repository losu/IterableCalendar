package gft.ddba.calendar.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Response {

	private List<String> files;
//	private FolderModel root;

	@JsonCreator
	public Response(@JsonProperty("files") List<String> files) {
		this.files = files;
	//	this.root = root;
	}



}

