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
import com.openclassrooms.paymybuddy.models.SendInfosModel;
import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.services.IFriendListService;
import com.openclassrooms.paymybuddy.services.ISendService;

@Controller
public class AddFriendController {

    @Autowired
    IFriendListService friendListService;

    @Autowired
    ISendService sendService;

    @GetMapping(value = "addFriend")
    public String getAddFriendPage(@CookieValue(value = "userEmail") String email,
	    @ModelAttribute("addFriend") UserModel addFriend) {

	return "addFriend";
    }

    @PostMapping(value = "addFriend")
    public String addFriend(@CookieValue(value = "userEmail") String email,
	    @Valid @ModelAttribute("addFriend") UserModel addFriend, BindingResult bindingResult, Model model) {
	boolean result = false;
	String emailFriend = addFriend.getEmail();
	result = friendListService.addFriend(email, emailFriend);

	if (bindingResult.hasErrors()) {
	    return "addFriend";
	} else if (email.equals(emailFriend) == true) {
	    model.addAttribute("errorEmail", "Erreur, vous ne pouvez pas ajouter votre propre email à votre liste d'ami");
	    return "addFriend";
	} else if (result == true) {

	    List<FriendNameModel> friendName = friendListService.listFriendName(email);
	    List<SendInfosListHomeModel> sendInfosList = sendService.sendInfosList(email);
	    SendInfosModel sendInfos = new SendInfosModel();
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
