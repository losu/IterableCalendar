package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.PathNode;
import rx.Observable;
import rx.Observer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class ChangeWatcher extends Observable<Path> implements AutoCloseable {

	private static Set<Observer> observers = new HashSet<>();
	private WatchService watchService;
	private ExecutorService executorService;


	public static ChangeWatcher watchChanges(Path root) throws IOException, InterruptedException {
		return new ChangeWatcher(root, observer -> observers.add(observer));
	}

	private ChangeWatcher(Path root, OnSubscribe<Path> subscribe) throws IOException {
		super(subscribe);

		watchService = root.getFileSystem().newWatchService();

		Node<Path> pathNodeRoot = new PathNode(root);

		NodeConverter.convertTreeToObservableStream(pathNodeRoot).subscribe((node) -> {
					if (Files.isDirectory(node)) {
						try {
							node.register(watchService, ENTRY_CREATE);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		);

		executorService = Executors.newSingleThreadExecutor();

		Runnable c = () -> {
			while (!Thread.interrupted()) {

				WatchKey key = null;
				try {
					key = watchService.take();
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
				} catch (InterruptedException e) {
					observers.forEach(Observer::onCompleted);
				}
				key.reset();
			}


		};
		executorService.submit(c);
	}


	@Override
	public void close() throws Exception {
//		observers.forEach(Observer::onCompleted);
		executorService.shutdownNow();
		//assert executorService.awaitTermination(1, TimeUnit.SECONDS);

		watchService.close();
	}

}
