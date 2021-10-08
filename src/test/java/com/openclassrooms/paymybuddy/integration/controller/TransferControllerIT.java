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

import com.openclassrooms.paymybuddy.models.TransferMoneyModel;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test de transfert d'argent vers le portefeuille")
    public void postTransferTest() throws Exception {
	String email = "test3@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Arnaud");
	transfer.setLastNameIbanAccount("Kalimuendo");
	transfer.setAmountTransfer(1000);
	
	mockMvc.perform(post("/transfer").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transferMoneyModel", transfer))
		.andExpect(model().attributeExists("transferSucces"))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test erreur nom de l'iban incorrect à celui en DB")
    public void postErrorTransferTest() throws Exception {
	String email = "test3@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Arnaud");
	transfer.setLastNameIbanAccount("Error");
	transfer.setAmountTransfer(1000);
	
	mockMvc.perform(post("/transfer").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transferMoneyModel", transfer))
		.andExpect(model().attributeExists("transferError"))
		.andExpect(view().name("transfer"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test erreur Iban manquant")
    public void postErrorIbanBlankTransferTest() throws Exception {
	String email = "test3@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setFirstNameIbanAccount("Arnaud");
	transfer.setLastNameIbanAccount("Kalimuendo");
	transfer.setAmountTransfer(1000);
	
	mockMvc.perform(post("/transfer").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transferMoneyModel", transfer))
		.andExpect(model().attributeExists("transferFieldError"))
		.andExpect(view().name("transfer"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test erreur montant à zéro")
    public void postErrorAmountZeroTransferTest() throws Exception {
	String email = "test3@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Arnaud");
	transfer.setLastNameIbanAccount("Kalimuendo");
	transfer.setAmountTransfer(0);
	
	mockMvc.perform(post("/transfer").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transferMoneyModel", transfer))
		.andExpect(model().attributeExists("transferFieldError"))
		.andExpect(view().name("transfer"))
		.andExpect(status().isOk());
	
    }

}
