package com.dxc.msf.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity(name = "Users")
public class UserDTO extends AbstractDTO {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userID")
	private int userID;

	@Column(name = "userName")
	private String userName;

	@Column(name = "userPassword")
	private String userPassword;

	@Column(name = "isAdmin")
	private String isAdmin;

	@Column(name = "userRank")
	private String userRank;

	@Column(name = "userActive")
	private String userActive;

	@Email
	@Column(name = "email", unique=true)
	private String email;

	@Column(name = "createDate")
	private Date createDate;

	@Column(name = "deleteDate")
	private Date deleteDate;

	@Column(name = "lastModifyDate")
	private Date lastModifyDate;
	
	@Column(name = "changeRankDate")
	private Date changeRankDate;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserRank() {
		return userRank;
	}

	public void setUserRank(String userRank) {
		this.userRank = userRank;
	}



	public String getUserActive() {
		return userActive;
	}

	public void setUserActive(String userActive) {
		this.userActive = userActive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	public Date getChangeRankDate() {
		return changeRankDate;
	}

	public void setChangeRankDate(Date changeRankDate) {
		this.changeRankDate = changeRankDate;
	}

	@Override
	public String toJSon() throws JsonProcessingException {
		ObjectMapper objectmapper =new ObjectMapper();
		return objectmapper.writeValueAsString(this);
	}
}
