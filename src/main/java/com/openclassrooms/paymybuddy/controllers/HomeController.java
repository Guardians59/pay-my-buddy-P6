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
    
    @PostMapping(value = "home")
    public String postUserConnection(@Valid @ModelAttribute("userModel") UserModel userConnection, BindingResult bindingResult, Model model, 
	    HttpServletResponse httpServlet) {
	boolean formConnectionValid;
	formConnectionValid = formService.formConnectionValid(userConnection);
	boolean result = false;
	result = userService.userExist(userConnection);
	model.addAttribute("result", result);
	
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
