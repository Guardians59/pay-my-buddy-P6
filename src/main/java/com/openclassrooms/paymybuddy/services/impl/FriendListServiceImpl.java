package com.openclassrooms.paymybuddy.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.openclassrooms.paymybuddy.models.FriendListModel;
import com.openclassrooms.paymybuddy.models.FriendNameModel;
import com.openclassrooms.paymybuddy.models.UserModel;
import com.openclassrooms.paymybuddy.repository.IFriendListRepository;
import com.openclassrooms.paymybuddy.repository.IUserRepository;
import com.openclassrooms.paymybuddy.services.IFriendListService;

/**
 * La classe FriendListServiceImpl est l'implémentation de l'interface
 * IFriendListService.
 * 
 * @see IFriendListService
 * @author Dylan
 *
 */
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
	//On récupère les données de l'utilisateur avec l'email hasher.
	user = userRepository.getByEmail(sha256hexEmail);
	//On récupére la liste d'ami de l'utilisateur.
	result = friendListRepository.getByIdUser(user.getId());
	//On instancie une liste FriendName pour récupérer les id, noms et prénoms.
	List<FriendNameModel> listFriendName = new ArrayList<>();
	/*
	 * Si la liste n'est pas vide, on utilise la boucle forEach afin de récupérer
	 * chaques id des différents amis pour les retrouver en base de donnée
	 * et de les ajouter dans liste friendName avec les informations voulues.
	 */
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

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean addFriend(String emailUser, String emailFriend) {
	boolean result = false;
	String sha256hexEmailUser = DigestUtils.sha256Hex(emailUser);
	//On récupère les données de l'utilisateur avec l'email hasher.
	UserModel user = userRepository.getByEmail(sha256hexEmailUser);
	List<Integer> listIdFriend = new ArrayList<>();
	//On récupère la liste d'amis de l'utilisateur.
	listIdFriend = friendListRepository.getByIdUser(user.getId());
	FriendListModel add = new FriendListModel();
	//On instancie une liste du model de l'entité friendList pour sauvegarder en base donnée.
	List<FriendListModel> addFriendList = new ArrayList<>();
	String sha256hexEmailFriend = DigestUtils.sha256Hex(emailFriend);
	UserModel userFriend = userRepository.getByEmail(sha256hexEmailFriend);
	logger.debug("User " + emailUser + " add the user " + emailFriend + " as a friend");
	/*
	 * On vérifie dans un premier temps que l'utilisateur ne cherche pas à ajouter son propre
	 * email en tant qu'ami, puis nous vérifions que le mail indiqué correspond bien à
	 * un utilisateur enregistré, ensuite nous vérifions que l'utilisateur n'est pas déjà
	 * présent dans la liste d'amis.
	 * Si tous ceci est correct on ajoute les id de l'utilisateur connecté et celui de l'ami
	 * dans liste addFriendList afin d'enregistrer les relations d'amitiées dans la
	 * base de donnée.
	 */
	if (!emailUser.equals(emailFriend)) {
	    
	    if (userFriend != null) {
		
		if (!listIdFriend.contains(userFriend.getId())) {

		    add.setIdUser(user.getId());
		    add.setIdFriend(userFriend.getId());
		    addFriendList.add(add);
		    FriendListModel addSecond = new FriendListModel();
		    addSecond.setIdUser(userFriend.getId());
		    addSecond.setIdFriend(user.getId());;
		    addFriendList.add(addSecond);
		    friendListRepository.saveAll(addFriendList);
		    result = true;
		    logger.info("Friendship relationship successfully registered");
		} else {
		    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		    logger.error("The user " + emailFriend + " is already registered as a friend");
		}
	    } else {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		logger.error("Email is not valid, no user found with this email " + emailFriend);
	    } } else {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		logger.error("Error you cannot add your own email to your friend list");
	    }
	
	return result;
    }

}
