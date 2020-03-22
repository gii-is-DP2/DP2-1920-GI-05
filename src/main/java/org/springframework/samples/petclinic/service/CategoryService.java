package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.CategoryRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Category> findAllCategories() throws DataAccessException {
		return categoryRepository.findAllCategories();
	}

	@Transactional
	public void saveCategory(@Valid Category category) throws DataAccessException {
		categoryRepository.save(category);

	}

	@Transactional(rollbackFor = DuplicateCategoryNameException.class)
	public void saveCategory(Category category) throws DataAccessException, DuplicateCategoryNameException {
		Category otherCategory = getCategorywithIdDifferent(category.getName(), category.getId());
		if (StringUtils.hasLength(category.getName())
				&& (otherCategory != null && otherCategory.getId() != category.getId())) {
			throw new DuplicateCategoryNameException();
		} else
			categoryRepository.save(category);
	}

}
