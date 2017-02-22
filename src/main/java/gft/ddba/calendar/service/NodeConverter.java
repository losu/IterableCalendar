package gft.ddba.calendar.service;


import rx.Observable;

import java.util.ArrayDeque;
import java.util.Queue;

public class NodeConverter {

	/**
	 * Convert tree-based structure to iterable collection
	 *
	 * @param root starting point of the tree from where the conversion is being
	 *             done
	 * @return structure converted to the stream
	 * @implNote by using DFS algorithms which is an algorithm for traversing or
	 * searching tree or graph data structures
	 * @info if root is equal to null then it @throws IllegalArgumentException()
	 */
	public static <T> Iterable<T> convertFromTreeStructureToIterableStream(Node<T> root) {
		if (root == null) {
			throw new IllegalArgumentException();
		}
		return new NodeIterable<>(root);
	}

	/**
	 * @param root
	 * @return
	 * @info if root is equal to null then it @throws IllegalArgumentException()
	 */
	public static <T> Observable<T> rxConvertFromIterableToObservableStream(Node<T> root) {
		if (root == null) {
			throw new IllegalArgumentException();
		}
		Queue<Node<T>> stackWithChildren = new ArrayDeque<>();
		stackWithChildren.addAll(root.getChildren());

		return Observable.create(subscriber -> {
			while (!stackWithChildren.isEmpty()) {
				Node<T> node = stackWithChildren.poll();
				stackWithChildren.addAll(node.getChildren());
				subscriber.onNext(node.getData());
			}
		});
	}


}