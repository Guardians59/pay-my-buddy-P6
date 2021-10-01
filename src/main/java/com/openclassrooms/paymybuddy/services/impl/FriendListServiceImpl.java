package com.openclassrooms.paymybuddy.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.models.FriendNameModel;
import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.IFriendListRepository;
import com.openclassrooms.paymybuddy.repository.IUserRepository;
import com.openclassrooms.paymybuddy.services.IFriendListService;

@Service
public class FriendListServiceImpl implements IFriendListService {

    @Autowired
    IFriendListRepository friendListRepository;

    @Autowired
    IUserRepository userRepository;

    private static Logger logger = LogManager.getLogger(FriendListServiceImpl.class);

    @Override
    public List<FriendNameModel> listFriendName(String email) {
	logger.debug("Search the friends to user " + email);
	List<Integer> result = new ArrayList<>();
	String sha256hexEmail = DigestUtils.sha256Hex(email);
	UserModel user = new UserModel();
	user = userRepository.getByEmail(sha256hexEmail);
	result = friendListRepository.getByIdUser(user.getId());
	List<FriendNameModel> listFriendName = new ArrayList<>();

	if (!result.isEmpty()) {

	    result.forEach(idList -> {
		int idFriend = idList;
		if (idFriend > 0) {
		    Optional<UserModel> userSearch = userRepository.findById(idFriend);
		    UserModel userFriend = userSearch.get();
		    FriendNameModel nameFriend = new FriendNameModel();
		    nameFriend.setId(userFriend.getId());
		    nameFriend.setFirstName(userFriend.getFirstName());
		    nameFriend.setLastName(userFriend.getLastName());
		    listFriendName.add(nameFriend);
		}
	    });
	    logger.info("Friends list successfully retrieved");
	} else {
	    logger.info("The user has no registered friends");
	}
	return listFriendName;

    }

}
