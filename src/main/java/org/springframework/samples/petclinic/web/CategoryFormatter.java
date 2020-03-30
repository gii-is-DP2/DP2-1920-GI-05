package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.stereotype.Component;

@Component
public class CategoryFormatter implements Formatter<Category> {

	private final CategoryService categoryService;

	@Autowired
	public CategoryFormatter(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public String print(Category category, Locale locale) {
		return category.getName();
	}

	@Override
	public Category parse(String text, Locale locale) throws ParseException {
		Collection<Category> findCategories = this.categoryService.findAllCategories();
		for (Category category : findCategories) {
			if (category.getName().equals(text)) {
				return category;
			}
		}
		throw new ParseException("category not found: " + text, 0);
	}

}
