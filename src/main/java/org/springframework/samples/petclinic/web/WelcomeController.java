package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.repository.UserRepository;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
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
	private JudgeService  jugdeService;
	private GuideService guideService;
	
	@Autowired
	public WelcomeController(OwnerService ownerService, JudgeService  jugdeService, GuideService guideService) {
		this.ownerService = ownerService;
		this.jugdeService = jugdeService;
		this.guideService = guideService;	

	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Map<String, Object> model) {
		
		
		if(this.ownerService.findOwnerByUserName()!=null)	{
			model.put("owner", this.ownerService.findOwnerByUserName());
		}
		
		if(this.jugdeService.findJudgeByUserName()!=null)	{
			model.put("judge", this.jugdeService.findJudgeByUserName());
		}
		
		if(this.guideService.findGuideByUserName()!=null)	{
			model.put("guide", this.guideService.findGuideByUserName());
		}

		return "welcome";
	}
}
