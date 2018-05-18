package com.dxc.msf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dxc.msf.dao.UserDAO;
import com.dxc.msf.model.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userDAO;

	@Override
	public boolean createUser(UserDTO user) {
		if (!user.getUserName().isEmpty() && !user.getUserPassword().isEmpty()) {
			boolean success = userDAO.createUser(user);
			if (success) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	@Override
	public List<UserDTO> getListUser() {
		return userDAO.getListUser();
	}

	// Nhut Lam updateUser

	@Override
	public boolean updateUser(UserDTO userDTO) {
		return userDAO.updateUser(userDTO);
	}

	// Nhut Lam deleteUser
	// @Override
	// public boolean disableUser(UserDTO user) {
	// boolean success = userDAO.disableUser(user);
	// if (success) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	@Override
	public UserDTO getUser(int userID) {
		return userDAO.getUser(userID);
	}

	@Override
	public boolean isActive(int userID, String status) {
		return userDAO.isActive(userID, status);
	}
	@Override
	public List<Object[]> countUserActive() {
		// TODO Auto-generated method stub
		return userDAO.countUserActive();
	}

	@Override
	public UserDTO Login(String Email, String Password) {
		// TODO Auto-generated method stub
		return userDAO.Login(Email, Password);
	}

	@Override
	public boolean ChangeRankUSer(UserDTO userDTO) {
		
		return userDAO.ChangeRankUSer(userDTO);
	}

	@Override
	public boolean ActiveAndInActiveUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return userDAO.ActiveAndInActiveUser(userDTO);
	}

	@Override
	public boolean updateUserPassword(UserDTO userID) {
		// TODO Auto-generated method stub
		return userDAO.updateUserPassword(userID);
	}
}
