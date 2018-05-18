package com.dxc.msf.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;

public interface FileService {
	//upload
	public Boolean uploadFile(MultipartFile file);
	public Boolean addUploadFile(FileDTO fileDto);
	public List<FileDTO> getListFile();
	//download
	public Boolean downloadFile(HttpServletResponse response, String fileId);
	public boolean addDownloadFile(DownloadDTO download);
	public List<DownloadDTO> getListDownloadFile();
	//Nhut Lam update file
	public boolean UpdateFile(int fileID);
	//Nhut Lam delete file
	public FileDTO getFileByID(int fileID);	
	public boolean DeleteFile(int fileID);
	//vungo
//	public void autoUpRank(FileDTO file);
	public boolean checkFileSizeUpload(int userID,double currentFileSize);
	public boolean checkSizeFilesDownload(int userID,double currentFileSize);
	public List<Object[]> countFileInCategoty();
	//-----------------//
	public void downloadFile(int fileID,HttpServletResponse response);//
	public List<FileDTO> searchFile();
	
	public List<FileDTO> getFileByUserID(int userID);	
	public List<FileDTO> searchFiles(String searchByRootPath, String searchByEmail, int searchByCategoryID);
	public boolean updateDescription(FileDTO fileID);

}
