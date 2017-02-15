package gft.ddba.calendar.impl;

import java.io.File;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gft.ddba.calendar.model.FileModel;
import gft.ddba.calendar.service.Node;
import gft.ddba.calendar.service.NodeConverter;

@RestController
public class FileNodeHandler {

	/**
	 * Scanning the given path of the directory to create tree structure
	 * 
	 * @param <T>
	 * 
	 * @implInfo The method is implemented recursively.
	 * 
	 * @param file
	 *            path from where should be started scanning
	 * @param root
	 *            which will contain nested children nodes
	 * @return tree structure of the root with all his children
	 */
	public static void scan(File file, Node<FileModel> root) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				FileModel fileModel = new FileModel(f.getName());
				Node<FileModel> child = new FileNode<>(fileModel);
				root.getChildren().add(child);
				scan(f, child);
			}
		} 
	}

	@RequestMapping(value = "/scan", method = RequestMethod.GET)
	public Node<FileModel> display() {
		Node<FileModel> root = new FileNode<>();
		scan(new File("src/"), root);
		return root;
	}

	public static Iterable<FileModel> convert() {
		Node<FileModel> root = new FileNode<>();
		scan(new File("C:/Users/ddba/Desktop/Test"), root);
		return NodeConverter.convertFromTreeStructureToIterableStream(root);

	}
}
