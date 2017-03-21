package gft.ddba.calendar.impl;

import gft.ddba.calendar.service.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathNode implements Node<Path> {

	private Path root;

	@Override
	public Path getData() {
		return root;
	}

	public PathNode(Path root) {
		this.root = root;
	}

	@Override
	public List<Node<Path>> getChildren() {
		return setupChildren();
	}

	public List<Node<Path>> setupChildren() {
		List<Node<Path>> children = new ArrayList<>();
		try {
			if (Files.isDirectory(root)) {
//			children = Files.walk(Paths.get("C:/Users/ddba/Desktop/Test/")).map(el -> new PathNode(el))
//					.collect(Collectors.toList());
				children = Files.list(root).map(path -> new PathNode(path)).collect(Collectors.toList());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return children;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PathNode{");
		sb.append("root=").append(root);
		return sb.toString();
	}
}
//private final Path rootPath;
//
//	public PathNode(Path rootPath) {
//		this.rootPath = rootPath;
//	}
//
//	@Override
//	public Path getData() {
//		return rootPath;
//	}
//
//
//	@Override
//	public List<Node<Path>> getChildren() {
//		return initChildren();
//	}
//
//	private List<Node<Path>> initChildren() {
//		List<Node<Path>> children = new ArrayList<>();
//		if (Files.isDirectory(rootPath)) {
//			try {
//				children = Files.list(rootPath).map(path -> new PathNode(path)).collect(Collectors.toList());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return children;
//	}
//}
