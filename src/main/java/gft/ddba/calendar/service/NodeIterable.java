package gft.ddba.calendar.service;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

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
		/**
		 * instead of stack it is used ArrayDeque
		 * which has better performance
		 */
		Queue<Node<T>> stackWithChildren = new ArrayDeque<>();

		public NodeInterator() {
			stackWithChildren.addAll(node.getChildren());
		}

		@Override
		public T next() {
			Node<T> node = stackWithChildren.poll();
			stackWithChildren.addAll(node.getChildren());
			return node.getData();
		}

		@Override
		public boolean hasNext() {
			return !stackWithChildren.isEmpty();
		}
	}
}
