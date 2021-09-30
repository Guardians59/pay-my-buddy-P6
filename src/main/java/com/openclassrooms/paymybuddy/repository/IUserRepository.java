package com.openclassrooms.paymybuddy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.models.UserModel;

@Repository
public interface IUserRepository extends CrudRepository<UserModel, Integer> {
    
    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    UserModel getByEmail(String email);

}
