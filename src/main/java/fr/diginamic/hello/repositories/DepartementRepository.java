package fr.diginamic.hello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.diginamic.hello.entites.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {
	
	Departement findByCodeDep(String codeDep);
	Departement deleteByCodeDep(String codeDep);
	
}
