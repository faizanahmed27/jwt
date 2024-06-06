package com.jwt.example.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.example.dto.AuthenticationResponse;
import com.jwt.example.dto.UserDTO;
import com.jwt.example.entities.User;
import com.jwt.example.enums.Role;
import com.jwt.example.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public UserDTO userRegister(UserDTO userRequest) {
		
		User user = new User();
		BeanUtils.copyProperties(userRequest, user);
		user.setPassowrd(passwordEncoder.encode(userRequest.getPassowrd()));
		user.setRole(Role.ADMIN);
		
		user = userRepository.save(user);
		if (null != user) {

			//String token = jwtService.generateToken(user);

			userRequest.setMessage("User saved successfully!");

			return userRequest;
		}

		return null;
	}
	
	public AuthenticationResponse authentication(UserDTO userRequest) {
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassowrd()));
		
		User user = userRepository.findByUserName(userRequest.getUserName()).orElseThrow();
		String token = jwtService.generateToken(user);
		
		return new AuthenticationResponse(token);
	}
	
	public String checkUser(Long id) {
		
		User user = userRepository.findById(id).orElseThrow();
		if(null != user) {
			
			return "User is Exist";
		}else {
			
			return "User does not Exist";
		}
	}
}
