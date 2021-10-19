package com.openclassrooms.paymybuddy.services;

import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.models.TransferMoneyModel;
import com.openclassrooms.paymybuddy.models.UserModel;

/**
 * L'interface IFormService est le service qui nous permet de vérifier que les
 * données sur les différents formulaires ne soient pas blank.
 *  
 * @author Dylan
 *
 */
public interface IFormService {
    
    /**
     * La méthode formRegisterValid vérifie le formulaire d'inscription, que toutes
     * les données attendues soient renseignées.
     * 
     * @param user l'entité user enregistrée en base de donnée afin de récupérer 
     * les données essentielles.
     * @return boolean true si toutes les données sont renseignées.
     */
    public boolean formRegisterValid(UserModel user);
    
    /**
     * La méthode formConnectionValid vérifie le formulaire de connection, que toutes
     * les données attendues soient renseignées.
     * 
     * @param userConnection l'entité user enregistrée en base de donnée pour vérifier
     * l'email et mot de passe.
     * @return boolean true si toutes les données sont renseignées.
     */
    
    public boolean formConnectionValid(UserModel userConnection);
    
    /**
     * La méthode formTransferMoneyValid vérifie le formulaire de transfert d'argent
     * entre le portefeuille et le compte bancaire de l'utilisateur, que toutes
     * les données attendues soient renseignées.
     * 
     * @param transferMoney le model de transfer ayant toutes les informations 
     * nécessaires.
     * @return boolean true si toutes les données sont renseignées.
     */
    
    public boolean formTransferMoneyValid(TransferMoneyModel transferMoney);
    
    /**
     * La méthode formSendValid vérifie le formulaire d'envoie d'argent, qu'un
     * montant soit bien indiqué.
     * 
     * @param sendModel l'entité send enregistrée en base de donnée pour vérifier 
     * la somme envoyée.
     * @return boolean true si le montant est supérieur à zéro.
     */
    
    public boolean formSendValid(SendModel sendModel);

}
