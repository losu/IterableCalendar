package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.FileNode;
import gft.ddba.calendar.impl.FileNodeHandler;
import gft.ddba.calendar.impl.PathHandler;
import gft.ddba.calendar.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class ChangeWatcher extends Observable<Path> {

	@Autowired
	private FileNodeHandler fileNodeHandler;
	@Autowired
	private NodeConverter nodeConverter;

	private final WatchService watcher;
	private final Map<WatchKey, Path> keys;
	private Observable<FileModel> observableFile;



	public ChangeWatcher(Path dir, OnSubscribe<Path> observable) throws IOException {
		super(observable);
		this.watcher = FileSystems.getDefault().newWatchService(); //new service registering
		this.keys = new HashMap<>();

		walkAndRegisterDirectories(dir);
	}

	/**
	 * Register the given directory with the WatchService; This function will be called by FileVisitor
	 */
	private void registerDirectory(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the WatchService.
	 */
	private void walkAndRegisterDirectories(final Path start) throws IOException {
		// register directory and sub-directories
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				registerDirectory(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Process all events for keys queued to the watcher
	 */
	//@RequestMapping(value = "/process", method = RequestMethod.GET)
	public void processEvents() {
		PathHandler handler = new PathHandler();

		Node<FileModel> rootDirectories = new FileNode<>();
		fileNodeHandler.scan(new File("C:\\Users\\ddba\\Desktop\\Test"), rootDirectories);
		Iterable<FileModel>iterableNode=nodeConverter.convertTreeToIterableStream(rootDirectories);
		iterableNode.forEach(System.out::println);

		observableFile = nodeConverter.convertTreeToObservableStream(rootDirectories);


		for (; ; ) {
			WatchKey key;
			try {
				key = watcher.take(); //waits until some action will occur
			} catch (InterruptedException x) {
				return;
			}

			Path dir = keys.get(key);
			handler.pathScan();
			if (dir != null) {

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind kind = event.kind();

					// Context for directory entry event is the file name of entry
					Path name = ((WatchEvent<Path>) event).context();
					Path child = dir.resolve(name);

					System.out.format("%s: %s\n", event.kind().name(), child);

					// if directory is created, and watching recursively, then register it and its sub-directories
					if (kind == ENTRY_CREATE) {
						try {
							if (Files.isDirectory(child)) {
								walkAndRegisterDirectories(child);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				// reset key and remove from set if directory no longer accessible
				boolean valid = key.reset();
				if (!valid) {
					keys.remove(key);

					// all directories are inaccessible
					if (keys.isEmpty()) {
						break;
					}
				}
			}
		}
	}

}
