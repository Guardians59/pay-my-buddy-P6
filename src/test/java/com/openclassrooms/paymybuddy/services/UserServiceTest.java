package com.openclassrooms.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.IUserRepository;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    IUserRepository userRepository;
    
    @Autowired
    IUserService userService;
    
    @Test
    @DisplayName("Test d'ajout d'un utilisateur en base de donnée avec l'email et mdp hasher")
    public void addUserTest() {
	//GIVEN
	UserModel newUser = new UserModel();
	String email = "test94@gmail.com";
	String password = "azerty94";
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	String sha256hex = DigestUtils.sha256Hex(password);
	newUser.setEmail(email);
	newUser.setPassword(password);
	newUser.setFirstName("Hector");
	newUser.setLastName("Bellerin");
	//WHEN
	boolean result = userService.addUser(newUser);
	UserModel userRegister = userRepository.getByEmail(sha256hexEmail);
	//THEN
	assertEquals(result, true);
	assertEquals(userRegister.getPassword(), sha256hex);
	
	
	userRepository.delete(userRegister);
    }
    
    @Test
    @DisplayName("Test de l'erreur lors de la tentative d'enregistrement avec un email déjà utilisé")
    public void addUserErrorEmailRegisteredTest() {
	//GIVEN
	UserModel newUser = new UserModel();
	String email = "test@gmail.com";
	String password = "azerty94";
	newUser.setEmail(email);
	newUser.setPassword(password);
	newUser.setFirstName("Test");
	newUser.setLastName("Error");
	//WHEN
	boolean result = userService.addUser(newUser);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    @DisplayName("Test field manquant lors de l'inscription")
    public void addUserErrorFieldTest() {
	//GIVEN
	UserModel newUser = new UserModel();
	newUser.setEmail("test45@gmail.com");
	newUser.setFirstName("New");
	String password = "Azerty45";
	newUser.setPassword(password);
	boolean result;
	//WHEN
	result = userService.addUser(newUser);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    @DisplayName("Test de connexion d'un utilisateur enregistré")
    public void getExistingUserWantConnectTest() {
	//GIVEN
	UserModel user = new UserModel();
	user.setEmail("test2@gmail.com");
	String password = "azerty2";
	user.setPassword(password);
	boolean result;
	//WHEN
	result = userService.userExist(user);
	//THEN
	assertEquals(result, true);
    }
    
    @Test
    @DisplayName("Test erreur de mdp d'un utilisateur enregistré")
    public void getErrorPasswordTest() {
	//GIVEN
	UserModel user = new UserModel();
	user.setEmail("test@gmail.com");
	String password = "Error";
	user.setPassword(password);
	boolean result;
	//WHEN
	result = userService.userExist(user);
	//THEN
	assertEquals(result, false);
    }
    
    @Test
    @DisplayName("Test erreur lors de la tentative de connexion d'un utilisateur non enregistré")
    public void getNotExistingUserWantConnectTest() {
	//GIVEN
	UserModel user = new UserModel();
	user.setEmail("test800@gmail.com");
	String password = "azerty80";
	user.setPassword(password);
	boolean result;
	//WHEN
	result = userService.userExist(user);
	//THEN
	assertEquals(result, false);
    }

}
