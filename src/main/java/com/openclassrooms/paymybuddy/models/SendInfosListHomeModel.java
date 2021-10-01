package com.openclassrooms.paymybuddy.models;

public class SendInfosListHomeModel {
    
    private String firstName;
    
    private String description;
    
    private double send;

    public SendInfosListHomeModel() {
    }

    public SendInfosListHomeModel(String firstName, String description, double send) {
	this.firstName = firstName;
	this.description = description;
	this.send = send;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSend() {
        return send;
    }

    public void setSend(double send) {
        this.send = send;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	long temp;
	temp = Double.doubleToLongBits(send);
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
	SendInfosListHomeModel other = (SendInfosListHomeModel) obj;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (Double.doubleToLongBits(send) != Double.doubleToLongBits(other.send))
	    return false;
	return true;
    }
    
    

}
