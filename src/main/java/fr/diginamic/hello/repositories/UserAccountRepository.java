package fr.diginamic.hello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.diginamic.hello.entites.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

	UserAccount findByUsername(String username);
	UserAccount findByPassword(String password);
	
	
}
