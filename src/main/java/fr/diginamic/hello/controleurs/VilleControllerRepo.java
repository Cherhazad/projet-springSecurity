package fr.diginamic.hello.controleurs;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import fr.diginamic.hello.dto.VilleTP6Dto;
import fr.diginamic.hello.entites.VilleTP6;
import fr.diginamic.hello.exceptions.AnomalieException;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import fr.diginamic.hello.services.VilleMapper;
import fr.diginamic.hello.services.VilleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ville")
public class VilleControllerRepo {

	@Autowired
	private VilleRepository villeRepository;

	@Autowired
	private DepartementRepository depRepository;

	@Autowired
	private VilleService villeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Operation(summary = "Extraire la liste des villes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Liste des villes au format JSON", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VilleTP6Dto.class)) }) })
	@GetMapping
	public ResponseEntity<String> extraireVilles() {
		return ResponseEntity.ok(villeRepository.findAll().stream().map(VilleMapper::villeToDto)
				.collect(Collectors.toList()).toString());
	}

	@GetMapping("/pagination")
	public ResponseEntity<String> extraireVilles(@RequestParam int page, @RequestParam int size)
			throws JsonProcessingException {

		Page<VilleTP6Dto> villesDto = villeRepository.findAll(PageRequest.of(page, size)).map(VilleMapper::villeToDto);
		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		String json = writer.writeValueAsString(villesDto);
		return ResponseEntity.ok(json);
	}

	@GetMapping("/parId/{id}")
	public ResponseEntity<String> extraireVilleParId(@PathVariable int id) {

		Optional<VilleTP6> ville = villeRepository.findById(id);
		if (ville.isPresent()) {
			VilleTP6Dto villeDto = VilleMapper.villeToDto(ville.get());

			try {
				ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
				String json = writer.writeValueAsString(villeDto);
				return ResponseEntity.ok(json);
			} catch (JsonProcessingException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur de conversion JSON.");
			}
		} else {
			return ResponseEntity.badRequest().body("L'id est inexistant.");
		}
	}

	@GetMapping("/parNom/{nom}")
	public ResponseEntity<String> extractVilleTP6Nom(@PathVariable String nom) throws JsonProcessingException {

		VilleTP6 ville = villeRepository.findByNom(nom);
		if (ville != null) {
			VilleTP6Dto villeDto = VilleMapper.villeToDto(ville);
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String json = writer.writeValueAsString(villeDto);
			return ResponseEntity.ok(json);
		} else {
			return ResponseEntity.badRequest().body("Ville avec le nom " + nom + " non trouvée.");
		}
	}

	// TP10

	/**
	 * Méthode qui permet de générer un fichier CSV après avoir déterminé le nombre
	 * minimal d'habitants.
	 * 
	 * @param min
	 * @param response
	 * @throws JsonProcessingException
	 * @throws IOException
	 * @throws DocumentException
	 */
