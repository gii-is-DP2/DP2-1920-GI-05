package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicateFieldNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

	private static final String VIEWS_CATEGORIES_CREATE_OR_UPDATE_FORM = "categories/createOrUpdateCategoryForm";

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@InitBinder("category")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new CategoryValidator());
	}

	// CRUD: List

	@GetMapping(value = { "/categories/all" })
	public String CategoriesList(ModelMap model) {
		List<Category> categories = this.categoryService.findAllCategories().stream().collect(Collectors.toList());
		model.put("categories", categories);
		return "categories/list";
	}

	@GetMapping(value = "/categories/new")
	public String initCreationForm(ModelMap model) {
		Category category = new Category();
		// Collection<Category> category = this.categoryService.findAllCategories();
		model.put("category", category);
		// model.put("category", category);
		return VIEWS_CATEGORIES_CREATE_OR_UPDATE_FORM;	
		}

	@PostMapping(value = "/categories/new")
	public String processCreationForm(@Valid Category category, BindingResult result, ModelMap model) 
		throws DataAccessException,  DuplicateCategoryNameException {
		if (result.hasErrors()) {
			model.put("category", category);
			return VIEWS_CATEGORIES_CREATE_OR_UPDATE_FORM;
		} else {
			try {
			
				this.categoryService.saveCategory(category);
			} catch (DuplicateCategoryNameException ex) {
				result.rejectValue("name", "duplicate", "already exists");
				return VIEWS_CATEGORIES_CREATE_OR_UPDATE_FORM;
			}

			return "redirect:/categories/all";
		}
	}

}
