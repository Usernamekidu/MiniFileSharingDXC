// Nhut Lam
package com.dxc.msf.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;

@Repository
public class CategoryDAOImpl implements CategoryDAO{
	@Autowired
	SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}
	@Override
	public boolean CreateCategory(CategoryDTO category) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction =session.beginTransaction();
			session.save(category);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean UpdateCategory(CategoryDTO categoryDTO) {
		Session session = sessionfactory.openSession();
		Transaction transaction =session.beginTransaction();
		try {
			 String hql = "UPDATE Categories set categoryName = ? WHERE categoryID = ?";
		     Query query = session.createQuery(hql);
		     query.setParameter(0, categoryDTO.getCategoryName());
		     query.setParameter(1, categoryDTO.getCategoryID());
		     query.executeUpdate();	     
		     transaction.commit();
		 	session.close();
			return true;
		} catch (Exception e) {
			return false;
			}
	}

	@Override
	public boolean DeleteCategory(int categoryID) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction =session.beginTransaction();
			CategoryDTO category = getCategoryByID(categoryID);
			session.delete(category);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<CategoryDTO> getListCategory() {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			try {
				List<CategoryDTO> list = (List<CategoryDTO>) session.createQuery("from Categories").list();
				transaction.commit();
				session.close();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} 
	}

	@Override
	public CategoryDTO getCategoryByID(int categoryID) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createQuery("FROM Categories WHERE categoryID = ?");
			query.setInteger(0, categoryID);
			CategoryDTO categoryDTO;
			List<CategoryDTO> listCategory= query.list();
			categoryDTO = (CategoryDTO) listCategory.get(0);
			transaction.commit();
			session.close();
			return categoryDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	
		
	
}
