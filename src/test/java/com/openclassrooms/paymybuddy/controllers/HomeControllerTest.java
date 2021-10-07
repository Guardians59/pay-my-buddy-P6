package com.openclassrooms.paymybuddy.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test de la vue de la page home")
    public void getHomePageTest() throws Exception {
	String email = "test@gmail.com";
	Cookie cookie = new Cookie("userEmail", email);
	
	mockMvc.perform(get("/home").cookie(cookie))
		.andExpect(view().name("home"))
		.andExpect(status().isOk());
    }

}
