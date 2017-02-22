package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.PathHandler;

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

public class ChangeWatcher {


//	private final WatchService watcher;
//	private final Map<WatchKey, Path> keys;
//
//	public ChangeWatcher(WatchService watcher, Map<WatchKey, Path> keys) {
//		this.watcher = watcher;
//		this.keys = keys;
//
//	}	public ChangeWatcher(Path root) throws IOException {
//		this.watcher = FileSystems.getDefault().newWatchService();
//		this.keys = new HashMap<>();
//
//		walkAndRegisterDirectories(root);
//	}
//
//
//	private void registerDirectory(Path root) throws IOException {
//		WatchKey key = root.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//		keys.put(key, root);
//	}
//
//	private void walkAndRegisterDirectories(Path root) throws IOException {
//		Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
//			@Override
//			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
//				registerDirectory(dir);
//				return FileVisitResult.CONTINUE;
//			}
//		});
//	}
//
//	public void processEvent() {
//		while (true) {
//			WatchKey key = null;
//
//			//try {
//				key = watcher.poll();
//			//} catch (InterruptedException e) {
//			//	e.printStackTrace();
//			//}
//			Path dir = keys.get(key);
//			if (dir == null) {
//				System.err.println("WatchKey not recognized !!!");
//			}
//
//			for (WatchEvent<?> event : key.pollEvents()) {
//				@SuppressWarnings("rawtypes")
//				WatchEvent.Kind kind = event.kind();
//
//				@SuppressWarnings("unchecked")
//				Path name = ((WatchEvent<Path>) event).context();
//				Path child = dir.resolve(name);
//				// print out event
//				System.out.format("%s: %s\n", event.kind().name(), child);
//
//				// if directory is created, and watching recursively, then register it and its sub-directories
//				if (kind == ENTRY_CREATE) {
//					try {
//						if (Files.isDirectory(child)) {
//							walkAndRegisterDirectories(child);
//						}
//					} catch (IOException x) {
//						// do something useful
//					}
//				}
//			}
//			// reset key and remove from set if directory no longer accessible
//			boolean valid = key.reset();
//			if (!valid) {
//				keys.remove(key);
//
//				// all directories are inaccessible
//				if (keys.isEmpty()) {
//					break;
//				}
//			}
//		}
//	}

	private final WatchService watcher;
	private final Map<WatchKey, Path> keys;


	/**
	 * Creates a WatchService and registers the given directory
	 */
	public ChangeWatcher(Path dir) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();

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
	public void processEvents() {
		PathHandler handler = new PathHandler();
		for (; ; ) {

			// wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			Path dir = keys.get(key);
			handler.pathScan();
			if (dir != null) {


				for (WatchEvent<?> event : key.pollEvents()) {
					@SuppressWarnings("rawtypes")
					WatchEvent.Kind kind = event.kind();

					// Context for directory entry event is the file name of entry
					@SuppressWarnings("unchecked")
					Path name = ((WatchEvent<Path>) event).context();
					Path child = dir.resolve(name);

					// print out event
					System.out.format("%s: %s\n", event.kind().name(), child);

					// if directory is created, and watching recursively, then register it and its sub-directories
					if (kind == ENTRY_CREATE) {
						try {
							if (Files.isDirectory(child)) {
								walkAndRegisterDirectories(child);
							}
						} catch (IOException x) {
							// do something useful
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
