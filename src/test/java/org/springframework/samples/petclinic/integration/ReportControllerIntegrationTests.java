package org.springframework.samples.petclinic.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.ReportController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportControllerIntegrationTests {

	private static final int TEST_PET_ID_1 = 1;

	private static final int TEST_JUDGE_ID = 1;

	private static final int TEST_TOURNAMENT_ID = 1;

	@Autowired
	private ReportController reportController;

	@Autowired
	private PetService petService;
	
	@Autowired
	private TournamentService tournamentService;
	
	@Autowired
	private JudgeService judgeService;
	
	
    @Test
	void testInitCreationForm() throws Exception {
		
		ModelMap model=new ModelMap();
		
		String view=reportController.initCreationForm(TEST_JUDGE_ID, TEST_TOURNAMENT_ID, model);
		
		assertEquals(view,"reports/create");
		assertNotNull(model.get("report"));		
	}

    @Test
	void testProcessCreationFormSuccess() throws Exception {

		ModelMap model = new ModelMap();
    	
    	Report newReport = new Report();
		
		newReport.setPoints(50);
		newReport.setComments("Bien comentado");
		newReport.setJudge(judgeService.findJudgeById(TEST_JUDGE_ID));
		newReport.setTournament(tournamentService.findTournamentById(TEST_TOURNAMENT_ID));
    	newReport.setPet(petService.findPetById(TEST_PET_ID_1));
	 	
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		
		String view=reportController.processCreationForm(
			newReport, bindingResult, TEST_JUDGE_ID, TEST_TOURNAMENT_ID, model);
		
    	
		assertEquals(view,"redirect:/judges/{judgeId}/reports");				
	}

	
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		
		ModelMap model=new ModelMap();

    	Report newReport = new Report();
		
		newReport.setComments("Bien comentado");
		newReport.setJudge(judgeService.findJudgeById(TEST_JUDGE_ID));
		newReport.setTournament(tournamentService.findTournamentById(TEST_TOURNAMENT_ID));
		newReport.setPet(petService.findPetById(TEST_PET_ID_1));
		
		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
		bindingResult.reject("points", "Required!");
		
		String view=reportController.processCreationForm(
			newReport, bindingResult, TEST_JUDGE_ID, TEST_TOURNAMENT_ID, model);
		
		assertEquals(view,"reports/create");		
	}

 
}