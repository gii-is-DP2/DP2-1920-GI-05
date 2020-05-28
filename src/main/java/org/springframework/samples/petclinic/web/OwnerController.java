/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.RankingService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class OwnerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	private final OwnerService ownerService;
	private final JudgeService  jugdeService;
	private final GuideService guideService;
	private final RankingService rankingService;
	private final PetService petService;

	@Autowired
	public OwnerController(OwnerService ownerService, UserService userService, AuthoritiesService authoritiesService,  JudgeService  jugdeService, GuideService guideService, RankingService rankingService, PetService petService) {
		this.ownerService = ownerService;
		this.jugdeService = jugdeService;
		this.guideService = guideService;
		this.rankingService = rankingService;
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/owner/new")
	public String initCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owner/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating owner, user and authorities
			this.ownerService.saveOwner(owner);
			
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping(value = "/owners/find")
	public String initFindForm(Map<String, Object> model) {
		
			model.put("owner", new Owner());	
						
		return "owners/findOwners";
	}

	@GetMapping(value = "/owners")
	public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {
		
		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Owner> results = this.ownerService.findOwnerByLastName(owner.getLastName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		}
		else if (results.size() == 1) {
			// 1 owner found
			owner = results.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping(value = "/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
			@PathVariable("ownerId") int ownerId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			owner.setId(ownerId);
			this.ownerService.saveOwner(owner);
			return "redirect:/owners/details";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/details")
	public ModelAndView showOwner() {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		mav.addObject(this.ownerService.findOwnerByUserName());
		return mav;
	}
	
	@GetMapping(value = "/owners/{ownerId}/show")
	public String showFinderOwner(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.ownerService.findOwnerById(ownerId);
		model.addAttribute(owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}
	
	@GetMapping(value = "/rankings/all")
	public String showAllRankings(Map<String, Object> model) {
				
		Collection<Ranking> allRankings = this.rankingService.findAll();
		model.put("rankings", allRankings);
		return "rankings/list";
	}
	
	@GetMapping(value =  "/rankings/{rankingId}/show" )
	public String showRanking(@PathVariable("rankingId") int rankingId, Map<String, Object> model) {
				
		Ranking ranking = this.rankingService.findRankingById(rankingId);
		model.put("ranking", ranking);
		Map<Pet,Integer> results = new HashMap<Pet,Integer>();
		for (Integer i : ranking.getPodium().keySet()) {
			results.put(this.petService.findPetById(i), this.rankingService.findScoreByPetIdAndRanking(ranking, i));
		}
		model.put("results", results);
		return "rankings/show";
	}

}