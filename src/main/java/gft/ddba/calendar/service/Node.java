package gft.ddba.calendar.service;

import java.util.List;

public interface Node<T> {

	T getData();

	// Iterable<Node<T>> getChildren();
	public List<Node<T>> getChildren();

}
