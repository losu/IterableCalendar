package gft.ddba.calendar.impl;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@ThreadSafe
public class EventObserverFactory {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private final Map<String, EventObserver> observers = new ConcurrentHashMap<>();
	private AtomicInteger endPointAccumulator= new AtomicInteger(0);

	public EventObserver getObserver() throws IOException {
		return new EventObserver(String.valueOf(endPointAccumulator.incrementAndGet()), simpMessagingTemplate);

	}

//	public EventObserver getObserver(String path) throws IOException {
//
//		EventObserver observer = observers.get(path);
//
//		if(observer==null) {
//			observer = new EventObserver(String.valueOf(endPointAccumulator),simpMessagingTemplate);
//			endPointAccumulator.incrementAndGet();
//			observers.put(path,observer);
//		}
//
//		return observer;
//	}



}
