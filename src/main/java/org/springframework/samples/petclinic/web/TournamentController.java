package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateTournamentNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TournamentController {
	
	private static final String VIEWS_TOURNAMENTS_CREATE_OR_UPDATE_FORM = "tournaments/createOrUpdateTournamentForm";

	private final TournamentService tournamentService;
	private final CategoryService categoryService;
	private final PetService petService;
	private final FieldService fieldService;
	private final JudgeService  judgeService;
	private final GuideService  guideService;
	private final OwnerService ownerService;
	private final ApplicationService applicationService;

	@Autowired
	public TournamentController(TournamentService tournamentService, PetService petService,
			CategoryService categoryService, FieldService fieldService,  JudgeService  judgeService, OwnerService ownerService, GuideService  guideService, ApplicationService applicationService) {
		this.categoryService = categoryService;
		this.tournamentService = tournamentService;
		this.petService = petService;
		this.fieldService = fieldService;
		this.judgeService = judgeService;
		this.ownerService = ownerService;
		this.guideService = guideService;
		this.applicationService = applicationService;
	}

	@ModelAttribute("categories")
	public Collection<Category> populateCategories() {
		return this.categoryService.findAllCategories();
	}
	
	@ModelAttribute("fields")
	public Collection<Field> populatefields() {
		return this.fieldService.findAllFields();
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}
	
	@ModelAttribute("judges")
	public Collection<Judge> populateJudges() {
		return this.judgeService.findAllJudges();
	}
	
		
	@InitBinder("tournament")
	public void initTournamentBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new TournamentValidator());
	}

	@GetMapping(value = "/tournaments/new")
	public String initCreationForm(ModelMap model) {
		Tournament tournament = new Tournament();
		model.put("tournament", tournament);
		return VIEWS_TOURNAMENTS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/tournaments/new")
	public String processCreationForm(@Valid Tournament tournament, BindingResult result, ModelMap model) 
			throws DataAccessException, DuplicateTournamentNameException {
		if (result.hasErrors()) {
			model.put("tournament", tournament);
			return VIEWS_TOURNAMENTS_CREATE_OR_UPDATE_FORM;
		} else {
			try {

				this.tournamentService.saveTournament(tournament);
			} catch (DuplicateTournamentNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_TOURNAMENTS_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/tournaments/all";
		}
	}
	
	@GetMapping(value = { "/tournaments/all" })
	public String showAllTournaments(Map<String, Object> model) {
				
		Collection<Tournament> allTournaments = this.tournamentService.findAllTournament();
		model.put("tournaments", allTournaments);
		return "tournaments/list";
	}
	
	@GetMapping(value = { "/tournaments/active" })
	public String showActiveTournaments(Map<String, Object> model) {
			
		
		if(this.ownerService.findOwnerByUserName()!=null)	{
			Owner owner = this.ownerService.findOwnerByUserName();
			model.put("owner", owner);			
		}
		
		if(this.judgeService.findJudgeByUserName()!=null)	{
			model.put("judge", this.judgeService.findJudgeByUserName());
		}
		
		if(this.guideService.findGuideByUserName()!=null)	{
			model.put("guide", this.guideService.findGuideByUserName());
		}
		
		
		Collection<Tournament> activeTournaments = this.tournamentService.findActiveTournaments();
		model.put("tournaments", activeTournaments);
		return "tournaments/list";
	}
	
	@GetMapping(value = "/tournaments/{tournamentId}/edit")
	public String initUpdateTournamentForm(@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
	
		 model.put("tournament", tournament);
		return VIEWS_TOURNAMENTS_CREATE_OR_UPDATE_FORM;
	}

	

	@PostMapping(value = "/tournaments/{tournamentId}/edit")
	public String processUpdateTournamentForm(@Valid Tournament tournament, BindingResult result,
			@PathVariable("tournamentId") int tournamentId) throws DataAccessException, DuplicateTournamentNameException {
		if (result.hasErrors()) {
			return VIEWS_TOURNAMENTS_CREATE_OR_UPDATE_FORM;
		}
		else {
			
			try {
				tournament.setId(tournamentId);
				this.tournamentService.saveTournament(tournament);
			} catch (DuplicateTournamentNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_TOURNAMENTS_CREATE_OR_UPDATE_FORM;
			}
		
			return "redirect:/tournaments/all";
		}
	}
	
	@GetMapping(value = "/tournaments/{tournamentId}/show")
	public String initShowTournament(@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		Tournament tournament = this.tournamentService.findTournamentById(tournamentId);
		
		if(this.ownerService.findOwnerByUserName()!=null)	{
			Owner owner = this.ownerService.findOwnerByUserName();
			Boolean hasApplication = false;
			int ownerId = owner.getId();
			if(this.applicationService.findApplicationsByOwnerTournament(ownerId, tournamentId) != null) {
				hasApplication=true;
			}
			model.put("hasApplication", hasApplication);	
			model.put("owner", owner);			
		}
		
		 model.put("tournament", tournament);
		return "tournaments/showTournament";
	}

}
