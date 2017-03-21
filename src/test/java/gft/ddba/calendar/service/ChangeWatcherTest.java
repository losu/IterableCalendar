package gft.ddba.calendar.service;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import gft.ddba.calendar.impl.FileNodeHandler;
import gft.ddba.calendar.impl.PathNode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import rx.observers.TestSubscriber;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class ChangeWatcherTest {
	protected FileSystem fs;

	@Before
	public void createFileSystem() {
		fs = Jimfs.newFileSystem(Configuration.unix()
				.toBuilder()
				//.setMaxSize(1024 )
				//.setMaxCacheSize(256)
				//.setRoots("/")
				.setWorkingDirectory("/root")
				.build()
		);
		try {
			Files.createDirectories(fs.getPath("main/resources/h2"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldNotifyEverySubscriber() throws Exception {
		//directory = Files.createTempDirectory("root");
		String newPath = "/root/main";
		String newFile = "newfile.txt";
		String rootDir = "/root";
		Path root = fs.getPath(rootDir);

		TestSubscriber<Path> subscriber = TestSubscriber.create();

		try (ChangeWatcher pathObservable = ChangeWatcher.watchChanges(root)) {

			pathObservable.subscribe(subscriber);

			Files.createFile(fs.getPath(newPath, newFile));
//			final Path newDir = directory.resolve(Paths.get("a"));
//			Files.createDirectory(newDir);
//			final Path file = newDir.resolve("b");
//			Thread.sleep(1000);
//			Files.createFile(file);
			subscriber.awaitValueCount(1, 7000, TimeUnit.MILLISECONDS);

			//subscriber.assertValue(fs.getPath(file.toString()));
			subscriber.assertValue(fs.getPath(newPath, newFile));
		}
	}

	@Test
	public void test() throws Exception {

//		ReplaySubject<WatchEvent<?>> replaySubject = ReplaySubject.create();
//		PathRx p = new PathRx();
//		PathRx.watch(Paths.get("C:/Users/ddba/Desktop/Test"))
//				//.map(e->e.context())
//				.subscribe(System.out::println);
//
//

//		PathObservables
//				.watchRecursive(Paths.get("C:/Users/ddba/Desktop/Test"))
//				//.map(event -> event.kind().name())
//				.map(event -> {
//							System.out.println(event.kind().name());
//					return event.context();}
//					)
//				.subscribe(event -> System.out.println(event));



		SimpMessagingTemplate a;
		Path path = Paths.get("C:/Users/ddba/Desktop/Test");
		Node<Path> rootNode = new PathNode(path);

		FileNodeHandler.scan(new File("C:/Users/ddba/Desktop/Test"), rootNode);

		Iterable<Path> iterable = NodeConverter.convertTreeToIterableStream(rootNode);
		iterable.forEach(System.out::println);

//
//		try (ChangeWatcher pathObservable = ChangeWatcher.watchChanges(path)) {
//			pathObservable
//
//
//
//		}

//	TestSubscriber<Path> subscriber = TestSubscriber.create();
//	List<Observer> observers = Collections.synchronizedList(new ArrayList<>());
//
//		try (ChangeWatcher pathObservable = ChangeWatcher.watchChanges(root)) {
//
//	}
	}



}
