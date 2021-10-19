package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.models.UserModel;
/**
 * L'interface IUserRepository permet de récupérer/enregistrer/modifier/supprimer
 * les données de l'entité user en base de donnée.
 * 
 * @author Dylan
 *
 */
@Repository
public interface IUserRepository extends CrudRepository<UserModel, Integer> {
    /*
     * La query getByEmail permet de récupérer les données d'un utilisateur via
     * son email.
     */
    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    UserModel getByEmail(String email);

}
