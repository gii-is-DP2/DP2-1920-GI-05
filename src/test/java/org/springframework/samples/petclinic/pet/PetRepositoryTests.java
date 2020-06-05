package org.springframework.samples.petclinic.pet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PetRepositoryTests {

	@Autowired
	PetRepository petRepository;

	// Repository test: Return only active tournaments
	@Test
	public void shouldReturnPetTypes() throws Exception {
				
		List<PetType> petTypes = this.petRepository.findPetTypes();
		
		assertThat(petTypes.size()).isEqualTo(7);
	}
	
	@Test
	public void shouldReturnPetsByOwner() throws Exception {
				
		List<Pet> pets = this.petRepository.findByOwnerId(1);
		
		assertThat(pets.size()).isEqualTo(2);
	}
	
	@Test
	public void shouldReturnAllPets() throws Exception {
				
		List<Pet> pets = this.petRepository.findAllPets();
		
		assertThat(pets.size()).isEqualTo(18);
	}
	
	@Test
	public void shouldReturnPetsByGuide() throws Exception {
				
		Collection<Pet> pets = this.petRepository.findPetByGuideId(1);
		
		assertThat(pets.size()).isEqualTo(2);
	}
	

}
