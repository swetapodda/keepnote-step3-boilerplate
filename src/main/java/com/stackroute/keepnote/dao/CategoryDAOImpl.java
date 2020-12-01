package com.stackroute.keepnote.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	SessionFactory sessionFactory;
	
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.save(category);
		session.flush();
		return true;
	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		Session session = sessionFactory.getCurrentSession();
		Category category = null;
		try {
			// category = this.sessionFactory.getCurrentSession().load(Category.class,
			// categoryId);
			category = this.getCategoryById(categoryId);
			if (null != category) {
				session.delete(category);
				return true;
			}
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(category);
		return true;

	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {

		Category category = this.sessionFactory.getCurrentSession().get(Category.class, categoryId);
		if (null == category) {
			throw new CategoryNotFoundException("Category Not Found");
		}
		return category;

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		Session session = sessionFactory.getCurrentSession();
		Query<Category> query = session.createQuery("from Category where categoryCreatedBy =:categoryCreatedBy");
		query.setParameter("categoryCreatedBy", userId);
		List<Category> resultList = query.getResultList();
		return resultList;

	}

}
