package gft.ddba.calendar.impl;

import java.io.File;

import org.springframework.stereotype.Controller;

import gft.ddba.calendar.model.FileModel;
import gft.ddba.calendar.service.Node;

@Controller
public class FileNodeHandler {

	/**
	 * Scanning the given path of the directory to create tree structure
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
				Node<FileModel> child = new FileNode<>(fileModel, new FileNode<>(root.getData()));
				root.getChildren().add(child);
				scan(f, child);
			}
		}
	}
}
