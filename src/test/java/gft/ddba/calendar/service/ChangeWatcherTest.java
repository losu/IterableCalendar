package gft.ddba.calendar.service;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Before;
import org.junit.Test;
import rx.Observer;
import rx.observers.TestSubscriber;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
		List<Observer> observers = Collections.synchronizedList(new ArrayList<>());

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
}
