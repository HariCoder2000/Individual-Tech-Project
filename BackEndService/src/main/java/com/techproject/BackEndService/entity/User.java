package com.techproject.BackEndService.entity;

import java.net.InetAddress;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Client")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name")
	private String name;
	
    @Column(name="validIpAddress")
    private String validIpAddress;

	@Column(name = "secret_key")
	private String secret_key;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	

	public User() {
		super();
	}

	public User(int id, String name, String validIpAddress, String secret_key, String password) {
		super();
		this.id = id;
		this.name = name;
		this.validIpAddress = validIpAddress;
		this.secret_key = secret_key;
		this.password = password;
	}

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

	public String getValidIpAddress() {
		return validIpAddress;
	}

	public void setValidIpAddress(String validIpAddress) {
		this.validIpAddress = validIpAddress;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", validIpAddress=" + validIpAddress + ", secret_key=" + secret_key
				+ ", password=" + password + "]";
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
}
	
