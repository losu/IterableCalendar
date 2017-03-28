 package gft.ddba.calendar.service;


import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class NodeConverter {

	/**
	 * Convert tree-based structure to iterable collection
	 *
	 * @param root starting point of the tree from where the conversion is being
	 *             done
	 * @return structure converted to the stream
	 * @implNote by using DFS algorithms which is an algorithm for traversing or
	 * searching tree or graph data structures
	 * @exception if root is equal to null then it @throws IllegalArgumentException()
	 */
	public static <T> Iterable<T> convertTreeToIterableStream(Node<T> root) {
		if (root == null) {
			throw new IllegalArgumentException();
		}
		return new NodeIterable<>(root);
	}

	/**
	 * Converts Tree structure to reactive stream
	 *
	 * @param root
	 * @return
	 * @info if root is equal to null then it returns empty items but it terminates normally
	 */
	public static <T> Observable<T> convertTreeToObservableStream(Node<T> root) {
		if (root == null) {
			//return Observable.never(); // creates an observable which emits no item and does not terminate
			return Observable.empty(); // creates an observable that is empty which has the end of the stream
		}
		Queue<Node<T>> stackWithChildren = new ArrayDeque<>();
		stackWithChildren.addAll(root.getChildren());

		return Observable.create(subscriber -> {
			while (!stackWithChildren.isEmpty()) {
				Node<T> node = stackWithChildren.poll();
				stackWithChildren.addAll(node.getChildren());
				subscriber.onNext(node.getData());
			}
			subscriber.onCompleted();
		});
	}


}
