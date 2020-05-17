package org.springframework.samples.petclinic.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Ranking;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.RankingService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/judges/{judgeId}/tournaments/{tournamentId}")
public class RankingController {

	private final RankingService rankingService;
	private final TournamentService tournamentService;
	private final JudgeService judgeService;

	@Autowired
	public RankingController(RankingService rankingService, 
	TournamentService tournamentService, JudgeService judgeService) {
		this.rankingService = rankingService;
		this.tournamentService = tournamentService;
		this.judgeService = judgeService;
	}
                

	@ModelAttribute("judge")
	public Judge findJudge(@PathVariable("judgeId") int judgeId) {
		return this.judgeService.findJudgeById(judgeId);
	}
                
	@InitBinder("judge")
	public void initJudgeBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("tournament")
	public Tournament findTournament(@PathVariable("tournamentId") int tournamentId) {
		return this.tournamentService.findTournamentById(tournamentId);
	}

	@InitBinder("tournament")
	public void initTournamentBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

 	@InitBinder("ranking")
	public void initRankingBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RankingValidator());
	} 



}
