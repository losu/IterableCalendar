package gft.ddba.calendar.controller;

import gft.ddba.calendar.impl.ReactiveStreamFactory;
import gft.ddba.calendar.impl.TreeObserver;
import gft.ddba.calendar.impl.TreeObserverFactory;
import gft.ddba.calendar.model.Event;
import gft.ddba.calendar.service.ReactiveStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/app")
public class FileDisplayerController {

	@Autowired
	private ReactiveStreamFactory reactiveStreamFactory;

	@Autowired
	private TreeObserverFactory treeObserverFactory;

	private List<Subscription> subscriptions = new ArrayList<>();

	@CrossOrigin
	@RequestMapping(path = "/start", method = RequestMethod.POST)
	public String startObserving(@RequestBody String path) throws IOException {
		ReactiveStream treeReactiveStream = reactiveStreamFactory.getReactiveStream(Paths.get(path));
		Observable<Event> observable = treeReactiveStream.createObservable();

		TreeObserver observer = treeObserverFactory.getObserver(path);

		subscriptions.add(observable.subscribeOn(Schedulers.io()).subscribe(observer));
		System.out.println("Start : "+observer.getEndPoint());
		return observer.getEndPoint();
	}

	@CrossOrigin
	@RequestMapping(path = "/stop", method = RequestMethod.POST)
	public void stopObserving() throws IOException {
		reactiveStreamFactory.close();
		reactiveStreamFactory.removeAll();
		treeObserverFactory.removeAll();
		subscriptions.forEach(Subscription::unsubscribe);
	}

	@CrossOrigin
	@RequestMapping(path = "/obtainEndPoint", method = RequestMethod.GET)
	@ResponseBody
	ResponseEntity<String> obtainEndPoint(String path) throws IOException {
		TreeObserver observer = treeObserverFactory.getObserver(path);


		return new ResponseEntity<>(observer.getEndPoint(), HttpStatus.OK);
	}


}



//
//	// WebSocketSession session;
//	@Autowired
//	private SimpMessagingTemplate template;
//
//	@Scheduled(fixedDelay = 1000)
//	public void send() {
//
//		List<String> pathOfFiles = new ArrayList<>();
//		pathOfFiles = scanDirctory(new File("C:/Users/ddba/Desktop/Test"), pathOfFiles);
//
//		// fileController.getFileObservable();
//
//		Response lista = new Response(pathOfFiles);
//		lista.getFiles().forEach(System.out::println);
//		template.convertAndSend("/topic/greetings", lista);
//
//	}
//
//	public List<String> scanDirctory(File file, List<String> paths) {
//		List<String> lista = new ArrayList<>();
//		try {
//			lista = Files.walk(Paths.get("C:/Users/ddba/Desktop/Test/")).map(el -> el.getFileName().toString())
//					.collect(Collectors.toList());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		File[] folders = null;
////
////		folders = file.listFiles();
////
////		for (File f : folders) {
////			if (f.isDirectory()) {
////				paths.add(f.getName());
////				// System.out.println(f.getName());
////				scanDirctory(f, paths);
////			} else {
////				// System.out.println(f.getName());
////				paths.add(f.getName());
////			}
////		}
//		return lista;
//	}
////
////	@CrossOrigin
////	@RequestMapping(path = "/obtainEndPoint", method = RequestMethod.GET)
////	@ResponseBody
////	ResponseEntity<String> obtainEndPoint() throws IOException {
////		FileObserver observer = treeObserverFactory.getObserver();
////
////		subscriptions.addSubscriber(observer.getEndPoint(), observer);
////
////		return new ResponseEntity<>(observer.getEndPoint(), HttpStatus.OK);
////	}
////
//
//	class FileObserver extends Subscriber<Event>{
//
//		@Autowired
//		private SimpMessagingTemplate simpMessagingTemplate;
//
//
//		@Override
//		public void onCompleted() {
//
//		}
//
//		@Override
//		public void onError(Throwable e) {
//
//		}
//
//		@Override
//		public void onNext(Event event) {
//			simpMessagingTemplate.convertAndSend("/event/get");
//		}
//	}
//}