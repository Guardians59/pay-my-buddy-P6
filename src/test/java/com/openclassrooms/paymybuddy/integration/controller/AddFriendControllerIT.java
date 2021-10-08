package com.openclassrooms.paymybuddy.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.paymybuddy.models.FriendListModel;
import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.IFriendListRepository;;

@SpringBootTest
@AutoConfigureMockMvc
public class AddFriendControllerIT {
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    IFriendListRepository friendListRepository;
    
    @Test
    @DisplayName("Test de l'ajout d'un ami")
    public void addFriendTest() throws Exception {
	String email = "test@gmail.com";
	UserModel addFriend = new UserModel();
	addFriend.setEmail("test4@gmail.com");
	Cookie cookie = new Cookie("userEmail", email);
	
	mockMvc.perform(post("/addFriend").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("addFriend", addFriend))
		.andExpect(view().name("home"))
		.andExpect(model().attributeExists("addConnectionSucces"))
		.andExpect(status().isOk());
	
	FriendListModel connectionFriend = new FriendListModel();
	connectionFriend.setIdUser(1);
	connectionFriend.setIdFriend(4);
	friendListRepository.delete(connectionFriend);
	connectionFriend.setIdUser(4);
	connectionFriend.setIdFriend(1);
	friendListRepository.delete(connectionFriend);
	
    }
    
    @Test
    @DisplayName("Test de l'erreur, l'email de l'ami est déjà enregistré dans la liste d'ami")
    public void addConnectionFriendErrorTest() throws Exception {
	String email = "test@gmail.com";
	UserModel addFriend = new UserModel();
	addFriend.setEmail("test2@gmail.com");
	Cookie cookie = new Cookie("userEmail", email);
	
	mockMvc.perform(post("/addFriend").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("addFriend", addFriend))
		.andExpect(view().name("addFriend"))
		.andExpect(model().attributeExists("error"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test de l'erreur l'utilisateur ajoute son propre email")
    public void addConnectionFriendErrorSameEmailTest() throws Exception {
	String email = "test@gmail.com";
	UserModel addFriend = new UserModel();
	addFriend.setEmail(email);
	Cookie cookie = new Cookie("userEmail", email);
	
	mockMvc.perform(post("/addFriend").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("addFriend", addFriend))
		.andExpect(view().name("addFriend"))
		.andExpect(model().attributeExists("errorEmail"))
		.andExpect(status().isOk());
	
    }
    
    @Test
    @DisplayName("Test de l'erreur l'utilisateur ajoute un email non enregistré en DB")
    public void addConnectionFriendErrorEmailNotValidTest() throws Exception {
	String email = "test@gmail.com";
	UserModel addFriend = new UserModel();
	addFriend.setEmail("testError@gmail.com");
	Cookie cookie = new Cookie("userEmail", email);
	
	mockMvc.perform(post("/addFriend").contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.cookie(cookie)
		.flashAttr("addFriend", addFriend))
		.andExpect(view().name("addFriend"))
		.andExpect(model().attributeExists("error"))
		.andExpect(status().isOk());
	
    }

}
