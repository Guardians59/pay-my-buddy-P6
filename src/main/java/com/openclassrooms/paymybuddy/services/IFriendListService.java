package com.openclassrooms.paymybuddy.services;

import java.util.List;

import com.openclassrooms.paymybuddy.models.FriendNameModel;

/**
 * L'interface IFriendListService est le service qui nous permet de récupérer une
 * liste avec les noms et prénoms des utilisateurs enregistrés en tant qu'amis,
 * ainsi que d'ajouter des utilisateurs dans la liste enregistrée en base de données.
 * 
 * @author Dylan
 *
 */
public interface IFriendListService {
    /**
     * La méthode listFriendName nous renvoie la liste des noms et prénoms des amis
     * de l'utilisateur.
     * @param email l'email de l'utilisateur connecté.
     * @return list avec le nom et prénom des utilisateurs enregistrés.
     */
    public List<FriendNameModel> listFriendName(String email);
    /**
     * La méthode addFriend nous permet d'ajouter un utilisateur dans la liste d'ami
     * de l'utilisateur connecté et l'enregistré en base de donnée.
     * @param emailUser l'email de l'utilisateur connecté.
     * @param emailFriend l'email de l'utilisateur à ajouter.
     * @return boolean true si la sauvegarde en base de donnée est un succès.
     */
    public boolean addFriend(String emailUser, String emailFriend);

}
