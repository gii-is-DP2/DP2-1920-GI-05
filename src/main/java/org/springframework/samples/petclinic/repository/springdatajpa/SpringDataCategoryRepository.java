package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.repository.CategoryRepository;


public interface SpringDataCategoryRepository extends CategoryRepository, Repository<Category,Integer>{

	
	@Override
	@Query("SELECT c FROM Category c ORDER BY c.name")
	List<Category> findAllCategories() throws DataAccessException;

	@Override
	@Query("SELECT c FROM Category c WHERE c.id =:id")
	public Category findById(@Param("id") int id);

	
	
}
