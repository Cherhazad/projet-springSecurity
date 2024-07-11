package fr.diginamic.hello.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.VilleTP6;
import fr.diginamic.hello.repositories.DepartementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DepartementService {

	@Autowired
	private DepartementDao departementDao;

	@Autowired
	private DepartementRepository depRepo;

	List<Departement> listeDepartements;

	@PostConstruct
	public void initDonnees() {
		this.listeDepartements = extractDepartements();
	}

	public List<Departement> extractDepartements() {
		return departementDao.extractDepartements();
	}

	public Departement extractDeptCodeDep(String codeDep) {
		Departement depParId = listeDepartements.stream()
				.filter(d -> d.getCodeDep() != null && d.getCodeDep().equals(codeDep)).findFirst().orElse(null);
		return depParId;
	}

	public Departement extractDepNom(String nom) {
		Departement depParNom = listeDepartements.stream()
				.filter(d -> d.getNomDepartement() != null && d.getNomDepartement().equalsIgnoreCase(nom)).findFirst().orElse(null);
		return depParNom;
	}

	public Departement insertDepartement(Departement dept) {

		Departement deptExistant = extractDeptCodeDep(dept.getCodeDep());
		if (deptExistant == null) {
			depRepo.save(dept);
			listeDepartements.add(dept);
		}
		return deptExistant;
	}

	public Departement modifierDepartement(String codeDep, Departement deptModifie) {
		Departement deptAModifier = depRepo.findByCodeDep(codeDep);
		if (deptAModifier != null) {
			System.out.println("Département trouvé : " + deptAModifier);
			deptAModifier.setCodeDep(deptModifie.getCodeDep());
			deptAModifier.setNomDepartement(deptModifie.getNomDepartement());
			departementDao.update(deptModifie);
			System.out.println("Département modifié : " + deptModifie);
		} else {
			System.out.println("DepartementService : Département avec codeDep " + codeDep + " non trouvée.");
		}
		return deptAModifier;
	}

	public Departement supprimerDepartement(String codeDep) {
		Departement deptParId = listeDepartements.stream().filter(d -> d.getCodeDep() == codeDep).findFirst()
				.orElse(null);
		if (deptParId != null) {
			listeDepartements.remove(deptParId);
			departementDao.delete(deptParId);
		}
		return deptParId;
	}

	public List<VilleTP6> villesPlusPeuplees(long nbrVilles, List<VilleTP6> setVilles) {
		return setVilles.stream().limit(nbrVilles).collect(Collectors.toList());
	}

//	public void associerNomDepartement(String codeDept) {
//		VilleTP6Dto response = restTemplate.getForObject("https://geo.api.gouv.fr/departements/codeDept?fields=nom,code,codeRegion", VilleTP6Dto.class);
//	}

}
