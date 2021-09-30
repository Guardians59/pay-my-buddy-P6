package com.openclassrooms.paymybuddy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.openclassrooms.paymybuddy.models.UserModel;

@Controller
public class IndexController {

    @GetMapping(value = "index")
    public String getIndex(Model model) {
	UserModel user = new UserModel();
	model.addAttribute("userModel", user);
	return "index";
    }
}
