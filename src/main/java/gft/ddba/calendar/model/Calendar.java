package gft.ddba.calendar.model;

import java.time.LocalDate;
import java.util.Iterator;


public class Calendar implements Iterable<LocalDate> {

	LocalDate currentDate = LocalDate.of(2016, 9, 16);
//	Iterator<LocalDate> iterator;
	
	
//	public Calendar(Iterator<LocalDate> iterator) {
//	this.iterator=iterator;
//	}
	
	Iterator<LocalDate> iterator = new Iterator<LocalDate>() {
		
		@Override
		public LocalDate next() {
			currentDate = currentDate.plusDays(1L);
			return currentDate;
		}
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return true;
		}
	};

	@Override
	public Iterator<LocalDate> iterator() {
		return iterator;
	}

}

