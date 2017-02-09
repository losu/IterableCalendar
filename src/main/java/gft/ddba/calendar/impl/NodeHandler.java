package gft.ddba.calendar.impl;

import java.io.File;

import org.springframework.stereotype.Controller;

import gft.ddba.calendar.model.FileModel;

@Controller
public class NodeHandler {

	
	/**
	 * Scanning the given path of the directory to create tree structure
	 * 
	 * @implInfo The method is implemented recursively.
	 * 
	 * @param file path from where should be started scanning
	 * @param root which will contain nested children nodes
	 * @return tree structure of the root with all his children
	 */
	public void scan(File file, FileNode<FileModel> root) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				FileModel fileModel = new FileModel(f.getName());
				FileNode<FileModel> child = new FileNode<>(fileModel, new FileNode<>(root.getData()));
				root.addChild(child);
				scan(f, child);
			}
		}

	}
}
