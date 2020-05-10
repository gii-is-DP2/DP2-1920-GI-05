package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Application;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.ApplicationService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReportController {
	
	private final ReportService reportService;
	private final TournamentService tournamentService;
	private final JudgeService judgeService;
	private final OwnerService ownerService;
	private final ApplicationService applicationService;

	@Autowired
	public ReportController(ReportService reportService, 
	TournamentService tournamentService, JudgeService judgeService,
	OwnerService ownerService, ApplicationService applicationService) {
		this.reportService = reportService;
		this.tournamentService = tournamentService;
		this.judgeService = judgeService;
		this.ownerService = ownerService;
		this.applicationService = applicationService;

	}

	@ModelAttribute("tournaments")
	public Collection<Tournament> populateTournaments() {
		return this.tournamentService.findActiveTournaments();
	}

	// Judge List

	@GetMapping(value = "/judges/{judgeId}/reports")
	public String reportListByJudge(@PathVariable("judgeId") int judgeId, ModelMap model) {
		
		Collection<Report> reports = reportService.findReportByJudgeId(judgeId);
		
		model.put("judge", judgeService.findJudgeById(judgeId));
		model.put("reports",reports);
		return "reports/listJudge";
	}

	// CRUD: Create

	@GetMapping(value = "/judges/{judgeId}/reports/{tournamentId}/new")
	public String initCreationForm(@PathVariable("judgeId") int judgeId,
	@PathVariable("tournamentId") int tournamentId, ModelMap model) {
		
		Report report = new Report();
		List<Pet> pets = new ArrayList<Pet>();
		List<Application> applications = applicationService.
		findApplicationsByTournamentId(tournamentId).stream().collect(Collectors.toList());

		for(Application a: applications){

			if(a.getStatus().equals("ACCEPTED") && !(thisPetHasAReport(
				a.getTournament(), a.getPet()))){
				pets.add(a.getPet());
			}
		}

		model.put("pets", pets);
		model.put("report", report);

		return "reports/create";
	}

	public Boolean thisPetHasAReport(Tournament tournament, Pet pet){

		Boolean res = false;
		List<Report> reports = reportService.findAll().stream().collect(Collectors.toList());

		for(Report r: reports){
			if(r.getTournament().equals(tournament) && r.getPet().getId().equals(pet.getId())){
				res = true;
			}
		}

		return res;
	}

	@PostMapping(value = "/judges/{judgeId}/reports/{tournamentId}/new")
	public String processCreationForm(@Valid Report report, BindingResult result, @PathVariable("judgeId") int judgeId,
	@PathVariable("tournamentId") int tournamentId,  ModelMap model) {		
		if (result.hasErrors()) {
			model.put("report", report);
			return "reports/create";
		}
		else {
				
			report.setJudge(judgeService.findJudgeById(judgeId));
			report.setTournament(tournamentService.findTournamentById(tournamentId));

            this.reportService.saveReport(report);
                    
            return "redirect:/judges/{judgeId}/reports";
		}
	}
	
	//Reports of my pets
	
	@GetMapping(value = "/myReports/")
	public String reportListByOwner(ModelMap model) {
		
		Collection<Report> reports = reportService.findAllReportsFromAnOwner(ownerService.findOwnerByUserName());
		model.put("reports",reports);
		return "reports/listOwner";
	}
	



}
