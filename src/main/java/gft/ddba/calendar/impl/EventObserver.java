package gft.ddba.calendar.impl;

import gft.ddba.calendar.model.Event;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import rx.Subscriber;

public class EventObserver extends Subscriber<Event> {

	private SimpMessagingTemplate simpMessagingTemplate;

	public String getEndPoint() {
		return endPoint;
	}

	private String endPoint;

	public EventObserver(String endPoint, SimpMessagingTemplate simpMessagingTemplate) {
		this.endPoint = endPoint;
		this.simpMessagingTemplate=simpMessagingTemplate;
	}

	@Override
	public void onCompleted() {
		System.out.print("Completed");
	}

	@Override
	public void onError(Throwable throwable) {
		throwable.printStackTrace();
	}

	@Override
	public void onNext(Event event) {
		System.out.println(event.getEventType() + event.getPath() + event.getFileName());
		System.out.println("/events/get/" + endPoint);
		simpMessagingTemplate.convertAndSend("/events/get/" + endPoint, event);
	}
}