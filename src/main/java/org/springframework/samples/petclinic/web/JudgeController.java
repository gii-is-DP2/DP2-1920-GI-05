package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JudgeController {

	private static final String VIEWS_JUDGE_CREATE_OR_UPDATE_FORM = "judges/createOrUpdateJudgeForm";

	private final JudgeService judgeService;
	private final TournamentService tournamentService;
	private final UserService userService;

	@Autowired
	public JudgeController(TournamentService tournamentService,JudgeService judgeService, UserService userService, AuthoritiesService authoritiesService) {
		this.judgeService = judgeService;	
		this.tournamentService = tournamentService;
		this.userService = userService;
	}
	
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = { "/judges" })
	public String showJugdeList(Map<String, Object> model) {
		Collection<Judge> judges = (this.judgeService.findAllJudges());
		model.put("judges", judges);
		System.out.println(judges);
		return "judges/list";
	}

	@GetMapping(value = "/judges/new")
	public String initCreationForm(Map<String, Object> model) {
		Judge judge = new Judge();
		model.put("judge", judge);
		return "judges/createOrUpdateJudgeForm";
	}

	@PostMapping(value = "/judges/new")
	public String processCreationForm(@Valid Judge judge, BindingResult result, ModelMap model) {
		
		if (this.userService.isUsernameTaken(judge.getUser().getUsername())) {
			model.put("judge", judge);
			model.put("usernameTakenError", "username taken");
			return "judges/createOrUpdateJudgeForm";
		}
		
		else if (result.hasErrors()) {
			return "judges/createOrUpdateJudgeForm";
		} else {
			// creating judge, user and authorities
			this.judgeService.saveJudge(judge);

			return "welcome";
		}
	}

	@GetMapping(value = "/judges/{judgeId}/edit")
	public String initUpdateJudgeForm(@PathVariable("judgeId") int judgeId, Model model) {
		Judge judge =  this.judgeService.findJudgeById(judgeId);
		model.addAttribute(judge);
		return VIEWS_JUDGE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/judges/{judgeId}/edit")
	public String processUpdateGuideForm(@Valid Judge judge, BindingResult result,
			@PathVariable("judgeId") int judgeId) {
		if (result.hasErrors()) {
			return VIEWS_JUDGE_CREATE_OR_UPDATE_FORM;
		} else {
			judge.setId(judgeId);
			this.judgeService.saveJudge(judge);
			return "redirect:/judges/details";
		}
	}

	@GetMapping("/judges/details")
	public ModelAndView showJudge() {
		ModelAndView mav = new ModelAndView("judges/judgeDetails");
		mav.addObject(this.judgeService.findJudgeByUserName());
		return mav;
	}
	
	@GetMapping(value = "/judges/{judgeId}/tournaments")
	public String initTournaments4Judge(@PathVariable("judgeId") int judgeId, Map<String, Object> model) {
		
		Collection<Tournament> tournaments = tournamentService.findTournamentByJudgeId(judgeId);
		model.put("tournaments",tournaments);
		model.put("judge", judgeService.findJudgeById(judgeId));
		return "judges/tournaments";
	}

}
