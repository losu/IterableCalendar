package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.ReactiveStream;
import gft.ddba.calendar.model.Event;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import rx.Observable;
import rx.subjects.ReplaySubject;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReactiveStreamTest {

	@Rule
	public TemporaryFolder dir = new TemporaryFolder();
	public ReplaySubject<Event> subscriber;
	public ReactiveStream stream;

	@Before
	public void initialize() throws IOException {
		//ReplaySubject emits to any observer all of the items that
		// were emitted by the source Observable(s)
		subscriber = ReplaySubject.create();
		stream = new ReactiveStream(Paths.get(dir.getRoot().getPath()));
	}

	@Test(timeout = 1000)
	public void shouldEmitEventOnNewDirectory() throws IOException, InterruptedException {
		subscriber = ReplaySubject.create();
		stream = new ReactiveStream(Paths.get(dir.getRoot().getPath()));
//		Observable<Event> observable = stream.getObservable();
		Observable<Event> observable = stream.createObservable();
		observable.subscribe(subscriber);
		Files.createFile(Paths.get(dir.getRoot().getPath() + "/test"));
		subscriber.toBlocking().first();
	}

	@Test(timeout = 1000)
	public void shouldRegisterNewEventInTheNewFolderCreatedFolder() throws IOException, InterruptedException {
//		Observable<Event> observable = stream.getObservable();
		Observable<Event> observable = stream.createObservable();
		observable.subscribe(subscriber);
		if (dir.newFolder("folder", "newDirectory").mkdir()) {
			Files.createFile(Paths.get(dir.getRoot().getPath() + "/newDirectory" + "/test"));
		}
		subscriber.toBlocking().first();
	}

	@Test
	public void shouldConvertDirectoryToObservable() throws IOException {
//		Assertions.assertThat(stream.getObservable()).isInstanceOf(Observable.class);
		Assertions.assertThat(stream.createObservable()).isInstanceOf(Observable.class);
	}

	@Test
	public void shouldCloseTheStreamAndAllTheRelatedResources() throws Exception {
		stream.close();
//		Assertions.assertThatThrownBy(stream::getObservable).isInstanceOf(ClosedWatchServiceException.class);
		Assertions.assertThatThrownBy(stream::createObservable).isInstanceOf(ClosedWatchServiceException.class);
	}
}