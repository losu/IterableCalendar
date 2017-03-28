package gft.ddba.calendar.impl;

import gft.ddba.calendar.model.Event;
import gft.ddba.calendar.service.Node;
import gft.ddba.calendar.service.NodeConverter;
import net.jcip.annotations.ThreadSafe;
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

@ThreadSafe
public class ReactiveStream implements AutoCloseable {

	private WatchService service;
	private Path path;
	private Observable<Event> observable;

	public ReactiveStream(Path path) throws IOException {
		service = FileSystems.getDefault().newWatchService();
		this.path = path;
	}

	public Observable<Event> createObservable() throws IOException {
		if (observable == null) {

			Node<Path> pathNode = new PathNode(path);
			Iterable<Path> iterablePath = NodeConverter.convertTreeToIterableStream(pathNode);

			path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
			iterablePath.forEach(o -> {
				try {
					if (o.toFile().isDirectory())
						o.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			observable = Observable.fromCallable(new EventObtainer())
					.flatMap(Observable::from)
					.subscribeOn(Schedulers.io())
					.repeat()
					.doOnUnsubscribe(() -> {
						try {
							close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}).share().startWith(Observable.from(iterablePath).map(
							o -> new Event(o.toString(), o.getFileName().toString(), "")));
		}
		return observable;
	}

	private class EventObtainer implements Callable<List<Event>> {

		@Override
		public List<Event> call() throws InterruptedException {
			WatchKey key = service.take();
			List<WatchEvent<?>> watchEvents = key.pollEvents();
			List<Event> events = convertWatchEvent(watchEvents, key);
			registerNewDirectories(events);
			key.reset();
			events.forEach(System.out::println);
			return events;
		}
	}

	private List<Event> convertWatchEvent(List<WatchEvent<?>> watchEvents, WatchKey key) {
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
