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
public class WithdrawControllerIT {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test du retrait d'argent vers le compte en banque")
    public void postWithDrawTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Seko");
	transfer.setLastNameIbanAccount("Fofana");
	transfer.setAmountTransfer(1000);
	
	mockMvc.perform(post("/withdraw").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transfer", transfer))
		.andExpect(model().attributeExists("withdrawSucces"))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test erreur nom de l'iban incorrect à celui en DB")
    public void postErrorWithDrawTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Seko");
	transfer.setLastNameIbanAccount("Error");
	transfer.setAmountTransfer(1000);
	
	mockMvc.perform(post("/withdraw").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transfer", transfer))
		.andExpect(model().attributeExists("withdrawError"))
		.andExpect(view().name("withdraw"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test erreur numéro iban manquant")
    public void postErrorIbanBlankWithdrawTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setFirstNameIbanAccount("Seko");
	transfer.setLastNameIbanAccount("Fofana");
	transfer.setAmountTransfer(1000);
	
	mockMvc.perform(post("/withdraw").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transfer", transfer))
		.andExpect(model().attributeExists("withdrawFieldError"))
		.andExpect(view().name("withdraw"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test erreur montant à zéro")
    public void postErrorAmountZeroWithDrawTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Seko");
	transfer.setLastNameIbanAccount("Fofana");
	transfer.setAmountTransfer(0);
	
	mockMvc.perform(post("/withdraw").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("transfer", transfer))
		.andExpect(model().attributeExists("withdrawFieldError"))
		.andExpect(view().name("withdraw"))
		.andExpect(status().isOk());
	
    }
}
