package com.example.userdetailservice.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userdetailservice.exception.UserAlreadyExistException;
import com.example.userdetailservice.exception.UserNotFoundException;
import com.example.userdetailservice.jsonview.UserView;
import com.example.userdetailservice.model.User;
import com.example.userdetailservice.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/user-detail")
public class UserDetailRestController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path = "/{id}")
	@JsonView(value = UserView.Public.class)
	public ResponseEntity<User> getUser(@PathVariable Long id){
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("User does not exists for id: "+id);
		} else {
			//User result = user.get();
			//result.getAddress();
			return ResponseEntity.ok(user.get());
		}
	}
	
	@PostMapping(path = "/{id}", consumes = "application/json")
	@JsonView(value = UserView.Public.class)
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long id){
		Optional<User> savedUser = userRepository.findById(id);
		if(!savedUser.isPresent()) {
			throw new UserNotFoundException("User does not exists for id: "+id);
		} else {
			user.setId(id);
			user.getAddress().setId(id);
			return ResponseEntity.ok(userRepository.save(user));
		}
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user){
		Optional<User> findUser = userRepository.findById(user.getId());
		if(findUser.isPresent()) {
			throw new UserAlreadyExistException("User with id " + user.getId()  + " already exists");
		}
		return ResponseEntity.ok(userRepository.save(user));

	}
}

