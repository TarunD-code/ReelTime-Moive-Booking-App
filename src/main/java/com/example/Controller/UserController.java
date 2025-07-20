package com.example.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.users;
import com.example.repo.UserInterFace;

@RestController("/users")
public class UserController {
	@Autowired
	UserInterFace Service;
	
	//create
	@PostMapping("/RegisterUser")
	public void RegisterUser(@RequestBody users user) {
		Service.save(user);	
		System.out.println("User saved"+user);
	}
	
	//select
	@GetMapping("/allUsers")
	public List<users> SelectUsers() {	
		List<users> allUsers=Service.findAll();
		return allUsers;	
	}
	
	//Delete By Id
	@DeleteMapping("/DeleteUsers/{id}")
	public void DeleteUser(@PathVariable int id) {
			users user=Service.findById(id).get();
			Service.delete(user);		
	}
	
	//update 
	@PutMapping("/UpdateUser/{id}")
	public users updateUser(@PathVariable int id) {
		users user=Service.findById(id).get();
		//You Can update the user for this plass
		user.setName("rajini");
		user.setEmail("rajini@Gmail.com");
		user.setPassword("000");
		Service.save(user);
		return user;
		
	}
	
	//find by id
	@GetMapping("/FindById/{id}")
	public users FindByUserID(@PathVariable int id) {
		users user=Service.findById(id).get();
		return user;		
	}
	
	
	
	
	
	

}
