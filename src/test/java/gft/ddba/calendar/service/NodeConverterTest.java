package gft.ddba.calendar.service;


import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NodeConverterTest {

	class Royalty implements Node<String> {
		String title;
		private List<Node<String>> childrenTitle;

		public Royalty(String title, Node<String> ... children) {
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
	public void shouldIterableAfterConvertionContainKingObject() {
		Node<String> kingdom = new Royalty("kingdom");
		Node<String> king = new Royalty("king");

		kingdom.getChildren().add(king);

		Iterable<String> iterableNode = NodeConverter.convertFromTreeStructureToIterableStream(kingdom);

		assertThat(iterableNode).contains("king");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenRoorIsNull() {
		Node<String> kingdom = null;
		NodeConverter.convertFromTreeStructureToIterableStream(kingdom);

	}

	NodeConverter converter = new NodeConverter();

	@Test
	public void test1(){
		Node<String> kingdom = new Royalty("kingdom");
		Node<String> king = new Royalty("king");
		Node<String> king2 = new Royalty("king2");
		Node<String> queen = new Royalty("queen");

		kingdom.getChildren().add(king);
		kingdom.getChildren().add(king2);
		king.getChildren().add(queen);


		Observable<String> iterableObservable = converter.rxConvertFromIterableToObservableStream(kingdom);

		TestSubscriber<String> subscriber = new TestSubscriber<>();
		iterableObservable.subscribe(subscriber);

		subscriber.assertNoErrors();
		subscriber.assertValues("king","king2","queen");

	}
	
	
}
