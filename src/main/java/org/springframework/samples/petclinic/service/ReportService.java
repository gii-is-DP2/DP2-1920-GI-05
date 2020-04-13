package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
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
	
	public List<Report> findByPetId(int id){
		return this.reportRepository.findByPetId(id);
	}
	
	public List<Report> findAllReportsFromAnOwner(Owner owner){
		List<Report> reports = new ArrayList<Report>();
		for (Pet p : owner.getPets()) {
			reports.addAll(findByPetId(p.getId()));
		}
		return reports;
	}
	
	

}
