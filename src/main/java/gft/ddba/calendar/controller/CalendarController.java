package gft.ddba.calendar.controller;

import java.io.File;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gft.ddba.calendar.impl.FileNode;
import gft.ddba.calendar.impl.NodeHandler;
import gft.ddba.calendar.model.Calendar;
import gft.ddba.calendar.model.FileModel;

@RestController
public class CalendarController {

	@Autowired
	NodeHandler nodeController;

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public void displayDates() {

		LocalDate startDate = LocalDate.of(2016, 9, 19);

		Calendar calendar = new Calendar(startDate);
		calendar.forEach(data -> {
			if (data != null) {
				System.out.println(data);
			}
		});
	}

	@RequestMapping(value = "/scan", method = RequestMethod.GET)
	public FileNode<FileModel> display() {

		FileNode<FileModel> root = new FileNode<>();
		nodeController.scan(new File("C:/Users/ddba/Desktop/Test"), root);

		return root;

	}

	/**
	 * Convert tree-based structure to iterable collection
	 * 
	 * @implNote by using DFS algorithms which is an algorithm for traversing or
	 *           searching tree or graph data structures
	 * @param root
	 *            starting point of the tree from where the conversion is being
	 *            done
	 * @return structure converted to the stream
	 */
	@RequestMapping(value = "/convert", method = RequestMethod.GET)
	public  Iterable<FileModel> convertFromTreeStructureToIterableStream(FileNode<FileModel> root) {

//		File file = new File("C:/Users/ddba/Desktop/Test");
//		root.setData(new FileModel("Test"));
//		nodeController.scan(file, root);
//		root.forEach(System.out::println);
		
		return null;
	}

}
