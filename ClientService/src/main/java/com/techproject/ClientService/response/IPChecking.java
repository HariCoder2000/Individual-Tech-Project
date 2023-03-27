package com.techproject.ClientService.response;

import org.springframework.stereotype.Component;

@Component
public class IPChecking {

	private String ipAddress;
	 private int id;
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	 
}
