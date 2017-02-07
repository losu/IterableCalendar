package gft.ddba.calendar.controller;

import java.time.LocalDate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gft.ddba.calendar.model.Calendar;
import gft.ddba.calendar.model.File;
import gft.ddba.calendar.model.Node;

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
		// return calendar;
	}

	/**
	 * Convert tree-based structure to iterable collection 
	 * 
	 * @implNote
	 * by using BFS algorithms  which
	 * is an algorithm for traversing or searching tree or graph data structures
	 * 
	 * @param root starting point of the tree from where the conversion is being done
	 * @return structure converted to the stream 
	 */
	public static Iterable<File> convertFromTreeStructureToIterableStream(Node<File>  root) {
		return null;
	}

}
