package fr.diginamic.hello.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.diginamic.hello.entites.VilleTP6;

@Repository
public interface VilleRepository extends JpaRepository<VilleTP6, Integer> {

	VilleTP6 findByNom(String nom);
	
	@Query("SELECT v FROM VilleTP6 v WHERE v.nom LIKE :debutNom%")
	List<VilleTP6> findVilleStartsWith(@Param("debutNom") String debutNom);

	@Query("SELECT v FROM VilleTP6 v WHERE v.nbHabitants > :min")
	List<VilleTP6> findVillePopGreaterThan(@Param("min") int min);

	@Query("SELECT v FROM VilleTP6 v WHERE v.nbHabitants BETWEEN :min AND :max")
	List<VilleTP6> findVillePopBetween(@Param("min") int min, @Param("max") int max);

	@Query("SELECT v FROM VilleTP6 v JOIN FETCH v.departement d WHERE v.nbHabitants > :min AND d.codeDep = :depCode")
	List<VilleTP6> findVillesDeptPopGreater(@Param("min") int min, @Param("depCode") String depCode);

	@Query("SELECT v FROM VilleTP6 v JOIN FETCH v.departement d WHERE v.nbHabitants BETWEEN :min AND :max AND d.codeDep = :depCode")
	List<VilleTP6> findVilleDeptPopBetween(@Param("min") int min, @Param("max") int max, @Param("depCode") String depCode);

	@Query("SELECT v FROM VilleTP6 v JOIN FETCH v.departement d WHERE d.codeDep = :depCode ORDER BY v.nbHabitants DESC")
    List<VilleTP6> findNVillesParDepartementOrdreDecroissant(@Param("depCode") String depCode, Pageable pageable);


}
