package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.service.ReportService;
import org.springframework.stereotype.Component;

@Component
public class ReportFormatter implements Formatter<Report> {

	private final ReportService reportService;

	@Autowired
	public ReportFormatter(ReportService reportService) {
		this.reportService = reportService;
	}

	@Override
	public String print(Report report, Locale locale) {
		return report.getJudge().toString() + report.getTournament().toString();
	}

	@Override
	public Report parse(String text, Locale locale) throws ParseException {
		Collection<Report> findReports = this.reportService.findAll();
		for (Report report : findReports) {
			if ((report.getJudge().toString() + report.getTournament().toString()).equals(text)) {
				return report;
			}
		}
		throw new ParseException("Report not found: " + text, 0);
	}

}
