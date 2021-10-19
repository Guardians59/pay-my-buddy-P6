package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.models.FriendListModel;
/**
 * L'interface IFriendListRepository permet de récupérer/enregistrer/modifier/
 * supprimer les données de l'entité friend_list en base de donnée.
 * 
 * @author Dylan
 *
 */
@Repository
public interface IFriendListRepository extends CrudRepository<FriendListModel, Integer>{
    /*
     * La query getByIdUser permet de récupérer une liste des id des utilisateurs
     * enregistrés en tant qu'ami à l'utilisateur connecté, via son id.
     */
    @Query(value = "SELECT id_friend FROM friend_list WHERE id_user = ?1", nativeQuery = true)
    List<Integer> getByIdUser(int id);

}
