package com.dxc.msf.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean createUser(UserDTO user) {
		try {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			user.setCreateDate(new Date());
			user.setIsAdmin("user");
			user.setLastModifyDate(new Date());
			user.setUserActive("Active");
			user.setUserRank("Bronze");
			session.save(user);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<UserDTO> getListUser() {
		Session session = getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<UserDTO> list = (List<UserDTO>) session.createQuery("from Users").list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Nhut Lam updateUser
	@Override
	public boolean updateUser(UserDTO userDTO) {
		Session session = sessionFactory.openSession();
		Transaction transaction =session.beginTransaction();
		try {
			 String hql = "UPDATE Users set userName = ? WHERE userID = ?";
		     Query query = session.createQuery(hql);
		     query.setParameter(0, userDTO.getUserName());
		     query.setParameter(1, userDTO.getUserID());
		     query.executeUpdate();	     
		     transaction.commit();
		 	session.close();
			return true;
		} catch (Exception e) {
			return false;
			}
	}

	@Override
	public UserDTO getUser(int userID) {
		Session session = getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createQuery("FROM Users WHERE userID = ?");
			query.setInteger(0, userID);
			UserDTO userDTO;
			List<UserDTO> userList = query.list();
			userDTO = (UserDTO) userList.get(0);
			transaction.commit();
			session.close();
			return userDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean isActive(int userID, String status) {

		try {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			UserDTO user = getUser(userID);
			user.setUserActive(status);
			session.update(user);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Object[]> countUserActive() {
		try {
			Session session = getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			String query = "SELECT Users.userActive , COUNT(Users.userActive)" + "FROM Users GROUP BY Users.userActive";
			List<Object[]> list = (List<Object[]>) session.createSQLQuery(query).list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UserDTO Login(String Email, String Password) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createQuery("FROM Users WHERE email=? AND userPassword =? AND userActive = 'Active'");
			query.setString(0, Email);
			query.setString(1, Password);
			List<UserDTO> listUser = query.list();
			UserDTO userDTO = (UserDTO) listUser.get(0);
			transaction.commit();
			session.close();
			return userDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean ChangeRankUSer(UserDTO userDTO) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String hql = "UPDATE Users set userRank = ?,changeRankDate=?,lastModifyDate=?  WHERE userID = ?";
			Query query = session.createQuery(hql);
			Date date =new Date();
			query.setParameter(0, userDTO.getUserRank());
			query.setParameter(1, userDTO.getChangeRankDate());
			query.setParameter(2, userDTO.getLastModifyDate());
			query.setParameter(3, userDTO.getUserID());
			query.executeUpdate();
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean ActiveAndInActiveUser(UserDTO userDTO) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String hql = "UPDATE Users set userActive = ?,lastModifyDate=?,deleteDate=? WHERE userID = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, userDTO.getUserActive());
			query.setParameter(1, userDTO.getLastModifyDate());
			query.setParameter(2, userDTO.getDeleteDate());
			query.setParameter(3, userDTO.getUserID());
			query.executeUpdate();
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateUserPassword(UserDTO userID) {
		Session session = sessionFactory.openSession();
		Transaction transaction =session.beginTransaction();
		try {
			 String hql = "UPDATE Users set userPassword = ? WHERE userID = ?";
		     Query query = session.createQuery(hql);
		     query.setParameter(0, userID.getUserPassword());
		     query.setParameter(1, userID.getUserID());
		     query.executeUpdate();	     
		     transaction.commit();
		 	session.close();
			return true;
		} catch (Exception e) {
			return false;
			}
	}

}
