package gft.ddba.calendar.impl;

import java.util.ArrayList;
import java.util.List;

import gft.ddba.calendar.service.Node;

public class FileNode<T> implements Node<T> {
	private T data;
	//private Node<T> parent;
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

//	public FileNode(T data, Node<T> parent) {
//		this.data = data;
//	//	this.parent = parent;
//		this.children = new ArrayList<>();
//	}

	public void addChild(Node<T> child) {
		children.add(child);
	}

	@Override
	public String toString() {
		return "Node [data=" + data + ", children=" + children + "]";
	}

}
