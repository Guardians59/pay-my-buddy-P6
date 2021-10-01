package com.openclassrooms.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.models.UserModel;

@SpringBootTest
public class FormServiceTest {

    @Autowired
    IFormService formService;

    @Test
    @DisplayName("Test du formulaire register complet")
    public void formRegisterValidTest() {
	// GIVEN
	boolean result;
	UserModel user = new UserModel();
	user.setEmail("test90@gmail.com");
	user.setPassword("abcd");
	user.setFirstName("test");
	user.setLastName("form");
	// WHEN
	result = formService.formRegisterValid(user);
	// THEN
	assertEquals(result, true);
    }

    @Test
    @DisplayName("Test de l'erreur mdp manquant au formulaire register")
    public void formRegisterErrorPasswordBlankTest() {
	// GIVEN
	boolean result;
	UserModel user = new UserModel();
	user.setEmail("test90@gmail.com");
	user.setFirstName("test");
	user.setLastName("form");
	// WHEN
	result = formService.formRegisterValid(user);
	// THEN
	assertEquals(result, false);
    }

    @Test
    @DisplayName("Test de l'erreur email manquant au formulaire register")
    public void formRegisterEmailBlankTest() {
	// GIVEN
	boolean result;
	UserModel user = new UserModel();
	user.setPassword("abcd");
	user.setFirstName("test");
	user.setLastName("form");
	// WHEN
	result = formService.formRegisterValid(user);
	// THEN
	assertEquals(result, false);
    }

    @Test
    @DisplayName("Test de l'erreur prénom manquant au formulaire register")
    public void formRegisterFirstNameBlankTest() {
	// GIVEN
	boolean result;
	UserModel user = new UserModel();
	user.setEmail("test90@gmail.com");
	user.setPassword("abcd");
	user.setLastName("form");
	// WHEN
	result = formService.formRegisterValid(user);
	// THEN
	assertEquals(result, false);
    }

    @Test
    @DisplayName("Test de l'erreur nom manquant au formulaire register")
    public void formRegisterLastNameBlankTest() {
	// GIVEN
	boolean result;
	UserModel user = new UserModel();
	user.setEmail("test90@gmail.com");
	user.setPassword("abcd");
	user.setFirstName("test");
	// WHEN
	result = formService.formRegisterValid(user);
	// THEN
	assertEquals(result, false);
    }

    @Test
    @DisplayName("Test du formulaire index complet")
    public void formConnectionValidTest() {
	// GIVEN
	boolean result;
	UserModel user = new UserModel();
	user.setEmail("test@gmail.com");
	user.setPassword("azerty");
	// WHEN
	result = formService.formConnectionValid(user);
	// THEN
	assertEquals(result, true);
    }

    @Test
    @DisplayName("Test erreur formulaire index incomplet")
    public void formConnectionInvalidTest() {
	// GIVEN
	boolean result;
	UserModel user = new UserModel();
	user.setEmail("test@gmail.com");
	// WHEN
	result = formService.formConnectionValid(user);
	// THEN
	assertEquals(result, false);
    }

}
