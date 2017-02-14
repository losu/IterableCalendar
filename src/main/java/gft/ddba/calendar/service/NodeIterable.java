package gft.ddba.calendar.service;

import java.util.Iterator;
import java.util.Stack;

public class NodeIterable<T> implements Iterable<T> {

	Node<T> node;

	public NodeIterable(Node<T> node) {
		super();
		this.node = node;
	}

	@Override
	public Iterator<T> iterator() {

		return new NodeInterator();
	}

	class NodeInterator implements Iterator<T> {
		Stack<Node<T>> stackWithChildren = new Stack<>();

		public NodeInterator() {
			stackWithChildren.addAll(node.getChildren());
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
}
