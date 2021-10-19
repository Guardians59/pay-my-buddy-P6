package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.models.FriendNameModel;
import com.openclassrooms.paymybuddy.models.SendInfosListHomeModel;
import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.services.IFormService;
import com.openclassrooms.paymybuddy.services.IFriendListService;
import com.openclassrooms.paymybuddy.services.ISendService;
/**
 * La classe SendController permet de gérer l'url send, afin d'effectuer les
 * envois d'argent entre amis.
 * 
 * @author Dylan
 *
 */
@Controller
public class SendController {

    @Autowired
    ISendService sendService;

    @Autowired
    IFriendListService friendListService;

    @Autowired
    IFormService formService;

    /**
     * La méthode postSendToFriend permet de poster l'envoi d'argent à un ami.
     * @param email l'email de l'utilisateur connecté récupéré via le cookie.
     * @param sendInfos le model de l'entité send afin d'enregistrer en base de
     * données toutes les informations nécessaires.
     * @param model pour définir les attributs nécessaire à la vue.
     * @return String la vue home.
     */
    @PostMapping(value = "send")
    public String postSendToFriend(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("sendInfos") SendModel sendInfos, Model model) {

	boolean result = false;
	boolean formSendValid;
	result = sendService.sendMoney(email, sendInfos);
	formSendValid = formService.formSendValid(sendInfos);
	/*
	 * On vérifie que le montant renseigner par l'utilisateur est supérieur à zéro,
	 * auquel cas nous renverrons l'utilisateur sur la page home avec un message d'erreur
	 * l'en informant.
	 * On vérifie que le solde de l'utilisateur est supérieur au montant total
	 * (montant envoyé à l'ami plus frais de 0,5% de celui-ci) auquel cas nous
	 * renverrons l'utilisateur sur la page home avec un message d'erreur lui
	 * indiquant.
	 * Si toutes les étapes sont corrects alors nous renverrons l'utilisateur
	 * sur la page home, avec un message lui indiquant le succès de la transaction.
	 */
	if (formSendValid == false) {
	    model.addAttribute("sendAmountError", "Erreur, vous devez envoyer un montant supérieur à zéro");
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    SendModel newSendInfos = new SendModel();
	    model.addAttribute("sendInfos", newSendInfos);
	    
	    return "home";
	} else if (result == true) {
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendSucces", "Envoie effectué avec succès");
	    SendModel newSendInfos = new SendModel();
	    model.addAttribute("sendInfos", newSendInfos);

	    return "home";
	} else {
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendError", "Erreur lors de l'envoie, votre solde est insuffisant");
	    SendModel newSendInfos = new SendModel();
	    model.addAttribute("sendInfos", newSendInfos);

	    return "home";
	}
    }

}
