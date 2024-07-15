package fr.diginamic.hello.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.diginamic.hello.entites.UserAccount;
import fr.diginamic.hello.repositories.UserAccountRepository;
import jakarta.annotation.PostConstruct;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        create(new UserAccount("admin", passwordEncoder.encode("admin"), "ROLE_ADMIN"));
        create(new UserAccount("user", passwordEncoder.encode("user"), "ROLE_USER"));
    }

    private void create(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }


//	public UserAccount findByUsernameAndPassword(String username, String password) {
//		
//		UserAccount usernameFound = userRepository.findByUsername(username);
//		String passwordFound = null;
//		
//		return userRepository.findByUsername(username);
//	}

}
