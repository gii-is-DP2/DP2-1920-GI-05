package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.OwnerRepository;

public interface SpringDataOwnerRepository extends OwnerRepository, Repository<Owner, Integer> {

	@Override
	@Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
	public Collection<Owner> findByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
	public Owner findById(@Param("id") int id);
	
	
	@Query("SELECT owner FROM Owner owner WHERE owner.user.username =:userName")
	public Owner findByUserName(@Param("userName") String  userName);
	
	@Override
	@Query("SELECT owner FROM Owner owner ORDER BY owner.lastName")
	List<Owner> findAllOwners() throws DataAccessException;
	


}
