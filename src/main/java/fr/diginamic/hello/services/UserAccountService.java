package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.entites.UserAccount;
import fr.diginamic.hello.repositories.UserAccountRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UserAccountService implements UserDetailsService {
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@PostConstruct
	public void init() {
		create(new UserAccount("admin", "admin", "ROLE_ADMIN"));
		create(new UserAccount("user", "user", "ROLE_USER"));
	}
	
	private void create(UserAccount userAccount) {
		userAccountRepository.save(userAccount);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // TODO faire DTO qui convertit un UserAccount en UserDetails
		return null;
	}

//	public UserAccount findByUsernameAndPassword(String username, String password) {
//		
//		UserAccount usernameFound = userRepository.findByUsername(username);
//		String passwordFound = null;
//		
//		return userRepository.findByUsername(username);
//	}

}
