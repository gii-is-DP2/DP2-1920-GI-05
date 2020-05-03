package org.springframework.samples.petclinic.judge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Category;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.web.JudgeController;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JudgeControllerIntegrationTests {

	private static final int TEST_JUDGE_ID = 1;
	
	@Autowired
	private JudgeController judgeController;
	
	@Autowired
	private  JudgeService judgeService;
	
	@Autowired
	private  TournamentService tournamentService;


	
	@Test	
	void testTournamentJudgeList() throws Exception {
		
		ModelMap model=new ModelMap();		
		String view= judgeController.initTournaments4Judge(TEST_JUDGE_ID, model);
		
		
		assertEquals(view,"judges/tournaments");
		assertNotNull(model.get("tournaments"));		
	}
	
   

    @Test
	void testInitCreationForm() throws Exception {

		ModelMap model=new ModelMap();		
		String view= judgeController.initCreationForm(model);
		
		assertEquals(view,"judges/createOrUpdateJudgeForm");
		assertNotNull(model.get("judge"));		
	}
    

    @Test    
    @Transactional
	void testProcessCreationFormSuccess()  throws Exception {
    	Judge j= new Judge();
    	ModelMap model=new ModelMap();		
    	
    	org.springframework.samples.petclinic.model.User u = new 
    			org.springframework.samples.petclinic.model.User();
    	u.setUsername("finn");
    	u.setPassword("jollyRoger");
    	
    	j.setAddress("address");
    	j.setCity("Chicago");
    	j.setFirstName("Roger");
    	j.setLastName("Finn");
    	j.setTelephone("941321444");
    	j.setUser(u);

    	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
    	String view= this.judgeController.processCreationForm(j, bindingResult);
 
		Collection<Judge> all = this.judgeService.findAllJudges();
		assertThat(all.size()).isEqualTo(3);
		assertEquals(view,"welcome");				
	}
    
 


    
	
}
