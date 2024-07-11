package fr.diginamic.hello.controleurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.services.HelloService;

@RestController
@RequestMapping("/saluer")
public class HelloControleur {
	
	@Autowired
	HelloService helloService;

	@GetMapping("/hello")
	public String direHello() {
		return helloService.salutations();
	}
	
	@GetMapping("/bonjour")
	public String direBonjour() {
		return "Bonjour, je m'appelle Chérhazad et je dis Bonjour !!";
	}

}
