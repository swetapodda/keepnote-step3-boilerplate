package com.stackroute.keepnote.controller;

import java.util.List;

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

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class NoteController {

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	@Autowired
	NoteService noteService;

	private static final String SESSION_ID = "loggedInUserId";

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in a Note table
	 * in the database.Handle ReminderNotFoundException and
	 * CategoryNotFoundException as well. please note that the loggedIn userID
	 * should be taken as the createdBy for the note.This handler method should
	 * return any one of the status messages basis on different situations: 1.
	 * 201(CREATED) - If the note created successfully. 2. 409(CONFLICT) - If the
	 * noteId conflicts with any existing user3. 401(UNAUTHORIZED) - If the user
	 * trying to perform the action has not logged in.
	 * 
	 * This handler method should map to the URL "/note" using HTTP POST method
	 */

	@PostMapping("/note")
	public ResponseEntity<?> createNote(@RequestBody Note note, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();

		if (null != session && null != session.getAttribute(SESSION_ID)) {
			String userId = session.getAttribute(SESSION_ID).toString();
			note.setCreatedBy(userId);
			try {
				if (this.noteService.createNote(note)) {

					return new ResponseEntity<String>("Note created Successfully", headers, HttpStatus.CREATED);

				} else {
					return new ResponseEntity<String>("Note created Successfully", headers, HttpStatus.CONFLICT);
				}
			} catch (ReminderNotFoundException | CategoryNotFoundException e) {
				return new ResponseEntity<String>("Note created Successfully", headers, HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<String>("User is Unauthorized", headers, HttpStatus.UNAUTHORIZED);
		}

	}

	/*
	 * Define a handler method which will delete a note from a database handle
	 * NoteNotFoundException as well. This handler method should return any one of
	 * the status messages basis on different situations: 1. 200(OK) - If the note
	 * deleted successfully from database. 2. 404(NOT FOUND) - If the note with
	 * specified noteId is not found. 3. 401(UNAUTHORIZED) - If the user trying to
	 * perform the action has not logged in.
	 * 
	 * This handler method should map to the URL "/note/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid noteId without {}
	 */

	@DeleteMapping("/note/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable int id, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		System.out.println(session);
		if (null != session && null != session.getAttribute(SESSION_ID)) {
			try {
				if (this.noteService.deleteNote(id)) {
					return new ResponseEntity<String>("Note deleted Successfully", headers, HttpStatus.OK);
				}
				return new ResponseEntity<String>("Note Not Found", headers, HttpStatus.NOT_FOUND);
			} catch (NoteNotFoundException e) {
				return new ResponseEntity<String>("Note Not Found", headers, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("User is Unauthorized", headers, HttpStatus.UNAUTHORIZED);
		}
	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * note table in database handle ReminderNotFoundException,
	 * NoteNotFoundException, CategoryNotFoundException as well. please note that
	 * the loggedIn userID should be taken as the createdBy for the note. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note updated successfully. 2.
	 * 404(NOT FOUND) - If the note with specified noteId is not found. 3.
	 * 401(UNAUTHORIZED) - If the user trying to perform the action has not logged
	 * in.
	 * 
	 * This handler method should map to the URL "/note/{id}" using HTTP PUT method.
	 */

	@PutMapping("/note/{id}")
	public ResponseEntity<?> updateNote(@RequestBody Note note, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		System.out.println(session);
		if (null != session && null != session.getAttribute(SESSION_ID)) {
			try {
				if (null != this.noteService.updateNote(note, note.getNoteId())) {
					return new ResponseEntity<String>("Note deleted Successfully", headers, HttpStatus.OK);
				}
				return new ResponseEntity<String>("Note Not Found", headers, HttpStatus.NOT_FOUND);
			} catch (ReminderNotFoundException e) {
				return new ResponseEntity<String>("Reminder Not Found", headers, HttpStatus.NOT_FOUND);
			} catch (CategoryNotFoundException e) {
				return new ResponseEntity<String>("category Not Found", headers, HttpStatus.NOT_FOUND);
			} catch (NoteNotFoundException e) {
				return new ResponseEntity<String>("Note Not Found", headers, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<String>("User is Unauthorized", headers, HttpStatus.UNAUTHORIZED);
		}
	}

	/*
	 * Define a handler method which will get us the notes by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully. 2.
	 * 401(UNAUTHORIZED) -If the user trying to perform the action has not logged
	 * in.
	 * 
	 * 
	 * This handler method should map to the URL "/note" using HTTP GET method
	 */
	@GetMapping("/note")
	public ResponseEntity<?> getCategory(HttpSession session) {
		HttpHeaders headers = new HttpHeaders();

		if (null != session && null != session.getAttribute(SESSION_ID)) {
			String userId = session.getAttribute(SESSION_ID).toString();
			List<Note> noteList = this.noteService.getAllNotesByUserId(userId);
			return new ResponseEntity<List<Note>>(noteList, headers, HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("User is Unauthorized", headers, HttpStatus.UNAUTHORIZED);
		}

	}

}
