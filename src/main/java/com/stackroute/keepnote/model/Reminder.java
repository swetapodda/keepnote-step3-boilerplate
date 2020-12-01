package com.stackroute.keepnote.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
/*
 * The class "Reminder" will be acting as the data model for the Reminder Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
@Table(name="Reminder")
public class Reminder {
	/*
	 * This class should have seven fields
	 * (reminderId,reminderName,reminderDescription,reminderType,
	 * reminderCreatedBy,reminderCreationDate,notes). Out of these seven fields, the
	 * field reminderId should be primary key and auto-generated. This class should
	 * also contain the getters and setters for the fields along with the no-arg ,
	 * parameterized constructor and toString method. The value of
	 * reminderCreationDate should not be accepted from the user but should be
	 * always initialized with the system date. annotate notes field with @OneToMany
	 * and @JsonIgnore
	 */

	@Id()
	@Column(name = "reminder_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reminderId;

	@Column(name = "reminder_name")
	private String reminderName;

	@Column(name = "reminder_type")
	private String reminderType;

	@Column(name = "reminder_description")
	private String reminderDescription;

	@Column(name = "reminder_created_by")
	private String reminderCreatedBy;
	
	@JsonIgnore
	@OneToMany
	private List<Note> notes;

	@Column(name = "reminder_creation_date")
	private Date reminderCreationDate;

	public Reminder() {

	}

	public Reminder(int Int, String string, String string1, String string2, String string3, List<Note> list,
			Date date) {
		this.reminderId = Int;
		this.reminderName = string;
		this.reminderType = string1;
		this.reminderDescription = string2;
		this.reminderCreatedBy = string3;
		this.notes = list;
		this.reminderCreationDate = date;
	}

	public int getReminderId() {
		return this.reminderId;

	}

	public void setReminderId(int Int) {
		this.reminderId = Int;
	}

	public void setReminderName(String string) {
		this.reminderName = string;
	}

	public String getReminderDescription() {
		return reminderDescription;
	}

	public void setReminderDescription(String string) {
		this.reminderDescription = string;
	}

	public void setReminderType(String string) {
		this.reminderType = string;
	}

	public void setReminderCreationDate(Date date) {
		this.reminderCreationDate = date;
	}

	public void setReminderCreatedBy(String string) {
		this.reminderCreatedBy = string;
	}

	public void setNotes(List<Note> list) {
		this.notes = list;
	}

	public String getReminderName() {
		return reminderName;
	}

	public String getReminderType() {
		return reminderType;
	}

	public String getReminderCreatedBy() {
		return reminderCreatedBy;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public Date getReminderCreationDate() {
		return reminderCreationDate;
	}

	@Override
	public String toString() {
		return String.format(
				"Reminder [reminderId=%s, reminderName=%s, reminderType=%s, reminderDescription=%s, reminderCreatedBy=%s, notes=%s, reminderCreationDate=%s]",
				reminderId, reminderName, reminderType, reminderDescription, reminderCreatedBy, notes,
				reminderCreationDate);
	}

}
