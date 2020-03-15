package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TournamentController {

	private final TournamentService tournamentService;
	private final CategoryService categoryService;
	private final PetService petService;

	@Autowired
	public TournamentController(TournamentService tournamentService, PetService petService,
			CategoryService categoryService) {
		this.categoryService = categoryService;
		this.tournamentService = tournamentService;
		this.petService = petService;
	}

	@ModelAttribute("categories")
	public Collection<Category> populateCategories() {
		return this.categoryService.findAllCategories();
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.petService.findPetTypes();
	}

	@GetMapping(value = "/tournament/new")
	public String initCreationForm(ModelMap model) {
		Tournament tournament = new Tournament();
		// Collection<Category> category = this.categoryService.findAllCategories();
		model.put("tournament", tournament);
		// model.put("category", category);
		return "tournament/form";
	}

	@PostMapping(value = "/tournament/new")
	public String processCreationForm(@Valid Tournament tournament, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.put("tournament", tournament);
			return "tournament/form";
		} else {
			System.out.println(tournament);

			this.tournamentService.saveTournament(tournament);

			return "redirect:/";
		}
	}

}
