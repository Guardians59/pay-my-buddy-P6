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
public class WithdrawController {
    
    @Autowired
    ISendService sendService;
    
    @Autowired
    IFriendListService friendListService;
    
    @Autowired
    IFormService formService;
    
    @GetMapping(value = "withdraw")
    public String getWithDrawPage(@CookieValue(value = "userEmail") String email, Model model) {
	TransferMoneyModel transfer = new TransferMoneyModel();
	model.addAttribute("transfer", transfer);
	
	return "withdraw";
    }
    
    @PostMapping(value = "withdraw")
    public String postWithDraw(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("transfer") TransferMoneyModel transfer, Model model) {
	boolean formTransferValid;
	formTransferValid = formService.formTransferMoneyValid(transfer);
	boolean result = false;
	result = sendService.withdrawMoneyInBankAccount(email, transfer);
	
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
