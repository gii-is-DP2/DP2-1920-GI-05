package org.springframework.samples.petclinic.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationRepositoryTests {

	@Autowired
	ApplicationRepository applicationRepository;
	
	@Test
	public void shouldReturnAllApplications() throws Exception {
				
		Collection<Application> allApplications = this.applicationRepository.findAllApplications();
		
		assertThat(allApplications.size()).isEqualTo(5);	
	}

}
