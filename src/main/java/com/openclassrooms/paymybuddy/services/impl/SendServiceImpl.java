package com.openclassrooms.paymybuddy.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.math3.util.Precision;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.openclassrooms.paymybuddy.models.SendInfosListHomeModel;
import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.models.TransferMoneyModel;
import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.ISendRepository;
import com.openclassrooms.paymybuddy.repository.IUserRepository;
import com.openclassrooms.paymybuddy.services.IFormService;
import com.openclassrooms.paymybuddy.services.ISendService;

@Service
public class SendServiceImpl implements ISendService {

    @Autowired
    ISendRepository sendRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IFormService formService;

    private static Logger logger = LogManager.getLogger(SendServiceImpl.class);

    @Override
    public List<SendInfosListHomeModel> sendInfosList(String email) {
	List<SendInfosListHomeModel> result = new ArrayList<>();
	List<SendModel> sendList = new ArrayList<>();
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel userAuthor = new UserModel();
	userAuthor = userRepository.getByEmail(sha256hexEmail);
	logger.debug("Search for the list of sends made by the user " + email);

	if (userAuthor != null) {
	    sendList = sendRepository.getByIdAuthor(userAuthor.getId());

	    if (!sendList.isEmpty()) {
		sendList.forEach(sendResult -> {
		    Optional<UserModel> userRecipient;
		    userRecipient = userRepository.findById(sendResult.getIdRecipient());
		    UserModel userFriend = new UserModel();
		    userFriend = userRecipient.get();
		    SendInfosListHomeModel sendInfos = new SendInfosListHomeModel();
		    sendInfos.setFirstName(userFriend.getFirstName());
		    sendInfos.setDescription(sendResult.getDescription());
		    sendInfos.setSend(sendResult.getAmountSend());
		    result.add(sendInfos);
		});
		logger.info("List of sends successfully recovered");
	    } else {
		logger.info("No send have been done to date by this user " + email);
	    }
	} else {
	    logger.error("No user registered in database matches this email");
	}

	return result;
    }

    @Override
    @Transactional
    public boolean transferMoneyInWallet(String email, TransferMoneyModel transferMoney) {
	boolean result = false;
	boolean formTransfer;
	formTransfer = formService.formTransferMoneyValid(transferMoney);

	if (formTransfer == true) {
	    String sha256hexEmail = DigestUtils.sha256Hex(email);
	    UserModel userAuthor = new UserModel();
	    userAuthor = userRepository.getByEmail(sha256hexEmail);
	    logger.debug("The user " + email + " transfer in your account a amount total of "
		    + transferMoney.getAmountTransfer());

	    if (userAuthor.getFirstName().equals(transferMoney.getFirstNameIbanAccount())
		    && userAuthor.getLastName().equals(transferMoney.getLastNameIbanAccount())) {
		double moneyNow = userAuthor.getWallet();
		double moneyAdd = moneyNow + transferMoney.getAmountTransfer();
		userAuthor.setWallet(moneyAdd);
		userRepository.save(userAuthor);
		result = true;
		logger.info("Transfer validated with successfully");
	    } else {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		logger.error("Error please enter your firstname and lastname from your bank account");
	    }
	} else {
	    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    logger.error("Error, please complete all information fields");
	}
	return result;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean sendMoney(String email, SendModel sendModel) {
	boolean result = false;
	boolean formSendValid = formService.formSendValid(sendModel);
	if (formSendValid == true) {
	    UserModel userAuthor = new UserModel();
	    String sha256hexEmail = DigestUtils.sha256Hex(email);
	    userAuthor = userRepository.getByEmail(sha256hexEmail);
	    int idFriend = sendModel.getIdRecipient();
	    Optional<UserModel> userFriend = userRepository.findById(idFriend);
	    UserModel userRecipient = new UserModel();
	    userRecipient = userFriend.get();

	    logger.debug("The user " + userAuthor.getFirstName() + " " + userAuthor.getLastName() + " sends to "
		    + userRecipient.getFirstName() + " " + userRecipient.getLastName());

	    List<UserModel> listUpdateUserDB = new ArrayList<>();
	    double sampling = (sendModel.getAmountSend() * 0.5) / 100;
	    double resultWithSampling = sendModel.getAmountSend() + sampling;
	    double roundResult = Precision.round(resultWithSampling, 2);
	    double roundSampling = Precision.round(sampling, 2);

	    if (userAuthor.getWallet() >= roundResult) {
		SendModel send = new SendModel();
		send.setIdAuthor(userAuthor.getId());
		send.setIdRecipient(userRecipient.getId());
		java.sql.Date dateSQL = new java.sql.Date(new Date().getTime());
		send.setDate(dateSQL);
		send.setAmountSend(sendModel.getAmountSend());
		send.setAmountSampling(roundSampling);
		send.setDescription(sendModel.getDescription());
		sendRepository.save(send);

		userAuthor.setWallet(userAuthor.getWallet() - roundResult);
		listUpdateUserDB.add(userAuthor);
		userRecipient.setWallet(userRecipient.getWallet() + sendModel.getAmountSend());
		listUpdateUserDB.add(userRecipient);
		userRepository.saveAll(listUpdateUserDB);

		result = true;
		logger.info("Send of user " + userAuthor.getFirstName() + " " + userAuthor.getLastName() + " to "
			+ userRecipient.getFirstName() + " " + userRecipient.getLastName() + " successfully");

	    } else {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		logger.error("The amount " + sendModel.getAmountSend() + " is greater than the user’s balance "
			+ userAuthor.getWallet());
	    }
	} else {
	    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    logger.error("The amount must be greater than 0");
	}

	return result;
    }

    @Override
    @Transactional
    public boolean withdrawMoneyInBankAccount(String email, TransferMoneyModel transferMoney) {
	boolean result = false;
	boolean formWithdraw;
	formWithdraw = formService.formTransferMoneyValid(transferMoney);

	if (formWithdraw == true) {
	    String sha256hexEmail = DigestUtils.sha256Hex(email);
	    UserModel user = new UserModel();
	    user = userRepository.getByEmail(sha256hexEmail);
	    logger.debug("The user " + email + " recover in its bank account a amount total of "
		    + transferMoney.getAmountTransfer());

	    if (user.getFirstName().equals(transferMoney.getFirstNameIbanAccount())
		    && user.getLastName().equals(transferMoney.getLastNameIbanAccount())) {

		if (user.getWallet() >= transferMoney.getAmountTransfer()) {
		    double moneyNow = user.getWallet();
		    user.setWallet(moneyNow - transferMoney.getAmountTransfer());
		    userRepository.save(user);
		    result = true;
		    logger.info("WithDraw validated with successfully");
		} else {
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    logger.error(
			    "The balance is insufficient to be able to withdraw this amount from the bank account");
		}
	    } else {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		logger.error("Error please enter your firstname and lastname from your bank account");
	    }
	} else {
	    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    logger.error("Error, please complete all information fields");
	}

	return result;
    }

}
