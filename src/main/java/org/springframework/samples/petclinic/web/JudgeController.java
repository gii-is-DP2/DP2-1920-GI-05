package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Jugdes;
import org.springframework.samples.petclinic.service.JugdeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JudgeController {
	
	private final JugdeService jugdeService;

	@Autowired
	public JudgeController(JugdeService clinicService) {
		this.jugdeService = clinicService;
	}

	@GetMapping(value = { "/jugdes" })
	public String showJugdeList(Map<String, Object> model) {
		Jugdes jugdes = new Jugdes();
		jugdes.getJugdeList().addAll(this.jugdeService.findJudges());
		model.put("jugdes", jugdes);
		return "jugdes/jugdeList";
	}

	@GetMapping(value = { "/jugdes.xml"})
	public @ResponseBody Jugdes showResourcesJugdeList() {
		Jugdes jugdes = new Jugdes();
		jugdes.getJugdeList().addAll(this.jugdeService.findJudges());
		return jugdes;
	}
}
