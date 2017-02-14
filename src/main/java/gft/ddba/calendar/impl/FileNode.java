package gft.ddba.calendar.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import gft.ddba.calendar.service.Node;

public class FileNode<T> implements Node<T>, Iterable<T> {
	private T data;
	//private @Getter Node<T> parent;
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

	public FileNode(T data, Node<T> parent) {
		this.data = data;
	//	this.parent = parent;
		this.children = new ArrayList<>();
	}

//	public void addChild(Node<T> child) {
//		children.add(child);
//	}

	@Override
	public Iterator<T> iterator() {

		return new NodeInterator();
	}

	class NodeInterator implements Iterator<T> {
		Stack<Node<T>> stackWithChildren = new Stack<>();

		public NodeInterator() {
			stackWithChildren.addAll(getChildren());
		}

		@Override
		public T next() {
			Node<T> node = stackWithChildren.pop();
			stackWithChildren.addAll(node.getChildren());
			return node.getData();
		}

		@Override
		public boolean hasNext() {
			return !stackWithChildren.isEmpty();
		}
	}

	@Override
	public String toString() {
		return "Node [data=" + data + ", children=" + children + "]";
	}

}
