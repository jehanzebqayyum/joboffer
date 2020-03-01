package joboffer.eventstore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemoryEventStoreTest {

	private static final int AGGREGATE_ID = 1;

	@Autowired
	private MemoryEventStore store;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Before
	public void setup() {
		store.delete(AGGREGATE_ID);
	}

	@Test
	public void shouldStoreLoadDelete() {
		assertThat("store is not empty", store.load(AGGREGATE_ID).count(), equalTo(0));

		Event e = createEvent(AGGREGATE_ID, 0);
		store.store(Arrays.asList(e));
		List<Event> loaded = store.load(e.getAggregateId()).collect(Collectors.toList());

		assertThat("failed to load. is empty", loaded.size(), equalTo(1));
		assertThat("failed to load. event not found", loaded, hasItem(e));

		verify(publisher, times(1)).publishEvent(e);

		store.delete(e.getAggregateId());
		loaded = store.load(e.getAggregateId()).collect(Collectors.toList());
		assertThat("failed to load. is empty", loaded.isEmpty(), equalTo(true));
	}

	@Test(expected = OptimisticLockException.class)
	public void shouldFailOnOptimisticLocking() {
		Event e0 = createEvent(AGGREGATE_ID, 0), e1 = createEvent(AGGREGATE_ID, 1);
		store.store(Arrays.asList(e0, e1));
		store.store(Arrays.asList(e0));
	}

	private static Event createEvent(int id, int version) {
		return new Event() {
			@Override
			public int getVersion() {
				return version;
			}

			@Override
			public Date getTimestamp() {
				return null;
			}

			@Override
			public Object getAggregateId() {
				return id;
			}
		};
	}

}
