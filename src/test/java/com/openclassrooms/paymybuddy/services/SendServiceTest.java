package com.openclassrooms.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.models.SendInfosModel;
import com.openclassrooms.paymybuddy.models.TransferMoneyModel;
import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.ISendRepository;
import com.openclassrooms.paymybuddy.repository.IUserRepository;

@SpringBootTest
public class SendServiceTest {
    
    @Autowired
    ISendService sendService;
    
    @Autowired
    ISendRepository sendRepository;
    
    @Autowired
    IUserRepository userRepository;
    
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
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel user = userRepository.getByEmail(sha256hexEmail);
	double moneyBeforeTransfer = user.getWallet();
	//WHEN
	result = sendService.transferMoneyInWallet(email, transfer);
	user = userRepository.getByEmail(sha256hexEmail);
	double moneyAfterTransfer = moneyBeforeTransfer + 1000;
	//THEN
	assertEquals(result, true);
	assertEquals(user.getWallet(), moneyAfterTransfer);
	
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
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel user = userRepository.getByEmail(sha256hexEmail);
	double moneyBeforeTransfer = user.getWallet();
	//WHEN
	result = sendService.transferMoneyInWallet(email, transfer);
	user = userRepository.getByEmail(sha256hexEmail);
	//THEN
	assertEquals(result, false);
	assertEquals(user.getWallet(), moneyBeforeTransfer);
	
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
    
    @Test
    @DisplayName("Test de l'envoie d'argent vers un ami")
    public void sendMoneyToFriendTest() {
	//GIVEN
	boolean result;
	String email = "test2@gmail.com";
	String emailFriend = "test@gmail.com";
	SendInfosModel sendInfos = new SendInfosModel();
	sendInfos.setIdRecipient(1);
	sendInfos.setDescription("Test");
	sendInfos.setSendAmount(100);
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel user = userRepository.getByEmail(sha256hexEmail);
	double moneyBeforeTransfer = user.getWallet();
	String sha256hexEmailFriend = DigestUtils.sha256Hex(emailFriend);
	UserModel userFriend = userRepository.getByEmail(sha256hexEmailFriend);
	double moneyFriendBeforeTransfer = userFriend.getWallet();
	//WHEN
	result = sendService.sendMoney(email, sendInfos);
	user = userRepository.getByEmail(sha256hexEmail);
	userFriend = userRepository.getByEmail(sha256hexEmailFriend);
	double resultSampling = moneyBeforeTransfer - 100.5;
	double resultWithoutSampling = moneyFriendBeforeTransfer + 100;
	//THEN
	assertEquals(result, true);
	assertEquals(resultSampling, user.getWallet());
	assertEquals(resultWithoutSampling, userFriend.getWallet());
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, solde insuffisant")
    public void sendErrorBalanceInsufficientTest() {
	//GIVEN
	boolean result;
	String email = "test@gmail.com";
	String emailFriend = "test2@gmail.com";
	SendInfosModel sendInfos = new SendInfosModel();
	sendInfos.setIdRecipient(2);
	sendInfos.setDescription("TestError");
	sendInfos.setSendAmount(30000);
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel user = userRepository.getByEmail(sha256hexEmail);
	double moneyBeforeTransfer = user.getWallet();
	String sha256hexEmailFriend = DigestUtils.sha256Hex(emailFriend);
	UserModel userFriend = userRepository.getByEmail(sha256hexEmailFriend);
	double moneyFriendBeforeTransfer = userFriend.getWallet();
	//WHEN
	result = sendService.sendMoney(email, sendInfos);
	user = userRepository.getByEmail(sha256hexEmail);
	userFriend = userRepository.getByEmail(sha256hexEmailFriend);
	//THEN
	assertEquals(result, false);
	assertEquals(moneyBeforeTransfer, user.getWallet());
	assertEquals(moneyFriendBeforeTransfer, userFriend.getWallet());
    }
    
    @Test
    @DisplayName("Test du transfert d'argent vers le compte en banque")
    public void transferMoneyInBankAccountTest() {
	//GIVEN
	boolean result;
	String email = "test2@gmail.com";
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Gael");
	transfer.setLastNameIbanAccount("Kakuta");
	transfer.setAmountTransfer(100);
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel user = userRepository.getByEmail(sha256hexEmail);
	double moneyBeforeTransfer = user.getWallet();
	//WHEN
	result = sendService.transferMoneyInWallet(email, transfer);
	user = userRepository.getByEmail(sha256hexEmail);
	double moneyAfterTransfer = moneyBeforeTransfer + 100;
	//THEN
	assertEquals(result, true);
	assertEquals(user.getWallet(), moneyAfterTransfer);
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, nom erroné pour le transfert d'argent vers le compte en banque")
    public void transferMoneyInBankAccountNameErrorTest() {
	//GIVEN
	boolean result;
	String email = "test2@gmail.com";
	TransferMoneyModel transfer = new TransferMoneyModel();
	transfer.setIbanAccount("ABCD123");
	transfer.setFirstNameIbanAccount("Gael");
	transfer.setLastNameIbanAccount("Error");
	transfer.setAmountTransfer(100);
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel user = userRepository.getByEmail(sha256hexEmail);
	double moneyBeforeTransfer = user.getWallet();
	//WHEN
	result = sendService.transferMoneyInWallet(email, transfer);
	user = userRepository.getByEmail(sha256hexEmail);
	//THEN
	assertEquals(result, false);
	assertEquals(user.getWallet(), moneyBeforeTransfer);
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, montant non indiqué lors du transfert vers le compte en banque")
    public void transferMoneyInBankAccountAmountErrorTest() {
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
