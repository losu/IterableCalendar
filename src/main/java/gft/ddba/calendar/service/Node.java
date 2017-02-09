package gft.ddba.calendar.service;

import java.util.List;

public interface Node<T> {

	public T getData();

	public List<Node<T>> getChildren();

}
