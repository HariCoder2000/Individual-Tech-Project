package com.techproject.ClientService.controller;

import java.io.IOException;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techproject.ClientService.entity.Files;
import com.techproject.ClientService.entity.User;
import com.techproject.ClientService.response.IPChecking;
import com.techproject.ClientService.response.UserResponse;
import com.techproject.ClientService.service.ClientService;

import jakarta.servlet.http.HttpServletRequest;

@RestController

public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private IPChecking ipChecking;
	
	
	
	@PostMapping("/saveUser")
	public Mono<String> saveUser(@RequestBody User user,HttpServletRequest request) throws JsonProcessingException
	{
		String ipAddress = request.getRemoteAddr();
		System.out.println(ipAddress);
		return clientService.saveUser(user,ipAddress);
	}
	
	@GetMapping("/GetResponseByid/{id}")
	public Mono<User> findByid(@PathVariable("id") int id,HttpServletRequest request)
	{
		String ipAddress = request.getRemoteAddr();
		IPChecking ip = new IPChecking();
		ip.setId(id);
		ip.setIpAddress(ipAddress);
		return clientService.getUserByid(ip);
	}
	
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
        	
        	clientService.uploadFile(file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@GetMapping("/getFileById/{id}")
	public Mono<ResponseEntity<ByteArrayResource>> getFileById(@PathVariable("id") int id,HttpServletRequest request) throws Exception
	{
		String ipAddress = request.getRemoteAddr();
		IPChecking ip = new IPChecking();
		ip.setId(id);
		ip.setIpAddress(ipAddress);
		return clientService.getFileById(ip);
	}
	
	@GetMapping("/getIpAddress")
	public String getIpAddress(HttpServletRequest request)
	{
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        // Use the IP address as needed
        return  ipAddress;
    }
	
		
	}
	


