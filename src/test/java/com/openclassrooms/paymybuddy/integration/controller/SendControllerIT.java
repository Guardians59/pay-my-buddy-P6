package com.openclassrooms.paymybuddy.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.repository.ISendRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SendControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    ISendRepository sendRepository;
    
    @Test
    @DisplayName("Test envoie d'argent à un ami")
    public void postSendTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	SendModel sendInfos = new SendModel();
	sendInfos.setIdRecipient(2);
	sendInfos.setDescription("Test intégration");
	sendInfos.setAmountSend(100);
	
	mockMvc.perform(post("/send").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("sendInfos", sendInfos))
		.andExpect(model().attributeExists("sendSucces"))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, solde insuffisant")
    public void postSendErrorTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	SendModel sendInfos = new SendModel();
	sendInfos.setIdRecipient(2);
	sendInfos.setDescription("Test intégration");
	sendInfos.setAmountSend(100000);
	
	mockMvc.perform(post("/send").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("sendInfos", sendInfos))
		.andExpect(model().attributeExists("sendError"))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, montant envoyé égal à zéro")
    public void postSendAmountNullErrorTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	SendModel sendInfos = new SendModel();
	sendInfos.setIdRecipient(2);
	sendInfos.setDescription("Test intégration");
	sendInfos.setAmountSend(0);
	
	mockMvc.perform(post("/send").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("sendInfos", sendInfos))
		.andExpect(model().attributeExists("sendAmountError"))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
	
    }

}
