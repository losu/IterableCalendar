package gft.ddba.calendar.model;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public class Event {
	private String path;
	private String fileName;
	private String eventType;

	public Event(){

	}

	public Event(String path, String fileName, String eventType) {
		this.path = path;
		this.fileName = fileName;
		this.eventType = eventType;
	}

	public Event(WatchEvent event,Path path) {
		this.path = path.resolve((Path) event.context()).toString();
		this.fileName=event.context().toString();
		this.eventType=event.kind().name();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("Event{");
		sb.append("path='").append(path).append('\'');
		sb.append(", fileName='").append(fileName).append('\'');
		sb.append(", eventType='").append(eventType).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
