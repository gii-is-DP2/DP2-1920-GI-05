package org.springframework.samples.petclinic.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ReportServiceTests {
	
	@Autowired
	protected ReportService reportService;
	
	@Autowired
	protected OwnerService  ownerService;
	
	@Autowired
	protected PetService petService;
	
	@Autowired
	protected TournamentService  tournamentService;
	
	@Autowired
	protected JudgeService judgeService;

	//  Service test: List reports by judge
	@Test
	@Transactional
	void shouldFindJudgeReports() {
		Collection<Report> judgeReports = this.reportService.findReportByJudgeId(1);
		assertThat(judgeReports.size()).isEqualTo(3);
	}
	
	//  Service test: List reports by pet
	@Test
	@Transactional
	void shouldFindPetReports() {
		Collection<Report> petReports = this.reportService.findByPetId(1);
		assertThat(petReports.size()).isEqualTo(2);
	}
	
	//  Service test: List reports by owner
	@Test
	@Transactional
	void shouldFindOwnerReports() {

		Owner owner1 = this.ownerService.findOwnerById(1);
		
		Collection<Report> ownerReports = this.reportService.findAllReportsFromAnOwner(owner1);
		assertThat(ownerReports.size()).isEqualTo(2);
	}
	
	//  Service test: Save report
	@Test
	@Transactional
	void shouldSaveReport() {

		//Save a report for the dog (pet2) from the tournament 2 by judge 1
		//Owner owner1 = this.ownerService.findOwnerById(1);
		Pet pet2 = this.petService.findPetById(2);
		Judge judge1 = this.judgeService.findJudgeById(1);
		Tournament tournament2 = this.tournamentService.findTournamentById(2);
		
		Report report = new Report();
		report.setComments("Well done");
		report.setPoints(72);
		report.setId(21);
		report.setJudge(judge1);
		report.setPet(pet2);
		report.setTournament(tournament2);
		
		this.reportService.saveReport(report);
		Collection<Report> reports = this.reportService.findAll();
		assertThat(reports.size()).isEqualTo(7);
		
	}
	
	
}
