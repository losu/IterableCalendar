package gft.ddba.calendar.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import gft.ddba.calendar.model.FileModel;
import gft.ddba.calendar.model.Node;

@RunWith(MockitoJUnitRunner.class)
public class NodeTest {

	@InjectMocks
	NodeController controller;
	@Mock
	File rootFile;
	@Mock
	File child;
	@Mock
	File child2;
	@Mock
	File child3;

	@Test
	public void shouldReturnOneChildWhenIsAddedToTheNodeAChild() {
		Node<FileModel> node = new Node<>();
		node.addChild(new Node<>());
		assertThat(node.getChildren().size(), is(1));
	}

	@Test
	public void test() {

		setaupMockFile();
		Node<FileModel> rootNode = new Node<>(new FileModel(""),new Node<>());
		controller.scan(rootFile, rootNode);

		assertThat(rootNode.getChildren().size(), is(3));

	}

	private void setaupMockFile() {
		File[] listOfFiles = { child, child2, child3 };
		Mockito.when(rootFile.isDirectory()).thenReturn(true);
		Mockito.when(child.isDirectory()).thenReturn(true);
		Mockito.when(child2.isDirectory()).thenReturn(true);
		Mockito.when(child3.isDirectory()).thenReturn(false);
		Mockito.when(child.getParentFile()).thenReturn(rootFile);
		Mockito.when(child2.getParentFile()).thenReturn(rootFile);
		Mockito.when(child3.getParentFile()).thenReturn(rootFile);
		Mockito.when(rootFile.listFiles()).thenReturn(listOfFiles);
		Mockito.when(child.listFiles()).thenReturn(new File[0]);
		Mockito.when(child2.listFiles()).thenReturn( new File[0]);
		Mockito.when(child3.listFiles()).thenReturn(new File[0]);
	}
}