//	@GetMapping("/fiche/{min}")
//	public void exportVillesCSV(@PathVariable int min, HttpServletResponse response) throws IOException, DocumentException {
//		
//		response.setHeader("Content-Disposition", "attachment; filename=\"fichier.csv\"");
//		List<VilleTP6> villes = villeRepository.findVillePopGreaterThan(min);
//
//		RestTemplate restTemplate = new RestTemplate();
//		VilleTP6Dto responseApi = restTemplate.getForObject("https://geo.api.gouv.fr/departements/code?fields=nom,code", VilleTP6Dto.class);
//		
//		ObjectMapper mapper = new ObjectMapper();
//		VilleTP6Dto villeDto = mapper.readValue(response.getBody(), Livre.class);
//		
//		for (VilleTP6 ville : villes) {
//			response.getWriter().append(ville.getNom() + ";" + ville.getNbHabitants() + ";" + ville.getDepartement().getCodeDept()  + "\n"); //+ ";" +ville.getDepartement().getNom()
//		}
//		response.flushBuffer();
//	}

	// Fin TP10

	/**
	 * @param nom
	 * @param result
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/commencePar")
	public ResponseEntity<String> findVilleStartsWith(@RequestParam String debutNom) throws JsonProcessingException {

		List<VilleTP6> villes = villeRepository.findVilleStartsWith(debutNom);

		if (villes != null) {
			List<VilleTP6Dto> villesDto = villes.stream().map(VilleMapper::villeToDto).collect(Collectors.toList());
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String json = writer.writeValueAsString(villesDto);
			return ResponseEntity.ok(json);

		} else {
			return ResponseEntity.badRequest()
					.body("Aucune ville dont le nom commence par " + debutNom + " a été trouvée.");
		}
	}

	/**
	 * @param min
	 * @param result
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/populationGreaterThan")
	public ResponseEntity<String> findVillePopGreaterThan(@RequestParam int min) throws JsonProcessingException {

		List<VilleTP6> villes = villeRepository.findVillePopGreaterThan(min);

		if (villes != null) {

			List<VilleTP6Dto> villesDto = villes.stream().map(VilleMapper::villeToDto).collect(Collectors.toList());
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String json = writer.writeValueAsString(villesDto);
			return ResponseEntity.ok(json);
		} else {
			return ResponseEntity.badRequest().body("Aucune ville n'a une population supérieure à " + min);
		}
	}

	/**
	 * @param min
	 * @param max
	 * @param result
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/populationBetween")
	public ResponseEntity<String> findVillePopBetween(@RequestParam int min, @Valid @RequestParam int max)
			throws JsonProcessingException {

		List<VilleTP6> villes = villeRepository.findVillePopBetween(min, max);

		if (villes != null) {
			List<VilleTP6Dto> villesDto = villes.stream().map(VilleMapper::villeToDto).collect(Collectors.toList());
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String json = writer.writeValueAsString(villesDto);
			return ResponseEntity.ok(json);
		} else {
			return ResponseEntity.badRequest()
					.body("Aucune ville n'a une population comprise entre " + min + " et " + max);
		}
	}

	/**
	 * @param min
	 * @param depCode
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/parDepartementPopGreaterThan")
	public ResponseEntity<String> findVilleDeptPopGreater(@RequestParam int min, @RequestParam String depCode)
			throws JsonProcessingException {

		if (depRepository.findByCodeDep(depCode) != null) {
			List<VilleTP6> ville = villeRepository.findVillesDeptPopGreater(min, depCode);
			List<VilleTP6Dto> villesDto = ville.stream().map(VilleMapper::villeToDto).collect(Collectors.toList());
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String json = writer.writeValueAsString(villesDto);
			return ResponseEntity.ok(json);

		} else {
			return ResponseEntity.badRequest()
					.body("Aucune ville n'a une population supérieure à " + min + " dans le département " + depCode);
		}
	}

	/**
	 * @param min
	 * @param max
	 * @param depCode
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/parDepartementPopBetween")
	public ResponseEntity<String> findVilleDeptPopBetween(@RequestParam int min, @RequestParam int max,
			@RequestParam String depCode) throws Exception {

		if (depRepository.findByCodeDep(depCode) != null) {
			List<VilleTP6> ville = villeRepository.findVilleDeptPopBetween(min, max, depCode);
			List<VilleTP6Dto> villesDto = ville.stream().map(VilleMapper::villeToDto).collect(Collectors.toList());
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String json = writer.writeValueAsString(villesDto);
			return ResponseEntity.ok(json);

		} else {
			return ResponseEntity.badRequest()
					.body("Aucune ville n'a une population comprise entre " + min + " et " + max);
		}
	}

	/**
	 * @param depCode
	 * @param nbrVilles
	 * @return
	 * @throws JsonProcessingException
	 */
	@GetMapping("/parDepartementTop")
	public ResponseEntity<String> findNVillesParDepartementOrdreDecroissant(@RequestParam String depCode,
			@RequestParam int nbrVilles) throws JsonProcessingException {

		if (depRepository.findByCodeDep(depCode) != null) {
			Pageable pageable = PageRequest.of(0, nbrVilles);
			List<VilleTP6> ville = villeRepository.findNVillesParDepartementOrdreDecroissant(depCode, pageable);
			List<VilleTP6Dto> villesDto = ville.stream().map(VilleMapper::villeToDto).collect(Collectors.toList());
			ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
			String json = writer.writeValueAsString(villesDto);
			return ResponseEntity.ok(json);

		} else {
			return ResponseEntity.badRequest().body("Le département n'a pas été trouvé ou est inexistant.");
		}
	}

	/**
	 * @param villeTP6
	 * @return
	 * @throws JsonProcessingException
	 * @throws AnomalieException
	 */
