//package gft.ddba.calendar.impl;
//
//
//import gft.ddba.calendar.service.Node;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//@RestController
//public class PathHandler {
//
//		@RequestMapping(value = "/pathScan", method = RequestMethod.GET)
//	public List<Node<Path>> pathScan() {
//		Path path = Paths.get("C:/Users/ddba/Desktop/Test/");
//		PathNode pathNode = new PathNode(path);
//		pathNode.getChildren().forEach(System.out::println);
//		return pathNode.getChildren();
//	}
//}
