package fr.diginamic.hello.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.diginamic.hello.entites.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Service
public class DepartementDao {

	@PersistenceContext
	private EntityManager em;

	// read
	public List<Departement> extractDepartements() {
		TypedQuery<Departement> query = em.createQuery("SELECT d FROM Departement d Join fetch d.villes", Departement.class);
		return query.getResultList();
	}

	// create
	@Transactional
	public void insert(Departement dept) {
		em.persist(dept);
	}

	// update
	@Transactional
	public void update(Departement dept) {
		Departement deptFromDb = em.find(Departement.class, dept.getCodeDep());
		if (deptFromDb != null) {
			System.out.println("Département trouvé dans la base de données : " + deptFromDb);
			em.merge(deptFromDb);
			System.out.println("Département mis à jour dans la base de données : " + deptFromDb);
		} else {
			System.out.println("DepartementDao: Departement avec id " + deptFromDb.getCodeDep() + " non trouvé dans la base de données.");

		}
	}
	
	// delete
	@Transactional
	public void delete(Departement dept) {
		Departement deptFromDb = em.find(Departement.class, dept.getCodeDep());
		if (deptFromDb != null) {
			System.out.println("Département trouvé dans la base de données : " + dept);
			em.remove(deptFromDb);
			System.out.println("Département mise à jour dans la base de données : " + dept);
		} else {
			System.out.println("DepartementDao: Département avec id " + dept.getCodeDep() + " non trouvé dans la base de données.");

		}
	}

}
