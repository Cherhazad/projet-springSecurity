package fr.diginamic.hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GestionnaireException {

	@ExceptionHandler({AnomalieException.class})
	public ResponseEntity<String> traiterErreurs(AnomalieException e) {
		System.out.println("Je suis le gestionnaire d'exceptions et je fonctionne bien");
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
