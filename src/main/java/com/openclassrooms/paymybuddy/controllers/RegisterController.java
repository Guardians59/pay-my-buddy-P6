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
/**
 * La classe RegisterController permet de gérer l'url pour s'enregistrer sur l'application.
 * 
 * @author Dylan
 *
 */
@Controller
public class RegisterController {
    
    @Autowired
    IUserService userService;
    
    @Autowired
    IFormService formService;
    
    /**
     * La méthode getRegisterPage permet d'obtenir la vue de l'url register.
     * @param model pour définir des attributs nécessaires à la vue.
     * @return String la vue register.
     */
    @GetMapping(value = "register")
    public String getRegisterPage(Model model) {
	UserModel user = new UserModel();
	model.addAttribute("userModel", user);
	return "register";
    }
    /**
     * La méthode postRegisterNewUser permet de poster l'enregistrement d'un nouvel utilisateur.
     * @param user le model de l'entité user afin d'enregistrer toutes les données dans
     * la base de donnée.
     * @param bindingResult pour vérifier que le champs email soit conforme au format email.
     * @param model pour définir les attributs nécessaire à la vue.
     * @return String la vue register si une erreur est rencontrée, ou la vue index si
     * l'enregistrement à été effectué avec succès.
     */
    @PostMapping(value = "register")
    public String postRegisterNewUser(@Valid @ModelAttribute("userModel") UserModel user, BindingResult bindingResult, Model model) {
	boolean formValid;
	formValid = formService.formRegisterValid(user);
	boolean result = false;
	
	result = userService.addUser(user);
	/*
	 * On vérifie que les champs du formulaire soient remplis correctement et
	 * que l'email soit conforme au format email, auquel cas nous renverrons
	 * l'utilisateur sur la page register avec un message d'erreur.
	 * Si l'email entré est valide mais déjà utilisé pour un autre compte, alors
	 * nous renverrons l'utilisateur sur la page register avec un message d'erreur
	 * l'en informant.
	 * Si toutes les informations sont valident alors nous renverrons l'utilisateur
	 * sur la page index avec un message l'informant de son enregistrement réussi.
	 */
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
