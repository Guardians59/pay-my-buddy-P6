package com.openclassrooms.paymybuddy.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.IUserRepository;


@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    IUserRepository userRepository;
    
    @Test
    @DisplayName("Test d'enregistrement d'un utilisateur")
    public void postRegisterTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test50@gmail.com");
	user.setFirstName("test50");
	user.setLastName("50test");
	user.setPassword("azerty50");
	
	mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("index"))
		.andExpect(model().attributeExists("registerValidate"))
		.andExpect(status().isOk());
		
	String sha256hexEmail = DigestUtils.sha256Hex("test50@gmail.com");
	UserModel userDelete = userRepository.getByEmail(sha256hexEmail);
	userRepository.delete(userDelete);
		
    }
    
    @Test
    @DisplayName("Test de l'erreur email déjà enregistré en DB")
    public void postErrorEmailAlreadyRegisteredRegisterTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test@gmail.com");
	user.setFirstName("test50");
	user.setLastName("50test");
	user.setPassword("azerty50");
	
	mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("register"))
		.andExpect(model().attributeExists("registerError"))
		.andExpect(status().isOk());
			
    }
    
    @Test
    @DisplayName("Test de l'erreur email manquant lors de l'inscription")
    public void postErrorEmailBlankRegisterTest() throws Exception {
	UserModel user = new UserModel();
	user.setFirstName("test50");
	user.setLastName("50test");
	user.setPassword("azerty50");
	
	mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("register"))
		.andExpect(model().attributeExists("fieldError"))
		.andExpect(status().isOk());
		
    }
    
    @Test
    @DisplayName("Test de l'erreur mot de passe manquant lors de l'inscription")
    public void postErrorPasswordBlankRegisterTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test50@gmail.com");
	user.setFirstName("test50");
	user.setLastName("50test");
	
	mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("register"))
		.andExpect(model().attributeExists("fieldError"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test de l'erreur prénom manquant lors de l'inscription")
    public void postErrorFirstNameBlankRegisterTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test@gmail.com");
	user.setLastName("50test");
	user.setPassword("azerty50");
	
	mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("register"))
		.andExpect(model().attributeExists("fieldError"))
		.andExpect(status().isOk());
		
    }
    
    @Test
    @DisplayName("Test de l'erreur nom manquant lors de l'inscription")
    public void postErrorLastNameBlankRegisterTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test@gmail.com");
	user.setFirstName("test50");
	user.setPassword("azerty50");
	
	mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("register"))
		.andExpect(model().attributeExists("fieldError"))
		.andExpect(status().isOk());
		
    }

}
