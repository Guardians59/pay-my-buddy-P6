package com.openclassrooms.paymybuddy.services;

import java.util.List;

import com.openclassrooms.paymybuddy.models.SendInfosListHomeModel;

public interface ISendService {
    
    public List<SendInfosListHomeModel> sendInfosList(String email);

}
