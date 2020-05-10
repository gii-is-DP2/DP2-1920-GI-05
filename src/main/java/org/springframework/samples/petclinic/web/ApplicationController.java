package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.ApplicationPOJO;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateApplicationException;
import org.springframework.samples.petclinic.service.exceptions.InactiveTournamentException;
import org.springframework.samples.petclinic.service.exceptions.InvalidPetTypeException;

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

	@InitBinder
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("application")
	public void initApplicationBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ApplicationValidator());
	}

	@ModelAttribute("statusType")
	public List<String> statusTypes() {
		List<String> types = new ArrayList<>();
		types.add("PENDING");
		types.add("ACCEPTED");
		types.add("REJECTED");

		return types;
	}

	@ModelAttribute("pets")
	public Collection<Pet> populatePets() {

		Collection<Pet> pets = new ArrayList<>();
		Owner owner = this.ownerService.findOwnerByUserName();
		if (owner != null) {
			pets = this.petService.findPetByOwnerId(owner.getId());
		}

		return pets;
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
	@GetMapping(value = { "/applications/all" })
	public String ApplicationList(Map<String, Object> model) {
		Collection<Application> allApplications = this.applicationService.findAllApplications();
		model.put("applications", allApplications);
		return "applications/list";
	}

	@GetMapping(value = "/applications/{tournamentId}/new")
	public String initCreateApplicationForm(@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		ApplicationPOJO applicationPOJO = new ApplicationPOJO();

		//Owner owner = this.ownerService.findOwnerByUserName();
		model.put("applicationPOJO", applicationPOJO);
		return "applications/createApplicationForm";
	}

	@PostMapping(value = "/applications/{tournamentId}/new")
	public String processCreateForm(@Valid ApplicationPOJO applicationPOJO, BindingResult result,
			@PathVariable("tournamentId") int tournamentId, ModelMap model) throws DataAccessException,
			DuplicateApplicationException, InvalidPetTypeException, InactiveTournamentException {
		if (result.hasErrors()) {			
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
				result.rejectValue("pet", "You have already applied for this tournament",
						"You have already applied for this tournament");
				model.put("applicationPOJO", applicationPOJO);
				return "applications/createApplicationForm";

			} catch (InvalidPetTypeException ex) {
				result.rejectValue("pet", "You can not apply to this tournament with that pet type",
						"You can not apply to this tournament with that pet type");
				//Owner owner = this.ownerService.findOwnerByUserName();
				// model.put("pets", this.petService.findPetByOwnerId(owner.getId()));
				return "applications/createApplicationForm";
			} catch (InactiveTournamentException ex) {
				result.rejectValue("pet", "This tournament no longer allows requests",
						"This tournament no longer allows requests");
				model.put("applicationPOJO", applicationPOJO);
				return "applications/createApplicationForm";
			}

			return "redirect:/applications/list_mine";
		}
	}

	@GetMapping(value = { "/applications/{applicationId}/edit" })
	public String initUpdateApplicationForm(@PathVariable("applicationId") int applicationId, ModelMap model) {
		Application application = this.applicationService.findApplicationById(applicationId);
		model.put("application", application);

		return "applications/updateApplicationForm";
	}

	@PostMapping(value = { "/applications/{applicationId}/edit" })
	public String processUpdateForm(@Valid Application application, BindingResult result,
			@PathVariable("applicationId") int applicationId, ModelMap model) throws DataAccessException, InactiveTournamentException {

		if (result.hasErrors()) {
			model.put("application", application);
			return "applications/updateApplicationForm";
		} else {

			try {

				application.setId(applicationId);
				this.applicationService.updateApplication(application);
			} catch (InactiveTournamentException ex) {
				result.rejectValue("status", "The application date for this tournament has expired",
						"The application date for this tournament has expired");
				model.put("application", application);
				return "applications/updateApplicationForm";
			}
			return "redirect:/applications/all";
		}
	}

}
