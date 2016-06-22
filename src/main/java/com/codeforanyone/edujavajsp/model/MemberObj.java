package com.codeforanyone.edujavajsp.model;

public class MemberObj {
	Integer id;
	Integer userId;
	String role;
	Integer petitionId;

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getRole() {
		return role;
	}

	public Integer getPetitionId() {
		return petitionId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPetitionId(Integer petitionId) {
		this.petitionId = petitionId;
	}

}
