package fr.diginamic.hello.entites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private String password;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<GrantedAuthority> authorities = new ArrayList<>();

//	/**
//	 * Constructeur
//	 * 
//	 * @param username
//	 * @param password
//	 * @param authorities
//	 */
//	public UserAccount(String username, String password, String... authorities) { // ne sert que lorsqu'on veut mettre en paramètre une liste d'authorities
//		super();
//		this.username = username;
//		this.password = password;
//		this.authorities = Arrays.stream(authorities).map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast)
//				.toList();
//	}

	/** Constructeur
	 * @param username
	 * @param password
	 * @param role
	 */
	public UserAccount(String username, String password, String role) {
		GrantedAuthority roleAuthority = new SimpleGrantedAuthority(role);
		authorities = new ArrayList<>();
		authorities.add(roleAuthority);
		this.username = username;
		this.password = password;
	}

	/**
	 * Getter pour id
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter pour id
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter pour username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter pour username
	 * 
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter pour password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter pour password
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter pour authorities
	 * 
	 * @return the authorities
	 */
	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * Setter pour authorities
	 * 
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", username=" + username + ", password=" + password + ", authorities="
				+ authorities + "]";
	}
}
