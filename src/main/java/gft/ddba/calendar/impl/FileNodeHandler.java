package gft.ddba.calendar.impl;


import gft.ddba.calendar.service.Node;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	public static void scan(File file, Node<Path> root) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				Path path = Paths.get(f.getPath());
				Node<Path> child = new PathNode(path);
				root.getChildren().add(child);
				scan(f, child);
			}
		} 
	}
//	/**
//	 * Scanning the given path of the directory to create tree structure
//	 *
//	 * @implInfo The method is implemented recursively.
//	 *
//	 * @param file path from where should be started scanning
//	 * @param root which will contain nested children nodes
//	 * @return tree structure of the root with all his children
//	 */
//	public static void scan(File file, Node<FileModel> root) {
//		if (file.isDirectory()) {
//			for (File f : file.listFiles()) {
//				FileModel fileModel = new FileModel(f.getName());
//				Node<FileModel> child = new FileNode<>(fileModel);
//				root.getChildren().add(child);
//				scan(f, child);
//			}
//		}
//	}

//	@RequestMapping(value = "/scan", method = RequestMethod.GET)
//	public Node<FileModel> display() {
//		Node<FileModel> root = new FileNode<>();
//		scan(new File("C:/Users/ddba/Desktop/Test"), root);
//		Iterable<FileModel> iterable = NodeConverter.convertTreeToIterableStream(root);
//		iterable.forEach(System.out::println);
//		return root;
//	}

//	public static Iterable<FileModel> convert() {
//		Node<FileModel> root = new FileNode<>();
//		scan(new File("C:/Users/ddba/Desktop/Test"), root);
//		return NodeConverter.convertTreeToIterableStream(root);
//
//	}
}
