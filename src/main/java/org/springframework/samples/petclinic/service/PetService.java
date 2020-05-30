package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.util.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PetService extends Utils {

	private PetRepository petRepository;
	
	private VisitRepository visitRepository;
	

	@Autowired
	public PetService(PetRepository petRepository,
			VisitRepository visitRepository) {
		this.petRepository = petRepository;
		this.visitRepository = visitRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}
	
	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
			
		Pet otherPet=pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
            if (checkDuplicated(otherPet, pet)) {            	
            	throw new DuplicatedPetNameException();
            }else
                petRepository.save(pet);                
	}


	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

	public Collection<Pet> findPetByOwnerId(int ownerId) {		
		return this.petRepository.findByOwnerId(ownerId);
	}

	public Collection<Pet> findAllPets() {
		return this.petRepository.findAllPets();
	}
	
	@Transactional()
	 public Collection<Pet> findPetByGuideId(int guideId) throws DataAccessException {
			return petRepository.findPetByGuideId(guideId);
		}


}
