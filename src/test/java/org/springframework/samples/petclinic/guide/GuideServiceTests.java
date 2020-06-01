package org.springframework.samples.petclinic.guide;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
class GuideServiceTests {                
        @Autowired
	protected GuideService guideService;

    
	@Test
	@Transactional
	void shouldUpdateguide() {
		Guide guide = this.guideService.findGuideById(1);
		String oldLastName = guide.getLastName();
		String newLastName = oldLastName + "X";

		guide.setLastName(newLastName);
		this.guideService.saveGuide(guide);

		// retrieving new name from database
		guide = this.guideService.findGuideById(1);
		assertThat(guide.getLastName()).isEqualTo(newLastName);
	}


}
