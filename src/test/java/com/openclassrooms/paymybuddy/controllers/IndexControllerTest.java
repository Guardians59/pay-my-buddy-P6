package com.openclassrooms.paymybuddy.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTest {
    
    @Autowired
    MockMvc mockMvc;
    
    @Test
    @DisplayName("Test de la vue de la page index")
    public void getIndexPageTest() throws Exception {
	mockMvc.perform(get("/index"))
		.andExpect(view().name("index"))
		.andExpect(status().isOk());
    }

}
