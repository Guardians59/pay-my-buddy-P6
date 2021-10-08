package com.openclassrooms.paymybuddy.services;

import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.models.TransferMoneyModel;
import com.openclassrooms.paymybuddy.models.UserModel;

public interface IFormService {
    
    public boolean formRegisterValid(UserModel user);
    
    public boolean formConnectionValid(UserModel userConnection);
    
    public boolean formTransferMoneyValid(TransferMoneyModel transferMoney);
    
    public boolean formSendValid(SendModel sendModel);

}
