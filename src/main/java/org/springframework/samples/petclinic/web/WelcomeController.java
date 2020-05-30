package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@Autowired
	public WelcomeController() {

	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Map<String, Object> model) {
		
	

		return "welcome";
	}
}
