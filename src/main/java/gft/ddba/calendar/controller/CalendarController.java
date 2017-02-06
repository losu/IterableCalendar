package gft.ddba.calendar.controller;

import java.time.LocalDate;
import java.util.Iterator;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gft.ddba.calendar.model.Calendar;

@RestController
public class CalendarController {

	
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public void show(){
		
//		Iterator<LocalDate> iterator= new Iterator<LocalDate>() {
//			LocalDate now = LocalDate.now();
//			
//			@Override
//			public LocalDate next() {
//				now= now.plusDays(1L);
//				return now;
//			}
//			
//			@Override
//			public boolean hasNext() {
//				return true;
//			}
//		};
	
		
		Calendar calendar = new Calendar();
		calendar.forEach(System.out::println);
		
		
		
	}
	
}
