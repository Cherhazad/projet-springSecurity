package fr.diginamic.hello.entites;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "VILLE")
public class VilleTP6 implements Comparable<VilleTP6> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Min(value = 1) on ne peut pas faire de contrôle sur l'id en auto incrément
	// car géré par mySql
	private int id;

	@NotNull
	@Column(unique=true)
	@Size(min = 2, max = 255, message = "Le nom de la ville doit contenir au moins 2 caractères.")
	private String nom;

	@Min(value = 10, message = "Le nombre d'habitants doit être supérieur ou égal à 10.")
	@Column(name = "nb_habitants")
	private int nbHabitants;

	@ManyToOne
	@JoinColumn(name = "ID_DEPARTEMENT")
	private Departement departement;

	/**
	 * Constructeur
	 * 
	 */
	public VilleTP6() {
		super();
	}

	/**
	 * Constructeur
	 * 
	 * @param nom
	 * @param nbHabitants
	 */

	public VilleTP6(int id, String nom, int nbHabitants) {
		super();
		this.id = id;
		this.nom = nom;
		this.nbHabitants = nbHabitants;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VilleTP6 other = (VilleTP6) obj;
		return nbHabitants == other.nbHabitants && Objects.equals(nom, other.nom);
	}

	/**
	 * Getter pour nom
	 * 
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter pour nom
	 * 
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Getter pour nbHabitants
	 * 
	 * @return the nbHabitants
	 */
	public int getNbHabitants() {
		return nbHabitants;
	}

	/**
	 * Setter pour nbHabitants
	 * 
	 * @param nbHabitants the nbHabitants to set
	 */
	public void setNbHabitants(int nbHabitants) {
		this.nbHabitants = nbHabitants;
	}

	/**
	 * Getter pour id
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter pour id
	 * 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter pour departement
	 * 
	 * @return the departement
	 */
	public Departement getDepartement() {
		return departement;
	}

	/**
	 * Setter pour departement
	 * 
	 * @param departement the departement to set
	 */
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	@Override
	public String toString() {
		return "VilleTP6 [id=" + id + ", nom=" + nom + ", nbHabitants=" + nbHabitants + "]";
	}

	@Override
	public int compareTo(VilleTP6 o) {
		if (this.nbHabitants > o.getNbHabitants()) {
			return 1;
		} else if (this.nbHabitants < o.getNbHabitants()) {
			return -1;
		} else {
			return 0;
		}
	}
	
//	   @Override // ChatGPT propose ce code pour trier par ordre décroissant
//	    public int compareTo(VilleTP6 o) {
//	        return Integer.compare(o.nbHabitants, this.nbHabitants); // Trie par ordre décroissant
//	    }
}
