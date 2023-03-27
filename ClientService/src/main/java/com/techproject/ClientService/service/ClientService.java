package com.techproject.ClientService.service;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techproject.ClientService.entity.Files;
import com.techproject.ClientService.entity.User;
import com.techproject.ClientService.response.IPChecking;

import reactor.core.publisher.Mono;

@Service
public class ClientService {

	@Autowired
	private WebClient webClient;

	@Autowired
	private ModelMapper modelMapper;

	String bodyString;

	public Mono<String> saveUser(User user, String ipAddress) throws JsonProcessingException {
		String ipadd = IPv6ToIPv4Converter(ipAddress);
		System.out.println("Ip Addresss = " + ipadd);
		user.setValidIpAddress(ipAddress);
		System.out.println(user);
		Mono<String> userResponse = webClient.post().uri("/BEService-app/saveUser").body(Mono.just(user), User.class)
				.retrieve().bodyToMono(String.class);

		return userResponse;
	}

	public Mono<User> getUserByid(IPChecking ip) {
//		User userDetails = webClient.get().uri("/BEService-app/getUserById/"+ id).retrieve().bodyToMono(User.class).block() ;
//		UserResponse UserResponse = modelMapper.map(userDetails, UserResponse.class);
//		return UserResponse;
		Mono<User> userResponse = webClient.post().uri("/BEService-app/getUserById")
				.body(Mono.just(ip), IPChecking.class).retrieve().bodyToMono(User.class);
//		int id = ip.getId();
//		String ipString = ip.getIpAddress();
//		Mono<User> userResponse = webClient.post()
//				.uri("/BEService-app/getUserById/"+ id)
//				.body(Mono.just(ipString),String.class)
//				.retrieve()
//				.bodyToMono(User.class);
		return userResponse;
	}

//	public String uploadFile(MultipartFile file) throws IOException {
//		
//		Mono<String> userResponse = webClient.post()
//				.uri("/BEService-app/uploadFile").contentType(MediaType.MULTIPART_FORM_DATA)
//				.body(BodyInserters.fromMultipartData("file", Files.class))
//				.retrieve()
//				.bodyToMono(String.class);
//				userResponse.subscribe(responseBody -> {
//					bodyString = responseBody; 
//				});
//		return "saved";
//		
//	}
	public String uploadFile(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename().toString();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		System.out.println(fileType);
	    if(fileType.equals("cert") || fileType.equals("tk8")|| fileType.equals("pem") || fileType.equals("txt"))
	    {
		Files dbFile = new Files();
		dbFile.setId(1);
		dbFile.setName(file.getName());
		dbFile.setContent(file.getBytes());
		dbFile.setContentType(file.getContentType());
		Mono<String> userResponse = webClient.post().uri("/BEService-app/uploadFile").body(Mono.just(dbFile), Files.class)
				.retrieve().bodyToMono(String.class);
		userResponse.subscribe(responseBody -> {
			bodyString = responseBody;
		});
		return "saved";
	    }
		return fileType;

	}

	public Mono<ResponseEntity<ByteArrayResource>> getFileById(IPChecking ip) {
		Mono<ResponseEntity<ByteArrayResource>> userResponse = webClient.post().uri("/BEService-app/getFileById")
				.body(Mono.just(ip), IPChecking.class).retrieve().toEntity(ByteArrayResource.class);
		return userResponse;

	}

	public String IPv6ToIPv4Converter(String Ip) {
		try {
			InetAddress ipv6Address = InetAddress.getByName(Ip);
			if (ipv6Address instanceof Inet6Address) {
				byte[] ipv6Bytes = ipv6Address.getAddress();
				byte[] ipv4Bytes = Arrays.copyOfRange(ipv6Bytes, 12, 16); // extract last 4 bytes
				InetAddress ipv4Address = InetAddress.getByAddress(ipv4Bytes);
				String ipv4String = ipv4Address.getHostAddress();
				System.out.println(ipv4String);
				return ipv4String;
			} else {
				System.err.println("Not a loopback IPv6 address.");
				return null;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Ip;
	}
}
