package com.techproject.ClientService.response;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;

@Component
public class UserResponse {
	private int id;
	

	private String name;
	

	private String password;
	

	private String secret_key;
	
    public String getValidIpAddress() {
		return validIpAddress;
	}


	public void setValidIpAddress(String validIpAddress) {
		this.validIpAddress = validIpAddress;
	}


	private String validIpAddress;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getSecret_key() {
		return secret_key;
	}


	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	
	

}
