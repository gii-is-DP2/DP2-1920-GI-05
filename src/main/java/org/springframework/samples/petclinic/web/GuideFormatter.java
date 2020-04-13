package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.model.Judge;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.JudgeService;
import org.springframework.stereotype.Component;

@Component
public class GuideFormatter implements Formatter<Guide>{

	private final GuideService guideService;

	@Autowired
	public GuideFormatter(GuideService guideService) {
		this.guideService = guideService;
	}

	@Override
	public String print(Guide guide, Locale locale) {
		return guide.getLastName() ;
	}

	@Override
	public Guide parse(String text, Locale locale) throws ParseException {
		Collection<Guide> findGuides = this.guideService.findAllGuides();
		for (Guide guide : findGuides) {
			if (guide.getLastName().equals(text)) {
				return guide;
			}
		}
		throw new ParseException("Guide not found: " + text, 0);
	}
}
