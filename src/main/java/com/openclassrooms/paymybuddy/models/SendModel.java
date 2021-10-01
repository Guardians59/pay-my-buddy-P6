package com.openclassrooms.paymybuddy.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "send")
public class SendModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "id_author")
    private int idAuthor;
    
    @Column(name = "id_friend")
    private int idRecipient;
    
    @Column(name = "date")
    private Date date;
    
    @Column(name = "amount_send")
    private double amountSend;
    
    @Column(name = "amount_sampling")
    private double amountSampling;
    
    @Column(name = "description")
    private String description;

    public SendModel() {
	super();
    }

    public SendModel(int id, int idAuthor, int idRecipient, Date date, double amountSend, double amountSampling,
	    String description) {
	super();
	this.id = id;
	this.idAuthor = idAuthor;
	this.idRecipient = idRecipient;
	this.date = date;
	this.amountSend = amountSend;
	this.amountSampling = amountSampling;
	this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public int getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(int idRecipient) {
        this.idRecipient = idRecipient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmountSend() {
        return amountSend;
    }

    public void setAmountSend(double amountSend) {
        this.amountSend = amountSend;
    }

    public double getAmountSampling() {
        return amountSampling;
    }

    public void setAmountSampling(double amountSampling) {
        this.amountSampling = amountSampling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(amountSampling);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(amountSend);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((date == null) ? 0 : date.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + id;
	result = prime * result + idAuthor;
	result = prime * result + idRecipient;
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
	SendModel other = (SendModel) obj;
	if (Double.doubleToLongBits(amountSampling) != Double.doubleToLongBits(other.amountSampling))
	    return false;
	if (Double.doubleToLongBits(amountSend) != Double.doubleToLongBits(other.amountSend))
	    return false;
	if (date == null) {
	    if (other.date != null)
		return false;
	} else if (!date.equals(other.date))
	    return false;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (id != other.id)
	    return false;
	if (idAuthor != other.idAuthor)
	    return false;
	if (idRecipient != other.idRecipient)
	    return false;
	return true;
    }

   
}
