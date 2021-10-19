package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.models.FriendNameModel;
import com.openclassrooms.paymybuddy.models.SendInfosListHomeModel;
import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.services.IFormService;
import com.openclassrooms.paymybuddy.services.IFriendListService;
import com.openclassrooms.paymybuddy.services.ISendService;
import com.openclassrooms.paymybuddy.services.IUserService;

/**
 * La classe HomeController permet la connexion de l'utilisateur depuis l'index,
 * afin d'arriver sur l'url home, page principal de l'application.
 * 
 * @author Dylan
 *
 */
@Controller
public class HomeController {
    
    @Autowired
    IUserService userService;
    
    @Autowired
    IFriendListService friendListService;
    
    @Autowired
    ISendService sendService;
    
    @Autowired
    IFormService formService;
    
    /**
     * La classe getConnectionPage permet d'obtenir la vue de l'url home.
     * @param email l'email de l'utilisateur connecté via le cookie.
     * @param model pour définir les attributs nécessaires à la vue.
     * @return String la vue home.
     */
    @GetMapping(value = "home")
    public String getConnectionPage(@CookieValue(value = "userEmail") String email, Model model) {
	List<FriendNameModel> friendName = friendListService.listFriendName(email);
	model.addAttribute("friendName", friendName);
	List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	model.addAttribute("sendInfosList", sendInfosList);
	SendModel sendInfos = new SendModel();
	model.addAttribute("sendInfos", sendInfos);

	return "home";
    }
    /**
     * La méthode postUserConnection permet de se connecter à l'application depuis
     * la page index.
     * @param userConnection le model de l'entité user afin de vérifier si l'utilisateur
     * est bien enregistré en base de donnée.
     * @param bindingResult pour vérifier que le champs email soit bien remplis par
     * du texte au format email.
     * @param model pour définir des attributs nécessaires à la vue.
     * @param httpServlet pour définir un cookie permettant de maintenir la connexion
     * de l'utilisateur sur les differentes pages.
     * @return String la vue de l'index si une erreur est rencontrée lors de la
     * tentative de connexion, ou la vue home si la connexion est réussi.
     */
    @PostMapping(value = "home")
    public String postUserConnection(@Valid @ModelAttribute("userModel") UserModel userConnection, BindingResult bindingResult, Model model, 
	    HttpServletResponse httpServlet) {
	boolean formConnectionValid;
	formConnectionValid = formService.formConnectionValid(userConnection);
	boolean result = false;
	result = userService.userExist(userConnection);
	model.addAttribute("result", result);
	/*
	 * On vérifie que les champs du formulaire soient remplis correctement et
	 * que l'email soit bien au format email, auquel cas nous renverrons
	 * l'utilisateur sur la page index avec un message d'erreur.
	 * Si tous les champs sont remplis correctement mais que l'utilisateur
	 * entre un email non enregistré ou un mauvais mot de passe, nous renverrons
	 * l'utilisateur sur la page index avec un message d'erreur.
	 * Si la connexion est réussi alors nous enverrons l'utilisateur sur la page
	 * home, tout en créant un cookie pour maintenir la connexion.
	 */
	if (bindingResult.hasErrors() || formConnectionValid == false) {
	    model.addAttribute("errorField", "Veuillez remplir tous les champs du formulaire");
	    return "index";
	} else if (result == true) {
	    var email = userConnection.getEmail();
	    Cookie cookie = new Cookie("userEmail", email);
	    httpServlet.addCookie(cookie);
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    SendModel sendInfos = new SendModel();
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendInfos", sendInfos);

	    return "home";
	} else {
	    model.addAttribute("error", "Erreur d'authentification, veuillez réesayer");
	    return "index";
	}

    }

}