//	@Operation(summary = "Création d'une nouvelle ville")
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "La ville a bien été insérée.", content = {
//					@Content(mediaType = "application/json", schema = @Schema(implementation = VilleTP6Dto.class)) }),
//			@ApiResponse(responseCode = "400", description = "La ville n'a pas pu être insérée.", content = @Content) })
//	@PostMapping
//	public ResponseEntity<String> insertVille(@RequestBody VilleTP6 villeTP6) throws JsonProcessingException {
//
//		VilleTP6 ville = villeRepository.findByNom(villeTP6.getNom());
//
//		if (ville != null) {
//			return ResponseEntity.badRequest().body("La ville n'a pas été insérée car elle existe déjà.");
//		}
//		VilleTP6 villeInseree = villeService.insertVille(villeTP6);
//		VilleTP6Dto villeDto = VilleMapper.villeToDto(villeInseree);
//		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
//		String json = writer.writeValueAsString(villeDto);
//		return ResponseEntity.ok(json);
//	}

	@PostMapping
	public ResponseEntity<String> insertVille(@Valid @RequestBody VilleTP6 villeTP6, BindingResult controleQualite)
			throws JsonProcessingException, AnomalieException {

		if (controleQualite.hasErrors()) {
			throw new AnomalieException(controleQualite.getAllErrors().get(0).getDefaultMessage());
		}
		VilleTP6 villeInseree = villeService.insertVille(villeTP6);
		VilleTP6Dto villeDto = VilleMapper.villeToDto(villeInseree);
		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		String json = writer.writeValueAsString(villeDto);
		return ResponseEntity.ok(json);
	}

	/**
	 * @param id
	 * @param villeTP6
	 * @return
	 * @throws JsonProcessingException
	 * @throws AnomalieException
	 */
	@Operation(summary = "Modification d'une ville")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "La ville a bien été modifiée.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VilleTP6Dto.class)) }),
			@ApiResponse(responseCode = "400", description = "La ville n'a pas pu être modifiée.", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<String> updateVille(@PathVariable int id, @Valid @RequestBody VilleTP6 villeTP6,
			BindingResult controleQualite) throws JsonProcessingException, AnomalieException {

		Optional<VilleTP6> ville = villeRepository.findById(id);
		if (controleQualite.hasErrors()) {
			throw new AnomalieException(controleQualite.getAllErrors().get(0).getDefaultMessage());
		}
		VilleTP6 updatedVille = villeService.modifierVilleTP6(id, villeTP6);
		VilleTP6Dto villeDto = VilleMapper.villeToDto(updatedVille);
		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		String json = writer.writeValueAsString(villeDto);
		return ResponseEntity.ok(json);
	}

	/**
	 * @param id
	 * @param villeTP6
	 * @return
	 */
	@Operation(summary = "Suppression d'une ville")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "La ville a bien été supprimée.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VilleTP6Dto.class)) }),
			@ApiResponse(responseCode = "400", description = "La ville n'a pas pu être supprimée.", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteVille(@PathVariable int id) {
		Optional<VilleTP6> ville = villeRepository.findById(id);

		if (ville.isPresent()) {
			VilleTP6Dto villeDto = VilleMapper.villeToDto(ville.get());
			villeRepository.deleteById(id);
			try {
				ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
				String json = writer.writeValueAsString(villeDto);
				return ResponseEntity.ok(json);
			} catch (JsonProcessingException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Erreur lors de la conversion de la ville en JSON.");
			}
		}
		return ResponseEntity.badRequest().body("La ville n'a pas été supprimée car l'id est inexistant.");
	}

}
