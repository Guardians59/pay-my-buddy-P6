package com.openclassrooms.paymybuddy.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.models.UserModel;


@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test de connexion d'un utilisateur enregistré")
    public void postConnectionTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test2@gmail.com");
	user.setPassword("azerty2");
	
	mockMvc.perform(post("/home").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("Test erreur de connexion, identifiants incorrects")
    public void postErrorConnectionTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test2@gmail.com");
	user.setPassword("azertyError");
	
	mockMvc.perform(post("/home").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("index"))
		.andExpect(model().attributeExists("error"));

    }
    
    @Test
    @DisplayName("Test erreur mot de passe manquant")
    public void postErrorPasswordBlankConnectionTest() throws Exception {
	UserModel user = new UserModel();
	user.setEmail("test2@gmail.com");
	
	mockMvc.perform(post("/home").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("index"))
		.andExpect(model().attributeExists("errorField"));

    }
    
    @Test
    @DisplayName("Test erreur email manquant")
    public void postErrorEmailBlankConnectionTest() throws Exception {
	UserModel user = new UserModel();
	user.setPassword("azerty");
	
	mockMvc.perform(post("/home").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.flashAttr("userModel", user))
		.andExpect(view().name("index"))
		.andExpect(model().attributeExists("errorField"));

    }
}
