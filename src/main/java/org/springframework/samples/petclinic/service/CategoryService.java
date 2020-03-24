package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.repository.CategoryRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicateCategoryNameException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional(readOnly = true)
	public Category findCategoryById(int id) throws DataAccessException {
		return categoryRepository.findById(id);
	}
	
	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void saveCategory(Category category) throws DataAccessException, DuplicateCategoryNameException {
			//Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
            if (!categoryRepository.findByName(category.getName()).isEmpty()){            	
            	throw new DuplicateCategoryNameException();
            }else
                categoryRepository.save(category);                
	}

}
