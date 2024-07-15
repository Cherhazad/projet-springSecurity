package fr.diginamic.hello.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;

@Controller
public class VillesVueController {

	@Autowired
	private VilleRepository villeRepository;
	
	@Autowired
	private DepartementRepository depRepository;
	
	@GetMapping("/townList")
	public ModelAndView getVilles() {
		Map<String, Object> model = new HashMap<>();
		model.put("towns", villeRepository.findAll());
		model.put("departments", depRepository.findAll());
		return new ModelAndView("town/townList", model);
		
	}

	@GetMapping("/deleteTown/{id}")
	public String getVilles(@PathVariable int id) {
		villeRepository.deleteById(id);
		return "redirect:/townList";
	}
}
