package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping(value = "/category/new")
	public String initCreationForm(ModelMap model) {
		Category category = new Category();
		// Collection<Category> category = this.categoryService.findAllCategories();
		model.put("category", category);
		// model.put("category", category);
		return "category/form";
	}

	@PostMapping(value = "/category/new")
	public String processCreationForm(@Valid Category category, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.put("category", category);
			return "category/form";
		} else {

			try {
				this.categoryService.saveCategory(category);
			} catch (DuplicateCategoryNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return "category/form";

			}
			return "redirect:/";
		}
	}

}
