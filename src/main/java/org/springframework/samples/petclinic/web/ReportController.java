package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.model.Tournament;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.samples.petclinic.service.TournamentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

	@Autowired
	public ReportController(ReportService reportService, 
	TournamentService tournamentService, JudgeService judgeService, OwnerService ownerService) {
		this.reportService = reportService;
		this.tournamentService = tournamentService;
		this.judgeService = judgeService;
		this.ownerService = ownerService;

	}

	@ModelAttribute("tournaments")
	public Collection<Tournament> populateTournaments() {
		return this.tournamentService.findActiveTournaments();
	}

	// Judge List

	@GetMapping(value = "/judges/{judgeId}/reports")
	public String initReport4Judge(@PathVariable("judgeId") int judgeId, Map<String, Object> model) {
		
		Collection<Report> reports = reportService.findReportByJudgeId(judgeId);
		
		model.put("judge", judgeService.findJudgeById(judgeId));
		model.put("reports",reports);
		return "reports/listJudge";
	}

	// CRUD: Create

	@GetMapping(value = "/judges/{judgeId}/reports/new")
	public String initCreationForm(@PathVariable("judgeId") int judgeId, ModelMap model) {

		Report report = new Report();

		model.put("report", report);

		return "reports/create";
	}

	@PostMapping(value = "/judges/{judgeId}/reports/new")
	public String processCreationForm(@Valid Report report,
	@PathVariable("judgeId") int judgeId, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("report", report);
			return "reports/create";
		}
		else {
				
			report.setJudge(judgeService.findJudgeById(judgeId));
            this.reportService.saveReport(report);
                    
            return "redirect:/judges/{judgeId}/reports";
		}
	}
	
	//Reports of my pets
	
	@GetMapping(value = "/myReports/")
	public String initMyReports(Map<String, Object> model) {
		
		Collection<Report> reports = reportService.findAllReportsFromAnOwner(ownerService.findOwnerByUserName());
		model.put("reports",reports);
		return "reports/listOwner";
	}
	



}
