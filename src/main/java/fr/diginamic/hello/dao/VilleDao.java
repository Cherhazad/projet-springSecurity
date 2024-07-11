package fr.diginamic.hello.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.diginamic.hello.entites.VilleTP6;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class VilleDao {

	@PersistenceContext
	private EntityManager em;

	// read

	public List<VilleTP6> extractVilles() {
		TypedQuery<VilleTP6> query = em.createQuery("SELECT v FROM VilleTP6 v", VilleTP6.class);
		return query.getResultList();
	}

	// create

	@Transactional
	public void insert(VilleTP6 villeTP6) {
		em.persist(villeTP6);
	}

	// update

	@Transactional
	public void update(VilleTP6 villeTP6) {
		VilleTP6 villeFromDb = em.find(VilleTP6.class, villeTP6.getId());
		if (villeFromDb != null) {
			System.out.println("Ville trouvée dans la base de données : " + villeFromDb);
			villeFromDb.setNom(villeTP6.getNom());
			villeFromDb.setNbHabitants(villeTP6.getNbHabitants());
			em.merge(villeFromDb);
			System.out.println("Ville mise à jour dans la base de données : " + villeFromDb);
		} else {
			System.out.println("VilleDao: Ville avec id " + villeTP6.getId() + " non trouvée dans la base de données.");

		}
	}

	// delete
	@Transactional
	public void delete(VilleTP6 villeTP6) {
		VilleTP6 villeFromDb = em.find(VilleTP6.class, villeTP6.getId());
		if (villeFromDb != null) {
			System.out.println("Ville trouvée dans la base de données : " + villeFromDb);
			em.remove(villeFromDb);
			System.out.println("Ville mise à jour dans la base de données : " + villeFromDb);
		} else {
			System.out.println("VilleDao: Ville avec id " + villeTP6.getId() + " non trouvée dans la base de données.");

		}
	}

}
