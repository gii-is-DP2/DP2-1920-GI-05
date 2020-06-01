package org.springframework.samples.petclinic.report;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.PetFormatter;
import org.springframework.samples.petclinic.web.ReportController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = ReportController.class, 
includeFilters = @ComponentScan.Filter(value = {PetFormatter.class}, type = FilterType.ASSIGNABLE_TYPE), 
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), 
excludeAutoConfiguration = SecurityConfiguration.class)
public class ReportControllerTests {

	private static final int TEST_JUDGE_ID = 1;
	
	private static final int TEST_PET_ID_1 = 1;
	
	private static final int TEST_PET_ID_2 = 2;	
	
	private static final int TEST_OWNER_ID = 1;
	
	private static final int TEST_TOURNAMENT_ID = 1;
	


	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private  ReportService reportService;
	
	@MockBean
	private TournamentService tournamentService;
	
	@MockBean
	private  OwnerService ownerService;
	
	@MockBean
	private  ApplicationService applicationService;
	
	@MockBean
	private  PetService petService;
	
	@MockBean
	private JudgeService judgeService;

	@BeforeEach
	void setup() {
		
		Pet petWithoutReport = new Pet();
		petWithoutReport.setId(TEST_PET_ID_1);
		petWithoutReport.setName("Leo");
	
		Pet petWithReport = new Pet();
		petWithReport.setId(TEST_PET_ID_2);
		petWithReport.setName("Pau");
		
		given(this.petService.findAllPets()).willReturn(Lists.newArrayList(petWithoutReport));
		given(this.petService.findPetByOwnerId(TEST_OWNER_ID)).willReturn(Lists.newArrayList(petWithoutReport));
		Owner owner = new Owner();
		owner.setId(TEST_OWNER_ID);
		owner.setLastName("Michael");
		given(this.ownerService.findOwnerByUserName()).willReturn(owner);
		
				
		Tournament tournament = new Tournament();
		tournament.setId(TEST_TOURNAMENT_ID);
		given(this.tournamentService.findTournamentById(TEST_TOURNAMENT_ID)).willReturn(tournament);
		given(this.tournamentService.findTournamentByJudgeId(TEST_JUDGE_ID)).willReturn(Lists.newArrayList(tournament));
		given(this.tournamentService.findActiveTournaments()).willReturn(Lists.newArrayList(tournament));
		
		Report report = new Report();
		report.setId(20);
		report.setPet(petWithReport);
		report.setTournament(tournament);
		given(this.reportService.findAll()).willReturn(Lists.newArrayList(report));
		given(this.reportService.findReportByJudgeId(TEST_JUDGE_ID)).willReturn(Lists.newArrayList(report));
		given(this.reportService.findAllReportsFromAnOwner(owner)).willReturn(Lists.newArrayList(report));
		given(this.reportService.findByPetId(TEST_PET_ID_1)).willReturn(Lists.newArrayList());
		given(this.reportService.findByPetId(TEST_PET_ID_2)).willReturn(Lists.newArrayList(report));
		Application application = new Application();
		application.setId(12);
		//Sin esto el test de crear report no funciona
		application.setStatus("ACCEPTED");
		given(this.applicationService.findApplicationsByTournamentId(TEST_TOURNAMENT_ID)).willReturn(Lists.newArrayList(application));

	}
	
	@WithMockUser(value = "spring")
	@Test
	void testListJudgeReports() throws Exception {
		mockMvc.perform(get("/judges/{judgeId}/reports", TEST_JUDGE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("reports")).andExpect(view().name("reports/listJudge"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testListOwnerReports() throws Exception {
		mockMvc.perform(get("/myReports/")).andExpect(status().isOk())
				.andExpect(model().attributeExists("reports")).andExpect(view().name("reports/listOwner"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testGetCreateReport() throws Exception {
		mockMvc.perform(get("/judges/{judgeId}/reports/{tournamentsId}/new", TEST_JUDGE_ID, TEST_TOURNAMENT_ID)).andExpect(status().isOk())
		.andExpect(view().name("reports/create")).andExpect(model().attributeExists("report"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testPostCreateReport() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Leo")
		.param("points", "100")
		.param("comments", "Perfect performances"))
		//.andExpect(model().attributeHasNoErrors("report"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/judges/{judgeId}/reports"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testPostCreateReportHigherPoints() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Leo")
		.param("points", "101")
		.param("comments", "Perfect performances"))
		.andExpect(model().attributeHasErrors("report"))
		.andExpect(status().isOk())
		.andExpect(view().name("reports/create"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testPostCreateReportNegativePoints() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Leo")
		.param("points", "-89")
		.param("comments", "Nothing"))
		.andExpect(model().attributeHasErrors("report"))
		.andExpect(status().isOk())
		.andExpect(view().name("reports/create"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testPostCreateReportBlankComment() throws Exception {
		mockMvc.perform(post("/judges/{judgeId}/reports/{tournamentsId}/new",  TEST_JUDGE_ID, TEST_TOURNAMENT_ID)
		.with(csrf())
		.param("pet", "Leo")
		.param("points", "89")
		.param("comments", ""))
		.andExpect(model().attributeHasErrors("report"))
		.andExpect(status().isOk())
		.andExpect(view().name("reports/create"));
	}
	

	
	
}
