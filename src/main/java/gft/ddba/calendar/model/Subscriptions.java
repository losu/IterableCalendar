package gft.ddba.calendar.model;

import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Subscriptions implements AutoCloseable {


	private ConcurrentHashMap<String, Subscription> subscription;
	private ConcurrentHashMap<String, Subscriber<Event>> subscriber;

	public Subscriptions() {
		this.subscription = new ConcurrentHashMap<>();
		this.subscriber = new ConcurrentHashMap<>();
	}

	public void addSubscriber(String endPoint, Subscriber<Event> subscriber) {
		this.subscriber.put(endPoint, subscriber);
	}

	public void subscribe(String endPoint, Observable<Event> observable){
		Subscriber<Event> subscriber = this.subscriber.remove(endPoint);
		subscription.put(endPoint, observable.subscribe(subscriber));
	}

	public void unsubscribe(String key) {
		Subscription currentSubscription = subscription.remove(key);
		if (currentSubscription == null) return;
		currentSubscription.unsubscribe();
	}

	@Override
	public void close() throws IOException {
		subscription.forEach((s, subscription) -> {
			subscription.unsubscribe();
		});
		subscriber.clear();
	}
}