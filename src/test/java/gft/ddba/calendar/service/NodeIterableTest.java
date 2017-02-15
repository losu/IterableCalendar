package gft.ddba.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class NodeIterableTest {

	class Royalty implements Node<String> {
		String title;
		private List<Node<String>> childrenTitle;

		public Royalty(String title, Node<String> ... children) {
			this.title = title;
			this.childrenTitle = Arrays.asList(children);
		}

		@Override
		public String getData() {
			return title;
		}

		@Override
		public List<Node<String>> getChildren() {
			return childrenTitle;
		}

	}

	@Test
	public void shouldIterableAfterConvertionContainQueenObject() {
		Node<String> kingdom = new Royalty("kingdom");
		Node<String> king = new Royalty("king");

		kingdom.getChildren().add(king);

		Iterable<String> iterableNode = NodeConverter.convertFromTreeStructureToIterableStream(kingdom);

		assertThat(iterableNode).contains("king");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test() {
		Node<String> kingdom = null;
		NodeConverter.convertFromTreeStructureToIterableStream(kingdom);

	}
	
	
	
}
