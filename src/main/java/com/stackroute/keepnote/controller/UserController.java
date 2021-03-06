package com.stackroute.keepnote.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserService;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class UserController {

	/*
	 * Autowiring should be implemented for the UserService. (Use Constructor-based
	 * autowiring) Please note that we should not create an object using the new
	 * keyword
	 */
	// @Autowired
	UserService userService;

	private static String SESSION_ID = "loggedInUserId";

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in a User table
	 * in the database. This handler method should return any one of the status
	 * messages basis on different situations: 1. 201(CREATED) - If the user created
	 * successfully. 2. 409(CONFLICT) - If the userId conflicts with any existing
	 * user
	 * 
	 * Note: ------ This method can be called without being logged in as well as
	 * when a new user will use the app, he will register himself first before
	 * login.
	 * 
	 * This handler method should map to the URL "/user/register" using HTTP POST
	 * method
	 */

	@PostMapping("/user/register")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();
		try {
			userService.registerUser(user);
			return new ResponseEntity<String>("User created successfully", headers, HttpStatus.CREATED);
		} catch (UserAlreadyExistException e) {
			return new ResponseEntity<String>("Unable to create user", headers, HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will update a specific user by reading the
	 * Serialized object from request body and save the updated user details in a
	 * user table in database handle exception as well. This handler method should
	 * return any one of the status messages basis on different situations: 1.
	 * 200(OK) - If the user updated successfully. 2. 404(NOT FOUND) - If the user
	 * with specified userId is not found. 3. 401(UNAUTHORIZED) - If the user trying
	 * to perform the action has not logged in.
	 * 
	 * This handler method should map to the URL "/user/{id}" using HTTP PUT method.
	 */
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(HttpSession session, @RequestBody User user) {
		HttpHeaders headers = new HttpHeaders();

		if (null != session && null != session.getAttribute(SESSION_ID)) {
			try {
				if (null != userService.updateUser(user, user.getUserId())) {
					return new ResponseEntity<String>("User Updated", headers, HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("Unable to update user", headers, HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				return new ResponseEntity<String>("Unable to update user", headers, HttpStatus.NOT_FOUND);
			}

		} else {
			return new ResponseEntity<String>("Unauthorized access", headers, HttpStatus.UNAUTHORIZED);
		}

	}

	/*
	 * Define a handler method which will delete a user from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the user deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the user with specified userId is not found.
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/user/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid userId without {}
	 */

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<String> deleteNote(HttpSession session, @PathVariable String id) {
		HttpHeaders headers = new HttpHeaders();
		if (null == session.getAttribute(SESSION_ID)) {
			return new ResponseEntity<String>("Unauthorized access", headers, HttpStatus.UNAUTHORIZED);
		}
		try {
			if (userService.deleteUser(id)) {
				return new ResponseEntity<String>("User deleted successfully", headers, HttpStatus.OK);
			}
			return new ResponseEntity<String>("User not found", headers, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<String>("User not found", headers, HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will show details of a specific user handle
	 * UserNotFoundException as well. This handler method should return any one of
	 * the status messages basis on different situations: 1. 200(OK) - If the user
	 * found successfully. 2. 401(UNAUTHORIZED) - If the user trying to perform the
	 * action has not logged in. 3. 404(NOT FOUND) - If the user with specified
	 * userId is not found. This handler method should map to the URL "/user/{id}"
	 * using HTTP GET method where "id" should be replaced by a valid userId without
	 * {}
	 */
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<User> getReminder(HttpSession session, @PathVariable String id) {
		HttpHeaders headers = new HttpHeaders();
		if (null == session.getAttribute(SESSION_ID)) {
			return new ResponseEntity<User>(null, headers, HttpStatus.UNAUTHORIZED);
		}
		try {
			User user = userService.getUserById(id);
			if (null != user) {
				return new ResponseEntity<User>(user, headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<User>(null, headers, HttpStatus.NOT_FOUND);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<User>(null, headers, HttpStatus.NOT_FOUND);
		}
	}
}