package gft.ddba.calendar.impl;

import gft.ddba.calendar.service.Node;

import java.util.ArrayList;
import java.util.List;

public class FileNode<T> implements Node<T> {
	private T data;
	private List<Node<T>> children;

	@Override
	public T getData() {
		return data;
	}

	@Override
	public List<Node<T>> getChildren() {
		return children;
	}

	public FileNode() {
		this.children = new ArrayList<>();
	}

	public FileNode(T data) {
		this.data = data;
		this.children = new ArrayList<>();
	}

	public void addChild(Node<T> child) {
		children.add(child);
	}

	@Override
	public String toString() {
		return "Node [data=" + data + ", children=" + children + "]";
	}

}
