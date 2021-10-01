package com.openclassrooms.paymybuddy.services;

import com.openclassrooms.paymybuddy.models.UserModel;

public interface IFormService {
    
    public boolean formRegisterValid(UserModel user);
    
    public boolean formConnectionValid(UserModel userConnection);

}
