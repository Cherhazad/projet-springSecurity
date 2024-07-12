package fr.diginamic.hello.controller;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@GetMapping("/listeVilles")
	public ModelAndView getVilles() {
		Map<String, Object> model = new HashMap<>();
		model.put("villes", villeRepository.findAll());
		model.put("departements", depRepository.findAll());
		return new ModelAndView("ville/listeVilles", model);
		
	}
	
//	@GetMapping("/listeVilles")
//	public String getVilles(Model model) {
//		model.addAttribute("villes", villeRepository.findAll());
//		model.addAttribute("departements", depRepository.findAll());
//		return "ville/listeVilles";
//	}
	
	@GetMapping("/supprimerVille/{id}")
	public String getVilles(@PathVariable int id) {
		villeRepository.deleteById(id);
		return "redirect:/listeVilles";
	}
}
