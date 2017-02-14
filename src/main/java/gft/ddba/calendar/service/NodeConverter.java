package gft.ddba.calendar.service;

//@RestController
public class NodeConverter {



	/**
	 * Convert tree-based structure to iterable collection
	 * 
	 * @implNote by using DFS algorithms which is an algorithm for traversing or
	 *           searching tree or graph data structures
	 * @param root
	 *            starting point of the tree from where the conversion is being
	 *            done
	 * @return structure converted to the stream
	 */
	//@RequestMapping(value = "/convert", method = RequestMethod.GET)
	public static <T> Iterable<T> convertFromTreeStructureToIterableStream(Node<T> root) {
		if(root==null){
		throw new IllegalArgumentException();
		}
		return new NodeIterable<>(root);
	}
}
