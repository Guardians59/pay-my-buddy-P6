package com.openclassrooms.paymybuddy.services;

import java.util.List;

import com.openclassrooms.paymybuddy.models.SendInfosListHomeModel;
import com.openclassrooms.paymybuddy.models.TransferMoneyModel;

public interface ISendService {
    
    public List<SendInfosListHomeModel> sendInfosList(String email);
    
    public boolean transferMoneyInWallet(String email, TransferMoneyModel transferMoney);

}
