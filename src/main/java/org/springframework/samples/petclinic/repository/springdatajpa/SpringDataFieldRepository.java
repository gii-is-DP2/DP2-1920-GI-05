package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.repository.FieldRepository;


public interface SpringDataFieldRepository extends FieldRepository, Repository<Field,Integer>{

	
	@Override
	@Query("SELECT f FROM Field f ORDER BY f.name")
	List<Field> findAllFields() throws DataAccessException;

	@Override
	@Query("SELECT f FROM Field f WHERE f.id =:id")
	public Field findById(@Param("id") int id);
	
	@Override
	@Query("SELECT f FROM Field f WHERE f.name =:name")
	public List<Field> findByName(@Param("name") String name);

	
	
}
