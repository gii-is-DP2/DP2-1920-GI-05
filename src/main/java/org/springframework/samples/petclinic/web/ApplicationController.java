package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;

@Controller
public class ApplicationController {

	private final ApplicationService applicationService;
	private final PetService petService;
	private final TournamentService tournamentService;
	private final OwnerService ownerService;

	@Autowired
	public ApplicationController(ApplicationService applicationService,
	PetService petService, TournamentService tournamentService, OwnerService ownerService) {
		this.applicationService = applicationService;
		this.petService = petService;
		this.tournamentService = tournamentService;
		this.ownerService = ownerService;
    
	}

	// Model Attributes

/* 	@ModelAttribute("pet")
	public Pet findPet(@PathVariable("petId") int petId) {
		return this.petService.findPetById(petId);
	} */

/* 	@ModelAttribute("tournament")
	public Tournament findTournament(@PathVariable("tournamentId") int tournamentId) {
		return this.tournamentService.findTournamentById(tournamentId);
	} */





	// CRUD: List
	
	@GetMapping(value = {"/applications/{ownerId}/list"})
	public String MyApplicationsList(@PathVariable("ownerId") int ownerId,ModelMap model) {
		List<Application> applications = this.applicationService.findApplicationsByOwnerId(ownerId).stream().collect(Collectors.toList());
		model.put("applications", applications);
		return "applications/list";
	}
	
	//user_story_8
	@GetMapping(value = "/applications/all")
	public String ApplicationList(ModelMap model) {		
		model.put("applications", this.applicationService.findAllApplications());
		return "applications/list";
	}
	
	
}
