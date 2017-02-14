package gft.ddba.calendar.controller;

import java.time.LocalDate;

import gft.ddba.calendar.model.Calendar;

@RestController
public class CalendarController {

//	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public void displayDates() {

		LocalDate startDate = LocalDate.of(2016, 9, 19);

		Calendar calendar = new Calendar(startDate);
		calendar.forEach(data -> {
			if (data != null) {
				System.out.println(data);
			}
		});
	}

}
