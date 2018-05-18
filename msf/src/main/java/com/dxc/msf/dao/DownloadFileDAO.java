package com.dxc.msf.dao;

import java.util.List;

import com.dxc.msf.model.DownloadDTO;

public interface DownloadFileDAO {
	public Boolean AddDownloadFile(DownloadDTO download);
	public List<DownloadDTO> getListDownload();
	
	public Double sumSizeFilesDownload(int userID);//Vu Ngo
	public boolean checkSizeFilesDownload(int userID,double currentFileSize);//Vu Ngo
}
