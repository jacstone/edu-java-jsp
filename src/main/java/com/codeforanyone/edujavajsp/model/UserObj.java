package com.codeforanyone.edujavajsp.model;

public class UserObj {
	Integer id;
	String userName;
	String pw;
	String firstName;
	String lastName;
	String phone;
	String email;
	boolean isPhonePrivate;
	boolean isEmailPrivate;

	public UserObj() {

	}

	public String toString() {
		return "User " + id + ":" + userName + " : " + firstName + " " + lastName + " : "
				+ (isPhonePrivate ? "HIDDEN" : phone) + " : " + (isEmailPrivate ? "HIDDEN" : email);
	}

	public Integer getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getPW() {
		return pw;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public boolean getIsPhonePrivate() {
		return isPhonePrivate;
	}

	public boolean getIsEmailPrivate() {
		return isEmailPrivate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIsEmailPrivate(boolean isEmailPrivate) {
		this.isEmailPrivate = isEmailPrivate;

	}

	public void setIsPhonePrivate(boolean isPhonePrivate) {
		this.isPhonePrivate = isPhonePrivate;
	}

	public void setPW(String pw) {
		this.pw = pw;
	}

}
