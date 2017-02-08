package gft.ddba.calendar.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class Node<T> {
	private @Getter T data;
	private @Getter Node<T> parent;
	private @Getter List<Node<T>> children;

	public Node() {
		this.children = new ArrayList<>();
	}

	public Node(T data) {
		this.data = data;
		this.children = new ArrayList<>();
	}

	public Node(T data, Node<T> parent) {
		this.data = data;
		this.parent = parent;
		this.children = new ArrayList<>();
	}

	public void addChild(Node<T> child) {
		children.add(child);

	}

}
