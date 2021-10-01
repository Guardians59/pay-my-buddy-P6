package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.models.SendModel;

@Repository
public interface ISendRepository extends CrudRepository<SendModel, Integer> {
    
    @Query(value = "SELECT * FROM send WHERE id_author = ?1", nativeQuery = true)
    List<SendModel> getByIdAuthor (int idAuthor);

}
