package com.openclassrooms.paymybuddy.services;

import com.openclassrooms.paymybuddy.models.UserModel;
/**
 * L'interface IUserService est le service qui permet de gérer l'inscription des
 * utilisateurs, de les enregistré en base donnée et de vérifier qu'un utilisateur
 * est connu en base de donnée lorsqu'il veut se connecter.
 * 
 * @author Dylan
 *
 */
public interface IUserService {
    /**
     * La méthode addUser permet de gérer l'inscription d'un utilisateur et de
     * l'enregistré en base de donnée.
     * @param user le model de l'entité user enregistrée en base de donnée afin de
     * d'enregistré les informations nécessaires.
     * @return boolean true si l'inscription est exécutée avec succès et l'utilisateur
     * bien enregistré en base de donnée.
     */
    public boolean addUser(UserModel user);
    /**
     * La méthode userExist nous permet de vérifier l'existence d'un utilisateur
     * en base de donnée lorsque celui-ci veut se connecter.
     * @param userConnection le model de l'entité user afin de vérifier le mail et
     * mot de passe de l'utilisateur.
     * @return boolean true si l'utilisateur est bien enregistré en base de donnée.
     */
    public boolean userExist(UserModel userConnection);

}
