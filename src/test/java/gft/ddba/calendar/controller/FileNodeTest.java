package gft.ddba.calendar.controller;

import gft.ddba.calendar.impl.FileNode;
import gft.ddba.calendar.model.FileModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FileNodeTest {


	@Test
	public void shouldReturnOneChildWhenIsAddedToTheNodeAChild() {
		FileNode<FileModel> node = new FileNode<>();
		node.addChild(new FileNode<>());
		assertThat(node.getChildren().size(), is(1));
	}



	
	
}
