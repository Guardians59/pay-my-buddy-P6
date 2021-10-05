package com.openclassrooms.paymybuddy.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.paymybuddy.models.FriendListModel;
import com.openclassrooms.paymybuddy.models.FriendNameModel;
import com.openclassrooms.paymybuddy.repository.IFriendListRepository;

@SpringBootTest
public class FriendListServiceTest {

    @Autowired
    IFriendListRepository friendListRepository;

    @Autowired
    IFriendListService friendListService;

    @Test
    @DisplayName("Test que l'on récupère le nom et prénom des amis")
    public void getNameFriendTest() {
	// GIVEN
	List<FriendNameModel> result = new ArrayList<>();
	String email = "test@gmail.com";
	// WHEN
	result = friendListService.listFriendName(email);
	// THEN
	assertEquals(result.get(0).getFirstName(), "Gael");
	assertEquals(result.get(0).getLastName(), "Kakuta");

    }

    @Test
    @DisplayName("Test que la liste est vide si l'utilisateur n'a pas d'ami enregistré")
    public void listFriendNameEmptyTest() {
	// GIVEN
	List<FriendNameModel> result = new ArrayList<>();
	String email = "test4@gmail.com";
	// WHEN
	result = friendListService.listFriendName(email);
	// THEN
	assertEquals(result.isEmpty(), true);
    }

    @Test
    @DisplayName("Test de l'ajout d'un ami")
    public void addFriendTest() {
	// GIVEN
	List<Integer> result = new ArrayList<>();
	String emailUser = "test2@gmail.com";
	String emailFriend = "test3@gmail.com";
	result = friendListRepository.getByIdUser(2);
	int numberFriendAfter = result.size() + 1;
	// WHEN
	boolean resultAdd = friendListService.addFriend(emailUser, emailFriend);
	result = friendListRepository.getByIdUser(2);
	// THEN
	assertEquals(resultAdd, true);
	assertEquals(result.size(), numberFriendAfter);

	FriendListModel friendList = new FriendListModel();
	friendList.setIdUser(2);
	friendList.setIdFriend(3);
	friendListRepository.delete(friendList);
	friendList.setIdUser(3);
	friendList.setIdFriend(2);
	friendListRepository.delete(friendList);
    }

    @Test
    @DisplayName("Test de l'erreur, l'ami est déjà dans la liste d'ami")
    public void addFriendErrorUserAlreadyInListTest() {
	// GIVEN
	List<Integer> result = new ArrayList<>();
	String emailUser = "test2@gmail.com";
	String emailFriend = "test@gmail.com";
	result = friendListRepository.getByIdUser(2);
	int numberFriendAfter = result.size();
	// WHEN
	boolean resultAdd = friendListService.addFriend(emailUser, emailFriend);
	result = friendListRepository.getByIdUser(2);
	// THEN
	assertEquals(resultAdd, false);
	assertEquals(result.size(), numberFriendAfter);

    }

    @Test
    @DisplayName("Test de l'erreur, l'ami n'est pas enregistré en base de donnée")
    public void addFriendErrorEmailFriendNotRegisteredTest() {
	// GIVEN
	List<Integer> result = new ArrayList<>();
	String emailUser = "test2@gmail.com";
	String emailFriend = "testError@gmail.com";
	result = friendListRepository.getByIdUser(2);
	int numberFriendAfter = result.size();
	// WHEN
	boolean resultAdd = friendListService.addFriend(emailUser, emailFriend);
	result = friendListRepository.getByIdUser(2);
	// THEN
	assertEquals(resultAdd, false);
	assertEquals(result.size(), numberFriendAfter);
    }

    @Test
    @DisplayName("Test de l'erreur, l'utilisateur ajoute son propre email")
    public void addFriendErrorUserAddHisEmailTest() {
	//GIVEN
	List<Integer> result = new ArrayList<>();
	String emailUser = ("test4@gmail.com");
	String emailFriend = ("test4@gmail.com");
	result = friendListRepository.getByIdUser(4);
	int numberFriendAfter = result.size();
	//WHEN
	boolean resultAdd = friendListService.addFriend(emailUser, emailFriend);
	result = friendListRepository.getByIdUser(4);
	//THEN
	assertEquals(resultAdd, false);
	assertEquals(result.size(), numberFriendAfter);
	
    }
}
