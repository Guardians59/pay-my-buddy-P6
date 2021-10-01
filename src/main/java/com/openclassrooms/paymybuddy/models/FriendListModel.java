package com.openclassrooms.paymybuddy.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(EmailPKFriendList.class)
@Table(name = "friend_list")
public class FriendListModel {
    
    @Id
    @Column(name = "id_user")
    private int idUser;
    
    @Id
    @Column(name = "id_friend")
    private int idFriend;

    public FriendListModel() {
	
    }

    public FriendListModel(int idUser, int idFriend) {
	
	this.idUser = idUser;
	this.idFriend = idFriend;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(int idFriend) {
        this.idFriend = idFriend;
    }

    @Override
    public String toString() {
	return "FriendListModel [idUser=" + idUser + ", idFriend=" + idFriend + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + idFriend;
	result = prime * result + idUser;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FriendListModel other = (FriendListModel) obj;
	if (idFriend != other.idFriend)
	    return false;
	if (idUser != other.idUser)
	    return false;
	return true;
    }
    
    

}
