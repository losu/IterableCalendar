package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.ReactiveStream;
import gft.ddba.calendar.impl.ReactiveStreamFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Paths;

public class ReactiveStreamFactoryTest {

	@Rule
	public TemporaryFolder dir = new TemporaryFolder();
	public ReactiveStream stream;
	public ReactiveStreamFactory reactiveStreamFactory = new ReactiveStreamFactory();

	@Before
	public void initialize() throws IOException {
		stream = reactiveStreamFactory.getReactiveStream(Paths.get(dir.toString()));
	}

	@Test(expected=ClosedWatchServiceException.class)
	public void shouldResourceBeClosed() throws IOException {
		reactiveStreamFactory.close();
//		stream.getObservable();
		stream.createObservable();
	}


}
