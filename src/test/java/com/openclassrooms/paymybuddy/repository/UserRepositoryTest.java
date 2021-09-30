package com.openclassrooms.paymybuddy.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.models.UserModel;

@SpringBootTest
public class UserRepositoryTest {
    
    @Autowired
    IUserRepository userRepository;
    
    @Test
    @DisplayName("Test récupération de la liste des utilisateurs enregistrés en base de donnée")
    public void getAllUserTest() {
	//GIVEN
	Iterable<UserModel> listUserDB = new ArrayList<>();
	listUserDB = userRepository.findAll();
	List<UserModel> result = new ArrayList<>();
	//WHEN
	listUserDB.forEach(userRegistered -> {
	    result.add(userRegistered);
	});
	//THEN
	assertEquals(result.isEmpty(), false);
	assertEquals(result.get(1).getFirstName(), "Gael");
	assertEquals(result.get(3).getFirstName(), "Jonathan");
    }
    
    @Test
    @DisplayName("Test récupération d'un utilisateur via son email")
    public void getUserExistByEmailTest() {
	//GIVEN
	UserModel user = new UserModel();
	int idUser = 1;
	String email = "test@gmail.com";
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	//WHEN
	user = userRepository.getByEmail(sha256hexEmail);
	//THEN
	assertEquals(user.getId(), idUser);
    }

}
