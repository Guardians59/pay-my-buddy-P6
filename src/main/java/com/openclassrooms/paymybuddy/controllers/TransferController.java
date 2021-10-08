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

@Controller
public class TransferController {

    @Autowired
    ISendService sendService;

    @Autowired
    IFriendListService friendListService;

    @Autowired
    IFormService formService;

    @GetMapping(value = "transfer")
    public String getTransferPage(@CookieValue(value = "userEmail") String email, Model model) {
	TransferMoneyModel transfer = new TransferMoneyModel();
	model.addAttribute("transferMoneyModel", transfer);

	return "transfer";
    }

    @PostMapping(value = "transfer")
    public String postTransfer(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("transferMoneyModel") TransferMoneyModel transfer, Model model) {
	boolean formTransfer;
	formTransfer = formService.formTransferMoneyValid(transfer);
	boolean result = false;
	result = sendService.transferMoneyInWallet(email, transfer);

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