package com.openclassrooms.paymybuddy.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
