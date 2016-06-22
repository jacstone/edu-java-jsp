package com.codeforanyone.edujavajsp.model;

public class RoleObj {
	Integer id;
	String name;
	Boolean isPublic;
	Integer petitionId;
	Integer creator;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public Integer getPetitionId() {
		return petitionId;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public void setPetitionId(Integer petitionId) {
		this.petitionId = petitionId;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

}
