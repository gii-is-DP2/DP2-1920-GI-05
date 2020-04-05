package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.ApplicationPOJO;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateApplicationException;
import org.springframework.samples.petclinic.service.exceptions.DuplicateTournamentNameException;

@Controller
public class ApplicationController {

	private final ApplicationService applicationService;
	private final PetService petService;
	private final TournamentService tournamentService;
	private final OwnerService ownerService;

	@Autowired
	public ApplicationController(ApplicationService applicationService, PetService petService,
			TournamentService tournamentService, OwnerService ownerService) {
		this.applicationService = applicationService;
		this.petService = petService;
		this.tournamentService = tournamentService;
		this.ownerService = ownerService;

	}

	// Model Attributes

	/*
	 * @ModelAttribute("pet") public Pet findPet(@PathVariable("petId") int petId) {
	 * return this.petService.findPetById(petId); }
	 */

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("pets")
	public Collection<Pet> findOwner() {
		Owner owner = this.ownerService.findOwnerByUserName();
		return this.petService.findPetByOwnerId(owner.getId());
	}

	@ModelAttribute("ownerId")
	public Integer findOwnerId() {
		return this.ownerService.findOwnerByUserName().getId();
	}

	@InitBinder("application")
	public void initTournamentBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ApplicationValidator());
	}

	// CRUD: List

	@GetMapping(value = { "/applications/list_mine" })
	public String MyApplicationsList(ModelMap model) {
		Owner owner = this.ownerService.findOwnerByUserName();

		List<Application> applications = this.applicationService.findApplicationsByOwnerId(owner.getId()).stream()
				.collect(Collectors.toList());

		model.put("applications", applications);
		return "applications/list";
	}

	// user_story_8
	@GetMapping(value = "/applications/all")
	public String ApplicationList(ModelMap model) {
		model.put("applications", this.applicationService.findAllApplications());
		return "applications/list";
	}

	@GetMapping(value = "/applications/{tournamentId}/new")
	public String initCreateApplicationForm(@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		ApplicationPOJO applicationPOJO = new ApplicationPOJO();
		model.put("applicationPOJO", applicationPOJO);
		System.out.println("Llegando al POST");
		return "applications/createApplicationForm";
	}

	@PostMapping(value = "/applications/{tournamentId}/new")
	public String processCreateForm(@PathVariable("tournamentId") int tournamentId,
			@Valid ApplicationPOJO applicationPOJO, BindingResult result, ModelMap model)
			throws DataAccessException, DuplicateApplicationException {

		if (result.hasErrors()) {
			System.out.println(result.getFieldError());
			model.put("applicationPOJO", applicationPOJO);
			return "applications/createApplicationForm";
		} else {

			try {
				Application application = new Application();

				Owner owner = this.ownerService.findOwnerByUserName();
				LocalDate date = LocalDate.now();

				application.setPet(applicationPOJO.getPet());
				application.setCreditCard(applicationPOJO.getCreditCard());
				application.setStatus("PENDING");
				application.setMoment(date);
				application.setTournament(this.tournamentService.findTournamentById(tournamentId));
				application.setOwner(owner);

				this.applicationService.saveApplication(application);
			} catch (DuplicateApplicationException ex) {
				result.rejectValue("pet", "You have already applied for this tournament", "You have already applied for this tournament");
				return "applications/createApplicationForm";
			}

			return "redirect:/applications/list_mine";
		}
	}

}
