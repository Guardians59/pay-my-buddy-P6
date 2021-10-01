package com.openclassrooms.paymybuddy.models;

import java.io.Serializable;

import javax.persistence.Column;

public class EmailPKFriendList implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3112135045427943756L;
    
    @Column(name = "id_user")
    private int idUser;
    
    @Column(name = "id_friend")
    private int idFriend;

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
	EmailPKFriendList other = (EmailPKFriendList) obj;
	if (idFriend != other.idFriend)
	    return false;
	if (idUser != other.idUser)
	    return false;
	return true;
    }
    
    

}
