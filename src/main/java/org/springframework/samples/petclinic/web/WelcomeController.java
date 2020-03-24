package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class WelcomeController {

	private OwnerService ownerService;
	
	@Autowired
	public WelcomeController(OwnerService ownerService) {
		this.ownerService = ownerService;

	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Map<String, Object> model) {
		
		
		if(this.ownerService.findOwnerByUserName()!=null)	{
			model.put("owner", this.ownerService.findOwnerByUserName());
		}

		return "welcome";
	}
}
