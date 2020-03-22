package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Field;
import org.springframework.samples.petclinic.model.Visit;

public interface FieldRepository {

	List<Field> findAllFields() throws DataAccessException;

	Field findById(int id) throws DataAccessException;
	
	void save(Field field) throws DataAccessException;

}
