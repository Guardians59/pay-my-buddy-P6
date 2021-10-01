package com.openclassrooms.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.models.FriendListModel;

@Repository
public interface IFriendListRepository extends CrudRepository<FriendListModel, Integer>{
    
    @Query(value = "SELECT id_friend FROM friend_list WHERE id_user = ?1", nativeQuery = true)
    List<Integer> getByIdUser(int id);

}
