package gft.ddba.calendar.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Iterator;

public class Calendar implements Iterable<LocalDate> {

	private LocalDate startDate;

	public Calendar(LocalDate startDate) {
		this.startDate = startDate;
	}

	@Override
	public Iterator<LocalDate> iterator() {
		return new Iterator<LocalDate>() {
			LocalDate currentDate = startDate;

			@Override
			public LocalDate next() {
				do {
					currentDate = currentDate.plusDays(1L);

				} while (!(currentDate.getDayOfWeek().equals(DayOfWeek.TUESDAY))
						&& !(currentDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)));

				return currentDate;
			}

			@Override
			public boolean hasNext() {
				return true;
			}
		};
	}

}
