package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.models.SendModel;
/**
 * L'interface ISendRepository permet de récupérer/enregistrer/modifier/supprimer
 * les données de l'entité send en base de donnée.
 * 
 * @author Dylan
 *
 */
@Repository
public interface ISendRepository extends CrudRepository<SendModel, Integer> {
    /*
     * La query getByIdAuthor permet de récupérer la liste des envois effectués
     * par l'utilisateur connecté, via son id.
     */
    @Query(value = "SELECT * FROM send WHERE id_author = ?1", nativeQuery = true)
    List<SendModel> getByIdAuthor (int idAuthor);

}
