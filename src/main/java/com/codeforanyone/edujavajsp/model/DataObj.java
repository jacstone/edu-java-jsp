package com.codeforanyone.edujavajsp.model;

import java.sql.Date;
import java.sql.Time;

public class DataObj {
	Integer id;
	Integer petitionId;
	Integer userId;
	Date date;
	Time startTime;
	Time stopTime;
	Integer signatures;
	String address;
	String city;
	String state;
	String zip;

	public Integer getId() {
		return id;
	}

	public Integer getPetitionId() {
		return petitionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Date getDate() {
		return date;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getStopTime() {
		return stopTime;
	}

	public Integer getSignatures() {
		return signatures;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPetitionId(Integer petitionId) {
		this.petitionId = petitionId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setStartTime(Time startTime) {
this.startTime=startTime;		
	}

	public void setStopTime(Time stopTime) {
this.stopTime=stopTime;		
	}

	public void setSignatures(Integer signatures) {
this.signatures=signatures;		
	}

	public void setAddress(String address) {
this.address=address;		
	}

	public void setCity(String city) {
this.city=city;		
	}

	public void setState(String state) {
this.state=state;		
	}

	public void setZip(String zip) {
this.zip=zip;		
	}

}
