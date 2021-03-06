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
			LocalDate nextDate = startDate;

			@Override
			public LocalDate next() {

				if ((startDate.equals(nextDate)) && ((startDate.getDayOfWeek().equals(DayOfWeek.TUESDAY))
						|| (startDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)))) {
					nextDate = nextDate.plusDays(1L);
					return startDate;
				}

				do {
					nextDate = nextDate.plusDays(1L);

				} while (!(nextDate.getDayOfWeek().equals(DayOfWeek.TUESDAY))
						&& !(nextDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)));

				return nextDate;
			}

			@Override
			public boolean hasNext() {
				return true;
			}
		};
	}

}
