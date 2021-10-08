package com.openclassrooms.paymybuddy.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.services.IFormService;
import com.openclassrooms.paymybuddy.services.IUserService;

@Controller
public class RegisterController {
    
    @Autowired
    IUserService userService;
    
    @Autowired
    IFormService formService;
    
    @GetMapping(value = "register")
    public String getRegisterPage(Model model) {
	UserModel user = new UserModel();
	model.addAttribute("userModel", user);
	return "register";
    }
    
    @PostMapping(value = "register")
    public String registerNewUser(@Valid @ModelAttribute("userModel") UserModel user, BindingResult bindingResult, Model model) {
	boolean formValid;
	formValid = formService.formRegisterValid(user);
	boolean result = false;
	
	result = userService.addUser(user);
	 
	if (bindingResult.hasErrors() || formValid == false) {
	
	 model.addAttribute("fieldError", "Erreur, veuillez renseigner tous les champs du formulaire");
	    return "register";
	} else if (result == true) {
	    UserModel userConnection = new UserModel();
	    model.addAttribute("userModel", userConnection);
	    model.addAttribute("registerValidate", "Inscription éxécutée avec succès, vous pouvez vous connecter");
	    return "index";
	} else {
	    model.addAttribute("registerError", "Erreur, l'email est déjà utilisé pour un compte existant");
	    return "register";
	} 

            }

}
