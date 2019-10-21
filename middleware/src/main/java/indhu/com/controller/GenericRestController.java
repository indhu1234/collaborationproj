package indhu.com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenericRestController {

	@GetMapping("/demo")
	public String checkDemo() {
		return "This is a demo string";
	}
}