package com.dxc.msf.service;

import java.util.List;

import com.dxc.msf.model.UserDTO;

public interface UserService {

	public boolean createUser(UserDTO user);

	public List<UserDTO> getListUser();

	// Nhut Lam update & delete User
	public boolean updateUser(UserDTO userDTO);

	// public boolean disableUser(UserDTO user);
	public UserDTO getUser(int userID);

	public boolean isActive(int userID, String status);

	public List<Object[]> countUserActive();

	// login
	public UserDTO Login(String Email, String Password);

	// down-up rank user
	public boolean ChangeRankUSer(UserDTO userDTO);

	// Active and DisActive user
	public boolean ActiveAndInActiveUser(UserDTO userDTO);
	public boolean updateUserPassword(UserDTO userID);
}
