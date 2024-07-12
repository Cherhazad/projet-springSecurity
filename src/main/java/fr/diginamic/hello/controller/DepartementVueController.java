package fr.diginamic.hello.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import fr.diginamic.hello.repositories.DepartementRepository;

@Controller
public class DepartementVueController {
	
	@Autowired
	private DepartementRepository depRepository;
	
	@GetMapping("/depList")
	public ModelAndView getDepartement() {
		Map<String, Object> model = new HashMap<>();
		model.put("departments", depRepository.findAll());
		return new ModelAndView("department/depList", model);
		
	}
		
//	@GetMapping("/deleteDep/{codeDep}") // TODO corriger pour pouvoir passer l'id ici 
//	public String getDepartement(@PathVariable String codeDep) {
//		depRepository.deleteByCodeDep(codeDep);
//		return "redirect:/depList";
//	}
}
