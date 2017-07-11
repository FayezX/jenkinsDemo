package com.gcit.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Borrower implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2394027389335083488L;
	private Integer CardNo;
	private String Name;
	private String Address;
	private String phone;
	/**
	 * @return the cardNo
	 */
	public Integer getCardNo() {
		return CardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(Integer cardNo) {
		CardNo = cardNo;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return Address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		Address = address;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CardNo == null) ? 0 : CardNo.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrower other = (Borrower) obj;
		if (CardNo == null) {
			if (other.CardNo != null)
				return false;
		} else if (!CardNo.equals(other.CardNo))
			return false;
		return true;
	}
	
}
