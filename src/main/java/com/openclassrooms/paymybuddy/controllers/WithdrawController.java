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
 * La classe WithdrawController permet de gérer l'url withdraw, afin de transférer
 * de l'argent du portefeuille de l'utilisateur à son compte bancaire.
 * 
 * @author Dylan
 *
 */
@Controller
public class WithdrawController {
    
    @Autowired
    ISendService sendService;
    
    @Autowired
    IFriendListService friendListService;
    
    @Autowired
    IFormService formService;
    /**
     * La méthode getWithdrawPage permet d'obtenir la vue de l'url withdraw. 
     * @param email l'email de l'utilisateur connecté récupéré via le cookie.
     * @param model pour définir les attributs nécessaires à la vue.
     * @return String la vue withdraw.
     */
    @GetMapping(value = "withdraw")
    public String getWithdrawPage(@CookieValue(value = "userEmail") String email, Model model) {
	TransferMoneyModel transfer = new TransferMoneyModel();
	model.addAttribute("transfer", transfer);
	
	return "withdraw";
    }
    /**
     * La méthode postWithdraw permet de poster le transfert d'argent du portefeuille
     * de l'utilisateur à son compte bancaire.
     * @param email l'email de l'utilisateur connecté récupéré via le cookie.
     * @param transfer le model transferMoney pour récupérer les informations du
     * retrait.
     * @param model pour définir les attributs nécessaire à la vue.
     * @return String la vue withdraw si une erreur est rencontrée avec un message
     * l'indiquant, ou la vue home si le retrait est exécuté avec succès.
     */
    @PostMapping(value = "withdraw")
    public String postWithdraw(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("transfer") TransferMoneyModel transfer, Model model) {
	boolean formTransferValid;
	formTransferValid = formService.formTransferMoneyValid(transfer);
	boolean result = false;
	result = sendService.withdrawMoneyInBankAccount(email, transfer);
	/*
	 * Si tous les champs d'informations ne sont pas renseignés, alors on enverra
	 * l'utilisateur sur la page withdraw avec un message d'erreur lui indiquant.
	 * Si une erreur quelconque survient lors de la tentative de retrait,
	 * alors on envarra l'utilisateur sur la page withdraw avec un message d'erreur
	 * l'en informant.
	 * Si le transfert est validé alors on enverra l'utilisateur sur la page home,
	 * avec un message lui indiquant la validation de son retrait.
	 */
	if(formTransferValid == false) {
	    model.addAttribute("withdrawFieldError", "Veuillez remplir tous les champs d'informations");
	    return "withdraw";
	} else if (result == true) {
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    SendModel sendInfos = new SendModel();
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendInfos", sendInfos);
	    model.addAttribute("withdrawSucces",
		    "Retrait exécuté avec succès");
	    return "home";
	} else {
	    model.addAttribute("withdrawError",
		    "Erreur lors de la tentative de retrait, veuillez réessayer");
	    return "withdraw";
	}
    }

}
