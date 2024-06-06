package com.jwt.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.example.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/checkUser")
	public ResponseEntity<?> checkUser(@RequestParam Long id){
		
		String existUser = userService.checkUser(id);
		
		return ResponseEntity.ok(existUser);
	}
}
