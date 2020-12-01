package com.stackroute.keepnote.dao;

import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

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
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	SessionFactory sessionFactory;

	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		this.sessionFactory.getCurrentSession().save(user);
		return true;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(user);
		return true;

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String UserId) {
		User user = this.sessionFactory.getCurrentSession().get(User.class, UserId);
		return user;
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		User user = this.getUserById(userId);
		boolean flag = Boolean.FALSE;
		if (null != user && user.getUserPassword().equals(password)) {
			flag = Boolean.TRUE;
		} else {
			throw new UserNotFoundException("Invalid UserId !!");
		}
		
		return flag;
	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		User user = this.getUserById(userId);
		if (null != user) {
			this.sessionFactory.getCurrentSession().delete(user);
			return true;
		}
		return false;

	}

}
