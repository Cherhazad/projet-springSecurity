package fr.diginamic.hello.controleurs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.hello.entites.Ville;
import jakarta.validation.Valid;

//@RestController
//@RequestMapping("/villes")
public class VilleController {

	List<Ville> listeVilles = new ArrayList<>(); // utiliser List.of return une erreur null car une liste est immutable.

	public VilleController() {
		listeVilles.add(new Ville(1, "Montpellier", 36000));
		listeVilles.add(new Ville(2, "Paris", 85000));
	}

	// méthodes GET

	@GetMapping
	public List<Ville> listerVilles() {
		return listeVilles;
	}

	@GetMapping(path = "/toutesVilles/{id}")
	public Ville trouverVilleParId(@PathVariable int id) {
		for (Ville ville : listeVilles) {
			if (ville.getId() == id) {
				return ville;
			}
		}
		return null;
	}

	// méthodes POST

	@PostMapping // ("/insertVille") pas besoin de mettre ce complément d'url si on n'a qu'une
					// seule fois le post.
	public ResponseEntity<String> insertVille(@Valid @RequestBody Ville nvVille, BindingResult controleQualite) {

//		for (Ville ville : listeVilles) {
//			if (ville.getNom().equals(nvVille.getNom()) || ville.getId() == nvVille.getId()) {
//
//				return ResponseEntity.badRequest().body("La ville existe déjà");
//			}
//			
//		}
//		listeVilles.add(nvVille);
//		return ResponseEntity.ok("Ville insérée avec succès");

		if (controleQualite.hasErrors()) {
			return ResponseEntity.badRequest().body(controleQualite.getAllErrors().stream()
					.map(e -> e.getDefaultMessage()).collect(Collectors.joining("\n")));
		}

		if (listeVilles.contains(nvVille)) { // il faut alors redéfinir la méthode equals dans la classe Ville (voir
												// corrigé obsidian).
			return ResponseEntity.badRequest().body("La ville " + nvVille.getNom() + " existe déjà.");
		}
		listeVilles.add(nvVille);
		System.out.println(listeVilles);
		return ResponseEntity.ok("Ville insérée avec succès.");

	}

	// méthodes PUT

	@PutMapping("/{id}") // ("/modifierVille/{id}")
	public ResponseEntity<String> modifierVille(@PathVariable int id, @Valid @RequestBody Ville modifVille,
			BindingResult controleQualite) { // @PathVariable int id,

		if (controleQualite.hasErrors()) { // va récupérer les messages personnalisés précisés pour les attributs dans la classe Ville
			ResponseEntity.badRequest().body(controleQualite.getAllErrors().stream().map(e -> e.getDefaultMessage())
					.collect(Collectors.joining("\n")));
		}

		Ville ville = listeVilles.stream().filter(v -> v.getId() == modifVille.getId()).findFirst().orElse(null);
		if (ville != null) {
			ville.setNbHabitants(modifVille.getNbHabitants());
			ville.setNom(modifVille.getNom());
			System.out.println(listeVilles);
			return ResponseEntity.ok("Ville modifiée avec succès");
		} else {
			return ResponseEntity.badRequest().body("La ville d'identifiant " + modifVille.getId() + " n'existe pas.");
		}

	}

	// méthodes DELETE

	@DeleteMapping("/{id}") // ("/supprimerVille/{id}")
	public ResponseEntity<String> supprimerVille(@PathVariable int id) {
		Ville ville = listeVilles.stream().filter(v -> v.getId() == id).findFirst().orElse(null);
		if (ville != null) {
			listeVilles.remove(ville);
			System.out.println(listeVilles);
		}
		return ResponseEntity.ok("La ville a été supprimée avec succès");
	}

}
