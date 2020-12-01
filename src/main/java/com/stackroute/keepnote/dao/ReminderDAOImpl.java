package com.stackroute.keepnote.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class ReminderDAOImpl implements ReminderDAO {
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	@Autowired
	SessionFactory sessionFactory;
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) {
		this.sessionFactory.getCurrentSession().save(reminder);
		return true;
	}
	
	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(reminder);
		return true;

	}

	/*
	 * Remove an existing reminder
	 */
	
	public boolean deleteReminder(int reminderId) {
		try {
			Reminder reminder = this.getReminderById(reminderId);
			if (null != reminder) {
				this.sessionFactory.getCurrentSession().delete(reminder);
				return true;
			}
		} catch (ReminderNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

	/*
	 * Retrieve details of a specific reminder
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Reminder reminder = this.sessionFactory.getCurrentSession().get(Reminder.class, reminderId);
		if (null == reminder) {
			throw new ReminderNotFoundException("Invalid Reminder Id");
		}
		return reminder;
	}

	/*
	 * Retrieve details of all reminders by userId
	 */
	
	public List<Reminder> getAllReminderByUserId(String userId) {
		Session session = sessionFactory.getCurrentSession();
		Query<Reminder> query = session.createQuery("from Reminder where reminderCreatedBy =:reminderCreatedBy");
		query.setParameter("reminderCreatedBy", userId);
		List<Reminder> resultList = query.getResultList();
		return resultList;
	}

}
