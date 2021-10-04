package com.openclassrooms.paymybuddy.models;

public class TransferMoneyModel {
    
private String ibanAccount;
    
    private String firstNameIbanAccount;
    
    private String lastNameIbanAccount;
    
    private double amountTransfer;

    public TransferMoneyModel() {
	super();
    }

    public TransferMoneyModel(String ibanAccount, String firstNameIbanAccount, String lastNameIbanAccount,
	    double amountTransfer) {
	super();
	this.ibanAccount = ibanAccount;
	this.firstNameIbanAccount = firstNameIbanAccount;
	this.lastNameIbanAccount = lastNameIbanAccount;
	this.amountTransfer = amountTransfer;
    }

    public String getIbanAccount() {
        return ibanAccount;
    }

    public void setIbanAccount(String ibanAccount) {
        this.ibanAccount = ibanAccount;
    }

    public String getFirstNameIbanAccount() {
        return firstNameIbanAccount;
    }

    public void setFirstNameIbanAccount(String firstNameIbanAccount) {
        this.firstNameIbanAccount = firstNameIbanAccount;
    }

    public String getLastNameIbanAccount() {
        return lastNameIbanAccount;
    }

    public void setLastNameIbanAccount(String lastNameIbanAccount) {
        this.lastNameIbanAccount = lastNameIbanAccount;
    }

    public double getAmountTransfer() {
        return amountTransfer;
    }

    public void setAmountTransfer(double amountTransfer) {
        this.amountTransfer = amountTransfer;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(amountTransfer);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	result = prime * result + ((firstNameIbanAccount == null) ? 0 : firstNameIbanAccount.hashCode());
	result = prime * result + ((ibanAccount == null) ? 0 : ibanAccount.hashCode());
	result = prime * result + ((lastNameIbanAccount == null) ? 0 : lastNameIbanAccount.hashCode());
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
	TransferMoneyModel other = (TransferMoneyModel) obj;
	if (Double.doubleToLongBits(amountTransfer) != Double.doubleToLongBits(other.amountTransfer))
	    return false;
	if (firstNameIbanAccount == null) {
	    if (other.firstNameIbanAccount != null)
		return false;
	} else if (!firstNameIbanAccount.equals(other.firstNameIbanAccount))
	    return false;
	if (ibanAccount == null) {
	    if (other.ibanAccount != null)
		return false;
	} else if (!ibanAccount.equals(other.ibanAccount))
	    return false;
	if (lastNameIbanAccount == null) {
	    if (other.lastNameIbanAccount != null)
		return false;
	} else if (!lastNameIbanAccount.equals(other.lastNameIbanAccount))
	    return false;
	return true;
    }

}
