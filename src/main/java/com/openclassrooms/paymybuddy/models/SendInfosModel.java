package com.openclassrooms.paymybuddy.models;

public class SendInfosModel {
    
    private int idRecipient;
    
    private String description;
    
    private double sendAmount;

    public SendInfosModel() {
    }

    public SendInfosModel(int idRecipient, String description, double sendAmount) {
	this.idRecipient = idRecipient;
	this.description = description;
	this.sendAmount = sendAmount;
    }

    public int getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(int idRecipient) {
        this.idRecipient = idRecipient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(double sendAmount) {
        this.sendAmount = sendAmount;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + idRecipient;
	long temp;
	temp = Double.doubleToLongBits(sendAmount);
	result = prime * result + (int) (temp ^ (temp >>> 32));
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
	SendInfosModel other = (SendInfosModel) obj;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (idRecipient != other.idRecipient)
	    return false;
	if (Double.doubleToLongBits(sendAmount) != Double.doubleToLongBits(other.sendAmount))
	    return false;
	return true;
    }

}
