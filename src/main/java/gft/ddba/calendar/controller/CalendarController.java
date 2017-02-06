package gft.ddba.calendar.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gft.ddba.calendar.model.Calendar;

@RestController
public class CalendarController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void displayDates() {

		LocalDate startDate = LocalDate.of(2016, 9, 19);

		Calendar calendar = new Calendar(startDate);
		calendar.forEach(data -> {
			if (data != null) { 
				System.out.println(data);
			}
		});
		//return calendar;
	}

	

}
