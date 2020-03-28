package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ApplicationServiceTests { 
	
    @Autowired
	protected ApplicationService applicationService;
                

	@Test
	void shouldFindAllApplications() {
		Collection<Application> applications = this.applicationService.findAllApplications();
		assertThat(applications.size()).isEqualTo(5);
	}

}
