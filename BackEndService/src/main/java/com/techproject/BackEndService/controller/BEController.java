package com.techproject.BackEndService.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techproject.BackEndService.Repositary.BERepo;
import com.techproject.BackEndService.entity.Files;
import com.techproject.BackEndService.entity.IPChecking;
import com.techproject.BackEndService.entity.User;
import com.techproject.BackEndService.service.BEService;

import reactor.core.publisher.Mono;
@RestController
public class BEController {
	
//	@Autowired
//	private ClientService clientService;
	
	@Autowired
	private BERepo beRepo;
	
	@Autowired
	private BEService beService;
	
	
	@PostMapping("/saveUser")
	public String saveUser(@RequestBody User user) throws Exception
	{
		System.out.println(user);
		beService.saveUser(user);
		 return "Usersaved";
	}
	
	@PostMapping("/getUserById")
	public User getUserById(@RequestBody IPChecking ip) throws Exception
	{
		return beService.findById(ip.getId(),ip.getIpAddress());
	}
	
	@PostMapping("/getUserById/{id}")
	public User getUserByIdOld(@PathVariable("id") int id,String ip) throws Exception
	{
		return beService.findById(id,ip);
	}
	
	@PostMapping("/uploadFile")
    public Mono<String> uploadFile(@RequestBody Files file) throws IOException{
        try {
        	return  beService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return Mono.just("Exception");
        }
    }

	
	@PostMapping("/getFileById")
	public ResponseEntity<ByteArrayResource> getFileById(@RequestBody IPChecking ip) throws Exception
	{
		
		return beService.getFileById(ip.getId(),ip.getIpAddress());
	}
	
	@GetMapping("/getFileById/{id}")
	public String getFile(@PathVariable("id") int id)
	{
		return "hello";
	}
	
//	@Scheduled(cron = "0 * * * * ?") 
//	@PostMapping("/AdduserForEveryMinute")
//	public void AdduserForEveryMinute()
//	{
//		beRepo.save(new User(1, "Hari", "abkbasb", "1234567890123456", "password"));
//		System.out.println("Hi");
//	}
}
