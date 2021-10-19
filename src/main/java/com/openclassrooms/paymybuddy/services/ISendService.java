package com.openclassrooms.paymybuddy.services;

import java.util.List;

import com.openclassrooms.paymybuddy.models.SendInfosListHomeModel;
import com.openclassrooms.paymybuddy.models.SendModel;
import com.openclassrooms.paymybuddy.models.TransferMoneyModel;
/**
 * L'interface ISendService est le service qui nous permet de gérer les différents
 * transferts/envois d'argent ainsi que de récupérer une liste d'informations
 * des envois effectués.
 * 
 * @author Dylan
 *
 */
public interface ISendService {
    /**
     * La méthode sendInfosList récupère la liste des envois effectués par
     * l'utilisateur, avec le prénom du bénéficiaire, la description et le montant
     * de ses envois.
     * @param email l'email de l'utilisateur connecté.
     * @return list une liste avec les prénoms des bénéficiaires, les descriptions
     * et le montant des envois effectués.
     */
    public List<SendInfosListHomeModel> sendInfosList(String email);
    /**
     * La méthode transferMoneyInWallet nous permet de transferer l'argent du
     * compte bancaire de l'utilisateur sur son portefeuille.
     * @param email l'email de l'utilisateur connecté.
     * @param transferMoney les informations du transfert (Iban, nom, prénom du
     * compte et le montant).
     * @return boolean true si le transfert est exécuté avec succès.
     */
    public boolean transferMoneyInWallet(String email, TransferMoneyModel transferMoney);
    /**
     * La méthode sendMoney permet d'envoyer de l'argent à un ami et d'enregistré
     * les informations en base de donnée.
     * @param email l'email de l'utilisateur connecté.
     * @param sendModel le model de l'entité send enregistrée en base de donnée avec
     * toutes les informations nécessaires (l'auteur, le bénéficiaire, la date,
     * le montant, le prélèvement et la description).
     * @return boolean true si l'envoie est validé et enregistré en base de donnée.
     */
    public boolean sendMoney(String email, SendModel sendModel);
    /**
     * La méthode withdrawMoneyInBankAccount nous permet de transferer l'argent
     * du portefeuille au compte bancaire de l'utilisateur.
     * @param email l'email de l'utilisateur connecté.
     * @param transferMoney les informations du transfert (Iban, nom, prénom du
     * compte et le montant).
     * @return boolean true si le transfert est exécuté avec succès.
     */
    public boolean withdrawMoneyInBankAccount(String email, TransferMoneyModel transferMoney);

}
