package org.springframework.samples.petclinic.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.repository.ReportRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ReportRepositoryTests {

	@Autowired
	ReportRepository reportRepository;
	
	@Test
	public void shouldReturnJudgeReports() throws Exception {
				
		Collection<Report> judgeReports = this.reportRepository.findByJudgeId(1);
		
		assertThat(judgeReports.size()).isEqualTo(3);	
	}	
	
	@Test
	public void shouldReturnPetReports() throws Exception {
				
		Collection<Report> petReports = this.reportRepository.findByPetId(1);
		
		assertThat(petReports.size()).isEqualTo(2);	
	}	
	
}
