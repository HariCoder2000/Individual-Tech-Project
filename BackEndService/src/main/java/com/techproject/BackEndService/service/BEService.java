package com.techproject.BackEndService.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.techproject.BackEndService.Repositary.BERepo;
import com.techproject.BackEndService.Repositary.FileRepositary;
import com.techproject.BackEndService.entity.Files;
import com.techproject.BackEndService.entity.User;

import reactor.core.publisher.Mono;

@Service
public class BEService {

	@Autowired
	private BERepo beRepo;
	
	@Autowired
	JavaMailSender javamailsender;
    
	@Autowired
	private FileRepositary fileRepository;

	private static final String ALGORITHM = "AES";

	public static final String cd = "Content-Disposition";
	


	public String saveUser(User u) throws Exception {

		String Secret_Key = u.getSecret_key();
		String Password = u.getPassword();
		SecretKeySpec spec = new SecretKeySpec(Secret_Key.getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, spec);
		byte[] encryptedBytes = cipher.doFinal(Password.getBytes());
		u.setPassword(Base64.getEncoder().encodeToString(encryptedBytes));
		beRepo.save(u);
		SimpleMailMessage m = new SimpleMailMessage();
		m.setTo(u.getEmail());
		m.setSubject("Account Created Successfully");
		m.setText("Hi "+u.getName()+" !!"+'\n'+"Your Account has been Created Successfully with the userId "+ u.getId()+'\n'+'\n' + "Please use this userid for future purpose");
		javamailsender.send(m);
		return "Saved";

	}

	public User findById(int id, String ipAddress) throws Exception {
		Optional<User> userDetails = beRepo.findById(id);
		System.out.println(id);
		System.out.println(ipAddress);
		if(beRepo.findByValidIpAddress(ipAddress))
		{
			System.out.println(ipAddress);
		if (userDetails.isPresent()) {
			User user = new User();
			user = userDetails.get();
			String Secret_Key = user.getSecret_key();
			String Password = user.getPassword();
			SecretKeySpec spec = new SecretKeySpec(Secret_Key.getBytes(), ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, spec);
			byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(Password));
			user.setPassword(new String(decryptedBytes));
			return user;
		} 
		else {
			return new User();
		}}
		return null;
		

	}

//	public Mono<String> uploadFile(@RequestParam("file") Files file) throws IOException {
//		  String fileName = file.getOriginalFilename().toString();
//	      String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
//		  System.out.println(fileType);
//	    if(fileType.equals("cert") || fileType.equals("tk8")|| fileType.equals("pem") || fileType.equals("txt"))
//	    {
//			Files dbFile = new Files();
//			dbFile.setId(1);
//			dbFile.setName(file.getName());
//			dbFile.setContent(file.getBytes());
//			dbFile.setContentType(file.getContentType());
//			fileRepository.save(dbFile);
//			return Mono.just("file saved");
//			}
//	    else
//	    {
//	    	return Mono.just("please make sure file is Proper extension");
//	    }
//	}
	public Mono<String> uploadFile(@RequestParam("file") Files file) throws IOException {
			fileRepository.save(file);
			return Mono.just("file saved");
	}
	public ResponseEntity<ByteArrayResource> getFileById(int id, String ipAddress) {
		Optional<Files> fileDataOptional = fileRepository.findById(id);
		if(beRepo.findByValidIpAddress(ipAddress))
		{
			System.out.println(ipAddress);
		if (fileDataOptional.isPresent()) {
			Files fileData = fileDataOptional.get();
			ByteArrayResource resource = new ByteArrayResource(fileData.getContent());
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileData.getContentType()))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getName() + "\"")
					.body(new ByteArrayResource(fileData.getContent()));
		} else {
			return ResponseEntity.notFound().build();
		}
		}
		return null;
	}

}
