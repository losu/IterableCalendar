package gft.ddba.calendar.impl;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;


@Component
public class ReactiveStreamFactory implements AutoCloseable {

	private final HashMap<String, ReactiveStream> reactiveStreams = new HashMap<>();

	public ReactiveStream getReactiveStream(Path path) throws IOException {

		ReactiveStream stream = reactiveStreams.get(path.toString());

		if(stream==null) {
			stream = new ReactiveStream(path);
			reactiveStreams.put(path.toString(),stream);
		}
		return stream;

	}

	@Override
	public void close() throws IOException {
		reactiveStreams.forEach((s, stream) -> {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		this.reactiveStreams.clear();
	}

}
