//package fr.diginamic.hello;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.client.RestTemplate;
//
//import fr.diginamic.hello.dto.DepartementApiGouvGeoDto;
//
//@SpringBootApplication
//public class InterrogationApiExterne implements CommandLineRunner {
//
//
//	@Override
//	public void run(String... args) throws Exception {
//
//		RestTemplate rest = new RestTemplate();
//		DepartementApiGouvGeoDto response = rest.getForObject("https://geo.api.gouv.fr/departements?fields=nom,code,codeRegion",DepartementApiGouvGeoDto.class);
//		System.out.println(response);
//		
//	}
//}
