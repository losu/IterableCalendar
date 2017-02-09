package gft.ddba.calendar.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gft.ddba.calendar.service.Node;
import lombok.Getter;

public class FileNode<T> implements Node<T>,Iterable<T> {
	private T data;
	private @Getter Node<T> parent;
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

	public FileNode(T data, FileNode<T> parent) {
		this.data = data;
		this.parent = parent;
		this.children = new ArrayList<>();
	}

	public void addChild(FileNode<T> child) {
		children.add(child);

	}

//	@Override
//	public Iterator<T> iterator() {
//
//		return new Iterator<T>() {
//			FileNode<T> nextNode = new FileNode<>(data);
//			
//			@Override
//			public T next() {
//				
////					System.out.println(nextData);
////					nextData= getChildren().iterator().next().getData();
//				
//					if(nextNode.getChildren()!=null){
//						for(FileNode<T> child: nextNode.getChildren()){
//							nextNode= getChildren().iterator().next();		
//						}
//					}
//				
//				return nextNode.getData();
//			}
//
//			@Override
//			public boolean hasNext() {
//
//				if (!getChildren().isEmpty() || getChildren() != null) {
//					return true;
//				}
//				if (getParent().getChildren().stream().anyMatch(node -> !node.getData().equals(getData()))) {
//					return true;
//				}
//				return false;
//			}
//		};
//	}
	
	public void preorder(FileNode<T>root){
		System.out.println(root.getData().toString());
		
		
	}
	

	@Override
	public String toString() {
		return "Node [data=" + data + ", parent=" + parent + ", children=" + children + "]";
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	


	
	
	

}
