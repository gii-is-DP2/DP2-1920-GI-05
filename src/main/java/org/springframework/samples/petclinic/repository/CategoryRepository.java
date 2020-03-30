package org.springframework.samples.petclinic.repository;

import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Category;

public interface CategoryRepository {

	List<Category> findAllCategories() throws DataAccessException;

	Category findById(int id) throws DataAccessException;

	void save(@Valid Category category);

	List<Category> findByName(String name) throws DataAccessException;



}
