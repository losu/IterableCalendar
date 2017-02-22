package gft.ddba.calendar.service;

import gft.ddba.calendar.impl.FileNode;
import gft.ddba.calendar.impl.FileNodeHandler;
import gft.ddba.calendar.model.FileModel;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ChangeWatcherTest {

	Node<FileModel> fileNode = new FileNode<FileModel>();
	@Before
	public void a(){
		FileNodeHandler handler = new FileNodeHandler();
		handler.scan(new File("C:/Users/ddba/Desktop/Test/"), fileNode);
	}

	@Test
	public void test() throws IOException {

//		ChangeWatcher cw = new ChangeWatcher(Paths.get("C:/Users/ddba/Desktop/Test/"));
//
//		TestSubscriber sub = new TestSubscriber(cw);





//
//		Node<FileModel> root = new FileNode<>();
//
//
//		TestSubscriber<Path> testSubscriber = new TestSubscriber<>();
//		NodeConverter.convertTreeToObservableStream()

	}


}
