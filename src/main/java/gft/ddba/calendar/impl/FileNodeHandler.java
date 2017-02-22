package gft.ddba.calendar.impl;

import gft.ddba.calendar.model.FileModel;
import gft.ddba.calendar.service.Node;
import gft.ddba.calendar.service.NodeConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;

@Controller
public class FileNodeHandler {


	/**
	 * Scanning the given path of the directory to create tree structure
	 *
	 * @implInfo The method is implemented recursively.
	 *
	 * @param file path from where should be started scanning
	 * @param root which will contain nested children nodes
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
		scan(new File("C:/Users/ddba/Desktop/Test"), root);
		Iterable<FileModel> iterable = NodeConverter.convertTreeToIterableStream(root);
		iterable.forEach(System.out::println);
		return root;
	}

	public static Iterable<FileModel> convert() {
		Node<FileModel> root = new FileNode<>();
		scan(new File("C:/Users/ddba/Desktop/Test"), root);
		return NodeConverter.convertTreeToIterableStream(root);

	}

//	@RequestMapping(value = "/pathScan", method = RequestMethod.GET)
//	public List<Node<Path>> pathScan(Path path) {
//		path = Paths.get("C:/Users/ddba/Desktop/Test/");
//		PathNode pathNode = new PathNode(path);
//		pathNode.getChildren().forEach(System.out::println);
//		return pathNode.getChildren();
//	}
}
