package com.openclassrooms.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.models.TransferMoneyModel;
import com.openclassrooms.paymybuddy.models.UserModel;

@SpringBootTest
public class SendServiceTest {
    
    @Autowired
    ISendService sendService;
    
    @Test
    @DisplayName("Test du transfert d'argent vers le portefeuille")
    public void transferMoneyInWalletTest() {
	//GIVEN
	boolean result;
	String email = "test2@gmail.com";
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Gael");
	transfer.setLastNameIbanAccount("Kakuta");
	transfer.setAmountTransfer(1000);
	//WHEN
	result = sendService.transferMoneyInWallet(email, transfer);
	//THEN
	assertEquals(result, true);
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, nom erroné pour le transfert d'argent vers le portefeuille")
    public void transferMoneyInWalletNameErrorTest() {
	//GIVEN
	boolean result;
	String email = "test2@gmail.com";
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Gael");
	transfer.setLastNameIbanAccount("Error");
	transfer.setAmountTransfer(1000);
	//WHEN
	result = sendService.transferMoneyInWallet(email, transfer);
	//THEN
	assertEquals(result, false);
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, montant non indiqué lors du transfert vers le portefeuille")
    public void transferMoneyInWalletAmountErrorTest() {
	//GIVEN
	boolean result;
	String email = "test2@gmail.com";
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Gael");
	transfer.setLastNameIbanAccount("Kakuta");
	transfer.setAmountTransfer(0);
	//WHEN
	result = sendService.transferMoneyInWallet(email, transfer);
	//THEN
	assertEquals(result, false);
	
    }

}
