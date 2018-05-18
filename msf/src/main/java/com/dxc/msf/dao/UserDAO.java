package com.dxc.msf.dao;

import java.util.List;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.UserDTO;

public interface UserDAO {
	public boolean createUser(UserDTO user);
	public List<UserDTO> getListUser();
	//Nhut Lam
	public boolean updateUser(UserDTO userID);
//	public boolean disableUser(UserDTO user);
	public boolean isActive(int userID,String status);
	public UserDTO getUser(int userID);
	public List<Object[]> countUserActive();
	
	//Login
	public UserDTO Login(String Email,String Password);
	//down-up rank user
	public boolean ChangeRankUSer(UserDTO userDTO);
	//Active and DisActive user
	public boolean ActiveAndInActiveUser(UserDTO userDTO);
	//edit password user
	public boolean updateUserPassword(UserDTO userID);
}
