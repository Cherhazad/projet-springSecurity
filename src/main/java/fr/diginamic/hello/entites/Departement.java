package fr.diginamic.hello.entites;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity
public class Departement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// @NotNull
	@Size(min = 2, max = 3, message = "Le nombre de caractère du code de département doit être compris entre 2 et 3.")
	@Column(name = "code_dep")
	private String codeDep;

	// @NotNull
	@Size(min = 2, max = 255, message = "Le nom du département doit contenir au moins 2 caractères.")
	@Column(name = "nom_departement")
	private String nomDepartement;

	@OneToMany(mappedBy = "departement")
	private Set<VilleTP6> villes = new HashSet<>();

	/**
	 * Constructeur
	 * 
	 */
	public Departement() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departement other = (Departement) obj;
		return Objects.equals(codeDep, other.codeDep);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codeDep);
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
	 * Getter pour codeDep
	 * 
	 * @return the codeDep
	 */
	public String getCodeDep() {
		return codeDep;
	}

	/**
	 * Setter pour codeDep
	 * 
	 * @param codeDep the codeDep to set
	 */
	public void setCodeDep(String codeDep) {
		this.codeDep = codeDep;
	}

	/**
	 * Getter pour nomDepartement
	 * 
	 * @return the nomDepartement
	 */
	public String getNomDepartement() {
		return nomDepartement;
	}

	/**
	 * Setter pour nomDepartement
	 * 
	 * @param nomDepartement the nomDepartement to set
	 */
	public void setNomDepartement(String nomDepartement) {
		this.nomDepartement = nomDepartement;
	}

	/**
	 * Getter pour villes
	 * 
	 * @return the villes
	 */
	public Set<VilleTP6> getVilles() {
		return villes;
	}

	/**
	 * Setter pour villes
	 * 
	 * @param villes the villes to set
	 */
	public void setVilles(Set<VilleTP6> villes) {
		this.villes = villes;
	}

	@Override
	public String toString() {
		return "Departement [id=" + id + ", codeDep=" + codeDep + ", nomDepartement=" + nomDepartement + ", villes="
				+ villes + "]";
	}

}
