package com.openclassrooms.paymybuddy.services.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.IUserRepository;
import com.openclassrooms.paymybuddy.services.IFormService;
import com.openclassrooms.paymybuddy.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IFormService formService;

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean addUser(UserModel user) {

	boolean result = false;
	boolean formRegister;
	formRegister = formService.formRegisterValid(user);

	if (formRegister == true) {

	    UserModel newUser = new UserModel();
	    String email = user.getEmail();
	    String sha256hexEmail = DigestUtils.sha256Hex(email);
	    UserModel userRegistred = userRepository.getByEmail(sha256hexEmail);
	    logger.debug("User registration with email " + email);

	    if (userRegistred == null) {
		newUser.setEmail(sha256hexEmail);
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		String password = user.getPassword();
		String sha256hex = DigestUtils.sha256Hex(password);
		newUser.setPassword(sha256hex);
		userRepository.save(newUser);
		result = true;
		logger.info("Successful registration");

	    } else {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		logger.error("The specified email already belongs to a registered user");
	    }

	} else {
	    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	    logger.error("Error, please complete all registration fields");
	}

	return result;
    }

    @Override
    public boolean userExist(UserModel userConnection) {
	boolean result = false;
	boolean formConnection;
	formConnection = formService.formConnectionValid(userConnection);

	if (formConnection == true) {
	    String email = userConnection.getEmail();
	    String sha256hexEmail = DigestUtils.sha256Hex(email);
	    UserModel user = new UserModel();
	    user = userRepository.getByEmail(sha256hexEmail);
	    logger.debug("User login with email " + userConnection.getEmail());

	    if (user != null) {
		String password = userConnection.getPassword();
		String sha256hex = DigestUtils.sha256Hex(password);
		if (sha256hex.equals(user.getPassword())) {
		    result = true;
		    logger.info("User exists in database, successful login");
		} else {
		    logger.error("Incorrect password");
		}
	    } else {
		logger.error("Incorrect authentication, no user found with this email");
	    }
	} else {
	    logger.error("Error, please complete all connection fields");
	}

	return result;
    }
}
