package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

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
import com.openclassrooms.paymybuddy.services.IFriendListService;
import com.openclassrooms.paymybuddy.services.ISendService;
/**
 * La classe AddFriendController permet de gérer l'url addFriend, afin que l'utilisateur
 * puisse ajouter un ami dans sa liste friend_list en base de donnée.
 * 
 * @author Dylan
 *
 */
@Controller
public class AddFriendController {

    @Autowired
    IFriendListService friendListService;

    @Autowired
    ISendService sendService;

   /**
    * La classe getAddFriendPage permet d'obtenir la vue de l'url addFriend.
    * @param email l'email de l'utilisateur connecté via le cookie.
    * @param addFriend le model utilisé par la vue pour ajouter un ami.
    * @return String la vue addFriend.
    */
    @GetMapping(value = "addFriend")
    public String getAddFriendPage(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("addFriend") UserModel addFriend) {

	return "addFriend";
    }
    /**
     * La méthode postAddFriend permet de poster l'enregistrement du lien d'amitié, 
     * entre l'utilisateur et un ami également enregistré dans la base de donnée.
     * @param email l'email de l'utilisateur connecté récupéré via le cookie.
     * @param addFriend le model de l'entité user afin de récupérer l'email de l'ami.
     * @param bindingResult pour vérifier que le champ email est remplie par une adresse
     * conforme au format email.
     * @param model pour definir des attributs nécessaires à la vue.
     * @return String la vue addFriend si une erreur est rencontrée, ou la vue home
     * si l'enregistrement de la relation d'amitié est validé.
     */
    @PostMapping(value = "addFriend")
    public String postAddFriend(@CookieValue(value = "userEmail") String email,
	    @Valid @ModelAttribute("addFriend") UserModel addFriend, BindingResult bindingResult, Model model) {
	boolean result = false;
	String emailFriend = addFriend.getEmail();
	result = friendListService.addFriend(email, emailFriend);
	/*
	 * Si le champs email est composé d'un texte non conforme au format email,
	 * ou si l'utilisateur cherche a ajouter son propre email cela enverra une
	 * erreur avec la page addFriend.
	 * Si l'utilisateur entre un email valide mais non enregistré en base de
	 * donnée ou déjà présent dans sa liste d'ami, alors il recevra également
	 * une erreur sur la page addFriend.
	 * Si l'utilisateur entre un email valide, enregistré et non présent dans sa
	 * liste d'amis, alors il recevra un message l'informant de la validation
	 * de l'enregistrement de la relation d'amitié sur la page home.
	 */
	if (bindingResult.hasErrors()) {
	    return "addFriend";
	} else if (email.equals(emailFriend) == true) {
	    model.addAttribute("errorEmail", "Erreur, vous ne pouvez pas ajouter votre propre email à votre liste d'ami");
	    return "addFriend";
	} else if (result == true) {

	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    SendModel sendInfos = new SendModel();
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendInfos", sendInfos);
	    model.addAttribute("addConnectionSucces", "Ajout de l'utilisateur dans la liste d'ami validé");
	    return "home";
	} else {
	    model.addAttribute("error",
		    "Erreur, l'email ne correspond à aucun utilisateur ou est déjà présent dans votre liste d'ami");
	    return "addFriend";
	}
    }
}
