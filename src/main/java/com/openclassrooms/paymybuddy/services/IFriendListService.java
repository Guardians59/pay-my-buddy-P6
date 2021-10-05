package com.openclassrooms.paymybuddy.services;

import java.util.List;

import com.openclassrooms.paymybuddy.models.FriendNameModel;

public interface IFriendListService {
    
    public List<FriendNameModel> listFriendName(String email);
    
    public boolean addFriend(String emailUser, String emailFriend);

}
