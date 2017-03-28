package gft.ddba.calendar.controller;

import gft.ddba.calendar.impl.EventObserver;
import gft.ddba.calendar.impl.EventObserverFactory;
import gft.ddba.calendar.impl.ReactiveStream;
import gft.ddba.calendar.impl.ReactiveStreamFactory;
import gft.ddba.calendar.model.Event;
import gft.ddba.calendar.model.Subscriptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "/app")
public class FileController {

	@Autowired
	private ReactiveStreamFactory reactiveStreamFactory;
	@Autowired
	private EventObserverFactory eventObserverFactory;

//	private Map<String, Subscriber<Event>> subscriber = new ConcurrentHashMap<>();
//	private Map<String, Subscription> subscriptions = new ConcurrentHashMap<>();
//
	@Autowired
	private Subscriptions subscriptions;

	@CrossOrigin
	@RequestMapping(path = "/start/{endPoint}", method = RequestMethod.POST)
	public ResponseEntity startObserving(@RequestBody String path, @PathVariable String endPoint) throws IOException {

		ReactiveStream reactiveStream = reactiveStreamFactory.getReactiveStream(Paths.get(path));
		Observable<Event> observable = reactiveStream.createObservable();

		EventObserver observer = eventObserverFactory.getObserver();

//		subscriptions.put(endPoint,observable.subscribeOn(Schedulers.io()).subscribe(observer));
		subscriptions.subscribe(endPoint, observable);

//		return new ResponseEntity<String>(observer.getEndPoint(), HttpStatus.OK);
		return new ResponseEntity(HttpStatus.OK);
	}


	@CrossOrigin
	@RequestMapping(path = "/stop/{websocket", method = RequestMethod.POST)
	public void stopObserving(String endpoint) throws IOException {
		subscriptions.unsubscribe(endpoint);
		//subscriptions.forEach(Subscription::unsubscribe);
	}



	@CrossOrigin
	@RequestMapping(path = "/obtainEndPoint", method = RequestMethod.GET)
	@ResponseBody
	ResponseEntity<String> obtainEndPoint() throws IOException {
		EventObserver observer = eventObserverFactory.getObserver();

		subscriptions.addSubscriber(observer.getEndPoint(), observer);
//		subscriber.put(observer.getEndPoint(),observer);

		return new ResponseEntity<>(observer.getEndPoint(), HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(path = "/addFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addFilePost(@RequestBody @PathParam("path") String path) throws IOException {
		File file = new File(path);

		if (file.createNewFile())
			return new ResponseEntity<>("\"" + file.getName() + " created\"", HttpStatus.OK);
		else
			return new ResponseEntity<>("\"Can not be created file: " + file.getName() + " because it already exists\"", HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(path = "/addFile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> addFileGet(@PathParam("path") String path) throws IOException {
		File file = new File(path);

		if (file.createNewFile())
			return new ResponseEntity<>("\"" + file.getName() + " created\"", HttpStatus.OK);
		else
			return new ResponseEntity<>("\"Can not be created file:" + file.getName() + " because it already exists\"", HttpStatus.OK);
	}
}