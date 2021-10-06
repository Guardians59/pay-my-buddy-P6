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
import com.openclassrooms.paymybuddy.models.SendInfosModel;
import com.openclassrooms.paymybuddy.services.IFriendListService;
import com.openclassrooms.paymybuddy.services.ISendService;

@Controller
public class SendController {

    @Autowired
    ISendService sendService;

    @Autowired
    IFriendListService friendListService;

    @PostMapping(value = "send")
    public String postSendToFriend(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("sendInfos") SendInfosModel sendInfos, Model model) {

	boolean result = false;
	result = sendService.sendMoney(email, sendInfos);

	if (result == true) {
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendSucces", "Envoie effectué avec succès");
	    SendInfosModel newSendInfos = new SendInfosModel();
	    model.addAttribute("sendInfos", newSendInfos);

	    return "home";
	} else {
	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    model.addAttribute("friendName", friendName);
	    model.addAttribute("sendInfosList", sendInfosList);
	    model.addAttribute("sendError", "Erreur lors de l'envoie, votre solde est insuffisant");
	    SendInfosModel newSendInfos = new SendInfosModel();
	    model.addAttribute("sendInfos", newSendInfos);
	    
	    return "home";
	}
    }

}
