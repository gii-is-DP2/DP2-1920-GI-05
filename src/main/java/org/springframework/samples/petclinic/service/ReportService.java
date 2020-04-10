package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Report;
import org.springframework.samples.petclinic.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

	private ReportRepository reportRepository;

	@Autowired
	public ReportService(ReportRepository reportRepository) {
		this.reportRepository = reportRepository;
	}
	
	@Transactional
	public void saveReport(Report report) throws DataAccessException {
		reportRepository.save(report);
	}

	@Transactional(readOnly = true)
	public Report findReportById(int id) throws DataAccessException {
		return reportRepository.findById(id);
	}

	public Collection<Report> findReportByJudgeId(int judgeId) {		
		return this.reportRepository.findByJudgeId(judgeId);
	}

	public Collection<Report> findAll() {
		return this.reportRepository.findAll();
	}

}
