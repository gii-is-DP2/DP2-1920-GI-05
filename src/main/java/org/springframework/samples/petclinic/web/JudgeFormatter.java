package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.stereotype.Component;

@Component
public class JudgeFormatter implements Formatter<Judge> {

	private final JudgeService judgeService;

	@Autowired
	public JudgeFormatter(JudgeService judgeService) {
		this.judgeService = judgeService;
	}

	@Override
	public String print(Judge judge, Locale locale) {
		return judge.getLastName() ;
	}

	@Override
	public Judge parse(String text, Locale locale) throws ParseException {
		Collection<Judge> findJudges = this.judgeService.findAllJudges();
		for (Judge Judge : findJudges) {
			if (Judge.getLastName().equals(text)) {
				return Judge;
			}
		}
		throw new ParseException("Judge not found: " + text, 0);
	}

}
