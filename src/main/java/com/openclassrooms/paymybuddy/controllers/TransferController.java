package com.openclassrooms.paymybuddy.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.openclassrooms.paymybuddy.models.FriendNameModel;
import com.openclassrooms.paymybuddy.models.SendInfosListHomeModel;
import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.models.TransferMoneyModel;
import com.openclassrooms.paymybuddy.services.IFormService;
import com.openclassrooms.paymybuddy.services.IFriendListService;
import com.openclassrooms.paymybuddy.services.ISendService;
/**
 * La classe TransferController permet de gérer l'url transfer, afin de transférer
 * de l'argent du compte bancaire de l'utilisateur sur son portefeuille.
 * 
 * @author Dylan
 *
 */
@Controller
public class TransferController {

    @Autowired
    ISendService sendService;

    @Autowired
    IFriendListService friendListService;

    @Autowired
    IFormService formService;
    /**
     * La méthode getTransferPage permet d'obtenir la vue de l'url transfer.
     * @param email l'email de l'utilisateur connecté récupéré via le cookie.
     * @param model pour définir les attributs nécessaires à la vue.
     * @return String la vue transfer.
     */
    @GetMapping(value = "transfer")
    public String getTransferPage(@CookieValue(value = "userEmail") String email, Model model) {
	TransferMoneyModel transfer = new TransferMoneyModel();
	model.addAttribute("transferMoneyModel", transfer);

	return "transfer";
    }
    /**
     * La méthode postTransfer permet de poster le transfert d'argent du compte bancaire
     * sur le portefeuille de l'utilisateur.
     * @param email l'email de l'utilisateur connecté récupéré via le cookie.
     * @param transfer le model transferMoney pour récupérer les informations
     * du transfert.
     * @param model pour définir les attributs nécessaires à la vue.
     * @return String la vue transfer si une erreur est rencontrée, ou la vue home
     * si le transfert est exécuté avec succès. 
     */
    @PostMapping(value = "transfer")
    public String postTransfer(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("transferMoneyModel") TransferMoneyModel transfer, Model model) {
	boolean formTransfer;
	formTransfer = formService.formTransferMoneyValid(transfer);
	boolean result = false;
	result = sendService.transferMoneyInWallet(email, transfer);
	/*
	 * Si tous les champs d'informations ne sont pas renseignés, alors on enverra
	 * l'utilisateur sur la page transfer avec un message d'erreur lui indiquant.
	 * Si une erreur quelconque survient lors de la tentative de transfert
	 * alors on envarra l'utilisateur sur la page transfer avec un message d'erreur
	 * l'en informant.
	 * Si le transfert est validé alors on enverra l'utilisateur sur la page home,
	 * avec un message lui indiquant la validation de son transfert.
	 */
	if (formTransfer == false) {
	    model.addAttribute("transferFieldError", "Veuillez remplir tous les champs d'informations");
	    return "transfer";
	} else if (result == true) {
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    SendModel sendInfosModel = new SendModel();
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendInfos", sendInfosModel);

	    model.addAttribute("transferSucces",
		    "Transfert exécuté avec succès, votre compte à bien été approvisionné");
	    return "home";
	} else {
	    model.addAttribute("transferError",
		    "Erreur lors de la tentative d'approvisionnement de votre compte, veuillez réessayer");
	    return "transfer";
	}

    }
}