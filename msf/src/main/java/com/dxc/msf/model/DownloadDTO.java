package com.dxc.msf.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity(name="Downloads")
public class DownloadDTO extends AbstractDTO{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="downloadID")
	private int downloadID;
	
	@Column(name = "downloadDate")
	private Date downloadDate;
	
	@ManyToOne(fetch =FetchType.EAGER)
	@JoinColumn(name="fileID" , nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonDeserialize(as=FileDTO.class)
    private FileDTO downloadFilePk;
    
    @ManyToOne(fetch =FetchType.EAGER)
	@JoinColumn(name="userID" , nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonDeserialize(as=UserDTO.class)
    private UserDTO downloadUserPk;
	
	public int getDownloadID() {
		return downloadID;
	}

	public void setDownloadID(int downloadID) {
		this.downloadID = downloadID;
	}

	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	public FileDTO getDownloadFilePk() {
		return downloadFilePk;
	}

	public void setDownloadFilePk(FileDTO downloadFilePk) {
		this.downloadFilePk = downloadFilePk;
	}

	public UserDTO getDownloadUserPk() {
		return downloadUserPk;
	}

	public void setDownloadUserPk(UserDTO downloadUserPk) {
		this.downloadUserPk = downloadUserPk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toJSon() throws JsonProcessingException {
		ObjectMapper objectmapper =new ObjectMapper();
		return objectmapper.writeValueAsString(this);
	}
	

}
