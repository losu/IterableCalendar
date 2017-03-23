package gft.ddba.calendar.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ddba on 14/03/2017.
 */
@Component
@ThreadSafe
public class EventObserverFactory {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private final HashMap<String, EventObserver> observers = new HashMap<>();
	private int endPointAccumulator;

	public EventObserver getObserver(String path) throws IOException {

		EventObserver observer = observers.get(path);

		if(observer==null) {
			observer = new EventObserver(String.valueOf(endPointAccumulator),simpMessagingTemplate);
			endPointAccumulator++;
			observers.put(path,observer);
		}

		return observer;
	}

	public void removeAll() {
		this.observers.clear();
	}


}
