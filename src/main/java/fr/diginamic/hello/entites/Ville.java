package fr.diginamic.hello.entites;

import java.util.Objects;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Ville {

	@Min(value = 1, message = "L'id d'une ville doit être strictement positive.")
	private int id;
	
	@NotNull
	@Size(min = 2, message = "Le nom d'une ville doit être composé d'au moins deux caractères.")
	private String nom;
	
	@Min(value = 1, message = "Le nombre d'habitants d'une ville doit être supérieur ou égal à 1.")
	private int nbHabitants;

	/**
	 * Constructeur
	 * 
	 */
	public Ville() {
		super();
	}

	/**
	 * Constructeur
	 * 
	 * @param nom
	 * @param nbHabitants
	 */

	public Ville(int id, String nom, int nbHabitants) {
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
		Ville other = (Ville) obj;
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

	@Override
	public String toString() {
		return "Ville [id=" + id + ", nom=" + nom + ", nbHabitants=" + nbHabitants + "]";
	}

	
	
}
