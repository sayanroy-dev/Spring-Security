package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
	
	public Users addUser(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public List<Users> getUsers() {
		return userRepo.findAll();
	}
	
	public Users getUserById(int id) {
		return userRepo.findById(id).get();
	}

	public Users updateUser(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}                                             

	public String verifyUser(Users user) {
		
		// AuthenticationManager has a function authenticate,
		// which returns Authentication object and takes Authentication object as arg (unauthenticated obj)
		Authentication authentication = 
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		if(authentication.isAuthenticated())
			return jwtService.generateToken(user.getUsername());
		return "fail";
	}
}
