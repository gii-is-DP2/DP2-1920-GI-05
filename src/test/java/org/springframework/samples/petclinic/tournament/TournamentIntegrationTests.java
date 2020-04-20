package org.springframework.samples.petclinic.tournament;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Money;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.CategoryService;
import org.springframework.samples.petclinic.service.FieldService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.TournamentController;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TournamentIntegrationTests {

	private static final int TEST_JUDGE_ID = 1;
	
	@Autowired
	private TournamentController tournamentController;
	
	@Autowired
	private  TournamentService tournamentService;
	
	@Autowired
	private  CategoryService categoryService;
	
	@Autowired
	private  PetService petService;
	
	@Autowired
	private  FieldService fieldService;
	
	@Autowired
	private  JudgeService  judgeService;
	
	@Autowired
	private  GuideService  guideService;
	
	@Autowired
	private  OwnerService ownerService;
	
	@Autowired
	private  ApplicationService applicationService;


	
	@Test
	void testAllTournaments() throws Exception {
		
		ModelMap model=new ModelMap();		
		String view= this.tournamentController.showAllTournaments(model);
				
		assertEquals(view,"tournaments/list");
		assertNotNull(model.get("tournaments"));		
	}
	
	@Test
	void testActiveTournaments() throws Exception {
		
		ModelMap model=new ModelMap();		
		String view= this.tournamentController.showActiveTournaments(model);
				
		assertEquals(view,"tournaments/list");
		assertNotNull(model.get("tournaments"));		
	}
	
	@Test 
	void testInitCreateTournament() throws Exception {
		
		ModelMap model=new ModelMap();		
		String view= this.tournamentController.initCreationForm(model);
		
		assertEquals(view,"tournaments/createOrUpdateTournamentForm");
		assertNotNull(model.get("tournament"));		
	}
	
	@Test 
	void testProcessCreateSuccess() throws Exception {
		
		ModelMap model=new ModelMap();		
		Tournament t = new Tournament();
		PetType pt = this.petService.findPetTypes().iterator().next();
		Category c = this.categoryService.findCategoryById(1);
		
		t.setApplyDate(LocalDate.of(2020, 10, 12));
		t.setCategory(c);
		t.setEndDate(LocalDate.of(2020, 12, 12));
		t.setLocation("New York");
		t.setPetType(pt);
		t.setName("Sample tournament");
		
		Money m = new Money();		
		m.setAmount(1000.00);
		m.setCurrency("EUR");
		
		t.setPrize(m);

		t.setStartDate(LocalDate.of(2020, 12, 10));		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view= this.tournamentController.processCreationForm(t, bindingResult, model);
		
		assertEquals(view,"redirect:/tournaments/all");
	}
	
	@Test 
	void testShouldNotCreateName() throws Exception {
		
		ModelMap model=new ModelMap();		
		Tournament t = new Tournament();
		PetType pt = this.petService.findPetTypes().iterator().next();
		Category c = this.categoryService.findCategoryById(1);
		
		t.setApplyDate(LocalDate.of(2020, 10, 12));
		t.setCategory(c);
		t.setEndDate(LocalDate.of(2020, 12, 12));
		t.setLocation("New York");
		t.setPetType(pt);
		t.setName("");
		
		Money m = new Money();		
		m.setAmount(1000.00);
		m.setCurrency("EUR");
		
		t.setPrize(m);

		t.setStartDate(LocalDate.of(2020, 12, 10));		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view= this.tournamentController.processCreationForm(t, bindingResult, model);
		
		assertEquals(view,"tournaments/createOrUpdateTournamentForm");
	}
	
	@Test 
	void testShouldNotCreateLocation() throws Exception {
		
		ModelMap model=new ModelMap();		
		Tournament t = new Tournament();
		PetType pt = this.petService.findPetTypes().iterator().next();
		Category c = this.categoryService.findCategoryById(1);
		
		t.setApplyDate(LocalDate.of(2020, 10, 12));
		t.setCategory(c);
		t.setEndDate(LocalDate.of(2020, 12, 12));
		t.setLocation("");
		t.setPetType(pt);
		t.setName("Sample tournament");
		
		Money m = new Money();		
		m.setAmount(1000.00);
		m.setCurrency("EUR");
		
		t.setPrize(m);

		t.setStartDate(LocalDate.of(2020, 12, 10));		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view= this.tournamentController.processCreationForm(t, bindingResult, model);
		
		assertEquals(view,"tournaments/createOrUpdateTournamentForm");
	}
	
   

    
    
 


    
	
}
