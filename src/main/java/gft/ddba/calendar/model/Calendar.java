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
				System.out.println("start" +startDate);
				System.out.println("next" + nextDate);
				
				if( (startDate.getDayOfWeek().equals(DayOfWeek.TUESDAY))
						|| (startDate.getDayOfWeek().equals(DayOfWeek.FRIDAY))){
					System.out.println("Zwracam " + startDate);
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
