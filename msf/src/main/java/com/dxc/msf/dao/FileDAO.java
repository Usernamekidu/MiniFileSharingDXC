package com.dxc.msf.dao;

import java.util.List;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;

public interface FileDAO {
	
	public boolean AddUploadFile(FileDTO file);
	public boolean UpdateFile(int fileID);// Nhut Lam
	public boolean DeleteFile(int fileID);// Nhut Lam
	public FileDTO getFileByID(int fileID);	
	public boolean DeleteFileTest(int fileID);
	public List<FileDTO> getListFile();
	
	//Vu Ngo
	public Double sumSizeFileUploaded(FileDTO file);
	public void autoUpRank(FileDTO file);
	public boolean checkFileSizeUpload(int userID,double currentFileSize);
	public List<Object[]> countFileInCategoty();
	
	//-----------------//
	public List<FileDTO> searchFile();
	public List<FileDTO> getFileByUserID(int userID);	

	public List<FileDTO> searchFiles(String searchByRootPath, String searchByEmail, int searchByCategoryID);
	public boolean updateFileDescription(FileDTO fileID);
}
