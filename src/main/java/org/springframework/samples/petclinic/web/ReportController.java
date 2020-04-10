package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReportController {
	
	private final ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;

	}

	@GetMapping(value = "/judges/{judgeId}/reports")
	public String initTournaments4Judge(@PathVariable("judgeId") int judgeId, Map<String, Object> model) {
		
		Collection<Report> reports = reportService.findReportByJudgeId(judgeId);
		model.put("reports",reports);
		return "reports/listJudge";
	}



}
