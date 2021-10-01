package com.openclassrooms.paymybuddy.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.IUserRepository;
import com.openclassrooms.paymybuddy.services.IFormService;

@Service
public class FormServiceImpl implements IFormService {
    
    @Autowired
    IUserRepository userRepository;

    @Override
    public boolean formRegisterValid(UserModel user) {
	boolean result = true;

	if ((user.getEmail() == null || user.getEmail().isBlank())
		|| (user.getFirstName() == null || user.getFirstName().isBlank())
		|| (user.getLastName() == null || user.getLastName().isBlank())
		|| (user.getPassword() == null || user.getPassword().isBlank())) {

	    result = false;
	}
	return result;
    }

    @Override
    public boolean formConnectionValid(UserModel userConnection) {
	boolean result = true;

	if ((userConnection.getEmail() == null || userConnection.getEmail().isBlank())
		|| (userConnection.getPassword() == null || userConnection.getPassword().isBlank())) {

	    result = false;
	}
	return result;
    }

}
