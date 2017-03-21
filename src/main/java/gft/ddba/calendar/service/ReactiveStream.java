package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.PathNode;
import gft.ddba.calendar.model.Event;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ReactiveStream implements AutoCloseable {


	private WatchService service;
	private Path path;
	private Observable<Event> observable;

	public ReactiveStream(Path path) throws IOException {
		service = FileSystems.getDefault().newWatchService();
		this.path = path;
	}

	public Observable<Event> createObservable() throws IOException {
		if(observable == null) {

			Node<Path> pathNode = new PathNode(path);
			Iterable<Path> iterablePath = NodeConverter.convertTreeToIterableStream(pathNode);

			//IterableTree<FileTree> iterableTree = new IterableTree<>(new FileTree(path));
			path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
			iterablePath.forEach(o -> {
				try {
					if (o.toFile().isDirectory())
						o.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			observable = Observable.fromCallable(new EventObtainer()).flatMap(Observable::from).subscribeOn(Schedulers.io()).repeat();
			System.out.println("Observable ");
		}
		return observable;
	}

	private class EventObtainer implements Callable<List<Event>> {

		@Override
		public List<Event> call() throws InterruptedException {
			System.out.println("przed call");
			WatchKey key = service.take();
			List<WatchEvent<?>> watchEvents = key.pollEvents();
			List<Event> events = convertWatchEvent(watchEvents,key);
			registerNewDirectories(events);
			key.reset();
			System.out.println("po call");
			events.forEach(System.out::println);
			return events;
		}
	}

	private List<Event> convertWatchEvent(List<WatchEvent<?>> watchEvents, WatchKey key){
		return watchEvents.stream().map(watchEvent -> new Event(watchEvent, (Path) key.watchable())).collect(Collectors.toList());
	}

	private void registerNewDirectories(List<Event> events) {
		for (Event event : events) {
			Path fullPath = Paths.get(event.getPath());
			if (event.getEventType().equals("ENTRY_CREATE")) {
				if (fullPath.toFile().isDirectory()) {
					try {
						fullPath.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void close() throws IOException {
		service.close();
	}
}


////	private enum State {
////		NEW, RUNNING, CLOSED, INITIALIZING
////	}
//
//	private WatchService watchService;
//	private Path path;
//	private Observable<Event> observable;
//	//private CustomClosable onClosing;
//	//private AtomicReference<State> state = new AtomicReference<>(State.NEW);
//
//	public ReactiveStream(Path path) {
//		this.path=path;
//	}
//// public ReactiveStream(Path path) {
////		this(path, () -> {
////
////		});
////	}
//
////	public ReactiveStream(Path path, CustomClosable onClosing) {
////		this.path = path;
////		this.onClosing = onClosing;
////	}
//
//	public void initialize() throws IOException {
//		//if (!state.compareAndSet(State.NEW, State.INITIALIZING)) return;
//
//		watchService = FileSystems.getDefault().newWatchService();
//
//		Node<Path> pathNode = new PathNode(path);
//		Iterable<Path> iterablePath = NodeConverter.convertTreeToIterableStream(pathNode);
//		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
//
//		iterablePath.forEach(el -> {
//			try {
//				if (el.toFile().isDirectory()) {
//					el.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//
//		observable = Observable.fromCallable(new EventObtaining())
//				.flatMap(Observable::from)
//				.subscribeOn(Schedulers.io())
//				.repeat()
//				.doOnUnsubscribe(() -> {
//					try {
//						close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				})
//			.share()
//				.startWith(Observable.from(iterablePath).
//						map(o -> new Event(o.toString(), o.getFileName().toString(), "Existing")));
//
//	//	state.set(State.RUNNING);
//
//	}
//
//	public Observable<Event> getObservable() {
//	//	if (state.get() == State.CLOSED)
//	//		return Observable.error(new ClosedWatchServiceException());
//
//		return observable;
//	}
//
//	private class EventObtaining implements Callable<List<Event>> {
//		@Override
//		public List<Event> call() throws InterruptedException {
//			WatchKey key = watchService.take();
//			List<WatchEvent<?>> watchEvents = key.pollEvents();
//			List<Event> events = convertWatchEvent(watchEvents, key);
//			registerNewDirectories(events);
//			key.reset();
//			return events;
//		}
//	}
//
//	private static List<Event> convertWatchEvent(List<WatchEvent<?>> watchEvents, WatchKey watchKey) {
//		return watchEvents.stream()
//				.map(watchEvent -> new Event(watchEvent, (Path) watchKey.watchable()))
//				.collect(Collectors.toList());
//	}
//
//	private void registerNewDirectories(List<Event> events) {
//		for (Event event : events) {
//			Path fullPath = Paths.get(event.getPath());
//			if (fullPath.toFile().isDirectory()) {
//				try {
//					fullPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (event.getEventType().equals("ENTRY_CREATE")) {
//			}
//		}
//	}
//
//	@Override
//	public void close() throws IOException {
//		//if (state.compareAndSet(State.RUNNING, State.CLOSED)) {
//			//onClosing.onClosing();
//			watchService.close();
//		//}
//	}
//
////	@FunctionalInterface
////	public interface CustomClosable {
////		void onClosing();
////	}
//}
