package org.springframework.samples.petclinic.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.repository.ApplicationRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
//Para este tests hemos tenido que comentar el includeFilters porque provocaba fallos en otros tests
//@DataJpaTest(includeFilters = @ComponentScan.Filter(Repository.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ApplicationRepositoryTests {

	@Autowired
	ApplicationRepository applicationRepository;
	
	// Repository test: List all applications
	@Test
	public void shouldReturnAllApplications() throws Exception {
				
		Collection<Application> allApplications = this.applicationRepository.findAllApplications();
		
		assertThat(allApplications.size()).isEqualTo(9);	
	}
	
	// Repository test: List all applications by one owner
	@Test
	public void shouldReturnOwnerApplications() throws Exception {
				
		Collection<Application> owner1Applications = this.applicationRepository.findApplicationsByOwnerId(1);
		
		assertThat(owner1Applications.size()).isEqualTo(2);	
	}

}
