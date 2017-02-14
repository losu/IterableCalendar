package gft.ddba.calendar.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NodeIterableTest {

	class Royalty implements Node<String> {
		String title;
		List<Node<String>> childrenTitle;

		public Royalty(String title) {
			this.title = title;
			this.childrenTitle = new ArrayList<>();
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
		Node<String> queen = new Royalty("queen");
		Node<String> prince = new Royalty("prince");
		Node<String> princess = new Royalty("princess");

		kingdom.getChildren().add(king);
		king.getChildren().add(queen);
		queen.getChildren().add(prince);
		prince.getChildren().add(princess);

		Iterable<String> iterableNode = NodeConverter.convertFromTreeStructureToIterableStream(kingdom);

		assertThat(iterableNode).contains("queen");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test() {
		Node<String> kingdom = null;
		NodeConverter.convertFromTreeStructureToIterableStream(kingdom);

	}
}
