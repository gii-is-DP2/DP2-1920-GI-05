package org.springframework.samples.petclinic.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.ReportController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerIntegrationTests {

	@Autowired
	private ReportController reportController;
	
	@Autowired
	private  JudgeService judgeService;
	
	@Autowired
	private  TournamentService tournamentService;
	
	@Autowired
	private  ReportService reportService;
	
	@Autowired
	private  OwnerService ownerService;
	
	@Autowired
	private  ApplicationService applicationService;
	
	@Autowired
	private  PetService petService;

	@Test
	void testInitJudgeReportList() throws Exception {
		
		ModelMap model=new ModelMap();		
		
		String view= this.reportController.reportListByJudge(1, model);		
		
		assertEquals(view,"reports/listJudge");
		assertNotNull(model.get("judge"));		
		assertNotNull(model.get("reports"));		
	}
	
	@WithMockUser(username="owner1",authorities= {"owner"})
	@Test
	void testInitOwnerReportList() throws Exception {
		
		ModelMap model=new ModelMap();		
		
		String view= this.reportController.reportListByOwner(model);
		
		assertEquals(view,"reports/listOwner");
		assertNotNull(model.get("reports"));		
	}
	
	@Test
	void testInitCreateReport() throws Exception {
		
		ModelMap model=new ModelMap();		
		
		String view= this.reportController.initCreationForm(1, 1, model);
		
		
		assertEquals(view,"reports/create");
		assertNotNull(model.get("pets"));		
		assertNotNull(model.get("report"));		
	}
	
   

    @Test
	void testProcessCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		Report report = new Report();
		Pet pet = this.petService.findPetById(12);
		report.setComments("Sample text");
		report.setPoints( 55);
		report.setPet(pet);
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view= this.reportController.processCreationForm(report, bindingResult, 1, 1, model);
		
		assertEquals(view,"redirect:/judges/{judgeId}/reports");
	}
    


    
 


    
	
}
