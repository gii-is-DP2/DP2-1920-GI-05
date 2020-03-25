/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Guide;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.GuideService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class GuideController {

	private static final String VIEWS_GUIDE_CREATE_OR_UPDATE_FORM = "guides/createOrUpdateGuideForm";

	private final GuideService guideService;

	@Autowired
	public GuideController(GuideService guideService, UserService userService, AuthoritiesService authoritiesService) {
		this.guideService = guideService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/guides/new")
	public String initCreationForm(Map<String, Object> model) {
		Guide guide = new Guide();
		model.put("guide", guide);
		return VIEWS_GUIDE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/guides/new")
	public String processCreationForm(@Valid Guide guide, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_GUIDE_CREATE_OR_UPDATE_FORM;
		}
		else {
			//creating guide, user and authorities
			this.guideService.saveGuide(guide);
			
			return "redirect:/guides/" + guide.getId();
		}
	}

	@GetMapping(value = "/guides/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("guide", new Guide());
		return "guides/findGuides";
	}

	@GetMapping(value = "/guides")
	public String processFindForm(Guide guide, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /guides to return all records
		if (guide.getLastName() == null) {
			guide.setLastName(""); // empty string signifies broadest possible search
		}

		// find guides
		Collection<Guide> results = this.guideService.findAllGuides();
		if (results.isEmpty()) {
			// no guides found
			result.rejectValue("lastName", "notFound", "not found");
			return "guides/all";
		}
		else if (results.size() == 1) {
			// 1 guide found
			guide = results.iterator().next();
			return "redirect:/guides/" + guide.getId();
		}
		else {
			// multiple guides found
			model.put("selections", results);
			return "guides/all";
		}
	}

	@GetMapping(value = "/guides/{guideId}/edit")
	public String initUpdateGuideForm(@PathVariable("guideId") int guideId, Model model) {
		Guide guide = this.guideService.findGuideById(guideId);
		model.addAttribute(guide);
		return VIEWS_GUIDE_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/guides/{guideId}/edit")
	public String processUpdateGuideForm(@Valid Guide guide, BindingResult result,
			@PathVariable("guideId") int guideId) {
		if (result.hasErrors()) {
			return VIEWS_GUIDE_CREATE_OR_UPDATE_FORM;
		}
		else {
			guide.setId(guideId);
			this.guideService.saveGuide(guide);
			return "redirect:/guides/{guideId}";
		}
	}



}