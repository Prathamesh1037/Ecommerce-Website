package com.example.Ecomm.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.example.Ecomm.Repository.UserRepository;

import jakarta.persistence.Entity;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	public com.example.Ecomm.Entity.User registeruser (com.example.Ecomm.Entity.User user) {
		com.example.Ecomm.Entity.User newUser = userRepository.save(user);
		System.out.println("User added...");
		return newUser;
	}
	
	public com.example.Ecomm.Entity.User loginUser(String email,String password)
	{
		com.example.Ecomm.Entity.User user = userRepository.findByEmail(email);
		if(user != null && user.getPassword().equals(password)) {
			return user;
		}
		return null;
	}
	
	public List<com.example.Ecomm.Entity.User> getAllUsers(){
       return userRepository.findAll();
}

	
}
