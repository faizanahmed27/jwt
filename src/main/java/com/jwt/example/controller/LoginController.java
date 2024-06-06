package com.jwt.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.example.dto.AuthenticationResponse;
import com.jwt.example.dto.UserDTO;
import com.jwt.example.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/userRegister")
	public ResponseEntity<?> userRegister(@RequestBody UserDTO userDTO){
		
		UserDTO userRegister = userService.userRegister(userDTO);
		
		return ResponseEntity.ok(userRegister);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO){
		
		AuthenticationResponse authentication = userService.authentication(userDTO);
		return ResponseEntity.ok(authentication);
	}
	
}
