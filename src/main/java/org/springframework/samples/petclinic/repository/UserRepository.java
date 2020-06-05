package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository extends  CrudRepository<User, String>{

	@Query("SELECT u FROM User u ORDER BY u.username")
	public List<User> findAllUsers() throws DataAccessException;
	
	
}
