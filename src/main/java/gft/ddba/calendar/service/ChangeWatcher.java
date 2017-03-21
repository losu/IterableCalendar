package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.PathNode;
import rx.Observable;
import rx.Observer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

import static java.nio.file.StandardWatchEventKinds.*;

public class ChangeWatcher extends Observable<Path> implements AutoCloseable {

	private WatchService watchService;
	private ExecutorService executorService;

	public static ChangeWatcher watchChanges(Path root) throws IOException, InterruptedException {
		CopyOnWriteArrayList<Observer> observers = new CopyOnWriteArrayList(new ArrayList<>());
		OnSubscribe<Path> onSubscribe = observers::add;
		return new ChangeWatcher(root, observers, onSubscribe);
	}

	private ChangeWatcher(Path root, CopyOnWriteArrayList<Observer> observers, OnSubscribe<Path> subscribe) throws IOException {
		super(subscribe);

		watchService = root.getFileSystem().newWatchService();
		Node<Path> pathNodeRoot = new PathNode(root);

		NodeConverter.convertTreeToObservableStream(pathNodeRoot).subscribe((node) -> {
					if (Files.isDirectory(node)) {
						try {
							node.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		);

		//	executorService = Executors.newSingleThreadExecutor();

		//	Runnable c = () -> {
		new Thread() {
			public void run() {

				while (true) {
					WatchKey key ;
					try {
						key = watchService.take();
					} catch (InterruptedException e) {
						observers.forEach(o -> o.onError(e));
						break;
					}
					if (Optional.ofNullable(key).isPresent()) {
						WatchKey finalKey = key;
						key.pollEvents().stream()
								.filter(event -> event.kind() == ENTRY_CREATE)
								.forEach(event -> {
									Path currentDirectoryPath = (Path) finalKey.watchable();
									Path fullNewPath = currentDirectoryPath.resolve((Path) event.context());
									if (Files.isDirectory(fullNewPath)) {
										try {
											fullNewPath.register(watchService, ENTRY_CREATE);
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									observers.forEach(o -> o.onNext(fullNewPath));
								});
					}
					key.reset();
				}
				observers.forEach(Observer::onCompleted);
			}
		}.start();
	}
	//	executorService.submit(c);

	@Override
	public void close() throws Exception {
	//	observers.forEach(Observer::onCompleted);
		//executorService.shutdownNow();
		//assert executorService.awaitTermination(1, TimeUnit.SECONDS);

		watchService.close();
	}
}
