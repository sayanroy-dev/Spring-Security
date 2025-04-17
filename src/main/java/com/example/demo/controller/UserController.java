package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public Users addUser(@RequestBody Users user)
	{
		return userService.addUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
		return userService.verifyUser(user);
	}
	
	@GetMapping("/users")
	public List<Users> getUsers()
	{
		return userService.getUsers();
	}
	@GetMapping("/user/{id}")
	public Users getUserById(@PathVariable int id)
	{
		return userService.getUserById(id);
	}
	@PutMapping("/user/{id}")
	public Users updateUser(@RequestBody Users user, @PathVariable int id)
	{
		return userService.updateUser(user);
	}
}
