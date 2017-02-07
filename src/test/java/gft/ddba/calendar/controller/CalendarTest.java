package gft.ddba.calendar.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.Test;

import gft.ddba.calendar.model.Calendar;

public class CalendarTest {

	

	@Test
	public void shouldHasNextMethodAlwaysReturnTrue() {

		Calendar initialDate = new Calendar(LocalDate.of(2016, 9, 19));
		assertTrue(initialDate.iterator().hasNext());
	}

	@Test
	public void shouldReturnNextTuesdayOrFriday() {
		Calendar initialDate = new Calendar(LocalDate.of(2016, 9, 19));
		Iterator<LocalDate> i1 = initialDate.iterator();
		
		assertThat(i1.next(), is(LocalDate.of(2016, 9, 20)));
		assertThat(i1.next(), is(LocalDate.of(2016, 9, 23)));
		assertThat(i1.next(), is(LocalDate.of(2016, 9, 27)));
		assertThat(i1.next(), is(LocalDate.of(2016, 9, 30)));

	}

	@Test
	public void twoIteratorsShouldNotImpactToEachOther() {
		LocalDate startDate = LocalDate.of(2016, 9, 19);

		Calendar initialDate = new Calendar(startDate);

		assertThat(initialDate.iterator().next(), is(LocalDate.of(2016, 9, 20)));

		Iterator<LocalDate> i1 = initialDate.iterator();
		Iterator<LocalDate> i2 = initialDate.iterator();

		assertThat(i1.next(), is(LocalDate.of(2016, 9, 20)));
		assertThat(i1.next(), is(LocalDate.of(2016, 9, 23)));
		assertThat(i1.next(), is(LocalDate.of(2016, 9, 27)));
		assertThat(i2.next(), is(LocalDate.of(2016, 9, 20)));
		

	}
	@Test
	public void shouldStartFromTheDayIfTheInitialDateIsTuesdayOrFriday() {
		Calendar initialDate = new Calendar(LocalDate.of(2016, 9, 20));
		Iterator<LocalDate> i1 = initialDate.iterator();

		assertThat(i1.next(), is(LocalDate.of(2016, 9, 20)));
	}

}
