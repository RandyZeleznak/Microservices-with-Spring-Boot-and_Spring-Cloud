package com.zsquared.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	// Retrieve all users
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	// Retrieve user with id
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		User user = service.findUser(id);
		if(user == null)
			throw new UserNotFoundException("id-"+id);
		
		//"all-users", SERVER_PATH + "/users"
		//retrieveALLUSERS
		EntityModel<User> resource = EntityModel.of(user);
		
		WebMvcLinkBuilder linkTo = 
				linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		

		
		
		
		return resource;
	}
	
	private UserResource methodOn(Class<? extends UserResource> class1) {
		// TODO Auto-generated method stub
		return null;
	}

		// Retrieve user with id
		@DeleteMapping("/users/{id}")
		public void deleteUser(@PathVariable int id) {
			User user = service.deleteById(id);
			if(user == null)
				throw new UserNotFoundException("id-"+id);
			
		}
	
	
	
	//Create user
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/id")
				.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	
	}

}