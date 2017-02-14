package gft.ddba.calendar.service;

import java.util.List;

public interface Node<T> {

	T getData();

	List<Node<T>> getChildren();

}
