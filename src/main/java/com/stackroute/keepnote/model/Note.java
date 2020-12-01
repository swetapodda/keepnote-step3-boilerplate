package com.stackroute.keepnote.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*
 * The class "Note" will be acting as the data model for the Note Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
@Table(name = "Note")
public class Note {
	/*
	 * This class should have eight fields
	 * (noteId,noteTitle,noteContent,noteStatus,createdAt,
	 * category,reminder,createdBy). Out of these eight fields, the field noteId
	 * should be primary key and auto-generated. This class should also contain the
	 * getters and setters for the fields along with the no-arg , parameterized
	 * constructor and toString method. The value of createdAt should not be
	 * accepted from the user but should be always initialized with the system date.
	 * annotate category and reminder field with @ManyToOne.
	 */
	@Id()
	@Column(name = "note_id")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int noteId;

	@Column(name = "note_title")
	private String noteTitle;

	@Column(name = "note_content")
	private String noteContent;

	@Column(name = "note_status")
	private String noteStatus;

	@Column(name = "note_creation_date")
	private Date noteCreatedAt;

	@ManyToOne(optional = true)
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne(optional = true)
	@JoinColumn(name = "reminder_id")
	private Reminder reminder;

	@Column(name = "note_created_by")
	private String createdBy;
	public Note() {

	}

	public Note(int Int, String string, String string1, String string2, Date date, Category category, Reminder reminder,
			String string3) {

		this.noteId = Int;
		this.noteTitle = string;
		this.noteContent = string1;
		this.noteStatus = string2;
		this.noteCreatedAt = date;
		this.category = category;
		this.reminder = reminder;
		this.createdBy = string3;

	}

	
	public int getNoteId() {
		return this.noteId;
	}

	public void setNoteId(int Int) {
		this.noteId = Int;
	}

	public String getNoteTitle() {
		return this.noteTitle;
	}

	public void setNoteTitle(String string) {
		this.noteTitle = string;
	}

	public String getNoteContent() {
		return this.noteContent;
	}

	public void setNoteContent(String string) {
		this.noteContent = string;
	}

	public void setNoteStatus(String string) {
		this.noteStatus = string;
	}

	public void setNoteCreatedAt(Date date) {
		this.noteCreatedAt = date;
	}

	public void setCreatedBy(String string) {
		this.createdBy = string;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	 public String getNoteStatus() {
		return noteStatus;
	}

	public Date getNoteCreatedAt() {
		return noteCreatedAt;
	}

	public Category getCategory() {
		return category;
	}

	public Reminder getReminder() {
		return reminder;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	 public String toString() {
	 	return String.format(
	 			"Note [noteId=%s, noteTitle=%s, noteContent=%s, noteStatus=%s, noteCreatedAt=%s, category=%s, reminder=%s, createdBy=%s]",
	 			noteId, noteTitle, noteContent, noteStatus, noteCreatedAt, category, reminder, createdBy);
	 }

}
