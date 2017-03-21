package gft.ddba.calendar.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by ddba on 14/03/2017.
 */
@Component
public class TreeObserverFactory {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private final HashMap<String, TreeObserver> observers = new HashMap<>();
	private int endPointAccumulator;

	public TreeObserver getObserver(String path) throws IOException {

		TreeObserver observer = observers.get(path);

		if(observer==null) {
			observer = new TreeObserver(String.valueOf(endPointAccumulator),simpMessagingTemplate);
			endPointAccumulator++;
			observers.put(path,observer);
		}

		return observer;
	}

	public void removeAll() {
		this.observers.clear();
	}


}
