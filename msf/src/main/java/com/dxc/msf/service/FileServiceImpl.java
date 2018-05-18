package com.dxc.msf.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.xml.dtm.DTMDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.msf.dao.DownloadFileDAO;
import com.dxc.msf.dao.FileDAO;
import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.FileDTO;

@Service
public class FileServiceImpl implements FileService {

	ServletContext servletContext;
	@Autowired
	FileDAO fileDao;
	@Autowired
	DownloadFileDAO downloadFileDAO;
	@Autowired
	ServletContext context;

	public ServletContext getContext() {
		return servletContext;
	}

	@Autowired
	public void setContext(ServletContext context) {
		this.servletContext = context;
	}

	private static final String uploadFilePath = "resources" + File.separator + "uploaded_files";

	@Override
	public Boolean uploadFile(MultipartFile file) {
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			try {
				byte[] fileBytes = file.getBytes();

				// // generate rootPath and check folder existence. If not, create
				// // new folder based
				// // on rootPath
				// String rootPath = getContext().getRealPath("");
				// File folder = new File(rootPath + uploadFilePath);
				// if (!folder.exists()) {
				// folder.mkdirs();
				// }
				//
				// // upload file on server
				// File uploadFile = new File(folder.getAbsolutePath()
				// + File.pathSeparator + fileName);
				// BufferedOutputStream bos = new BufferedOutputStream(
				// new FileOutputStream(uploadFile));
				// generate rootPath and check folder existence. If not, create new folder based
				// on rootPath
				String rootPath = getContext().getRealPath("");
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYYhhmmss");
				Date getDate = new Date();
				String dateString = sdf.format(getDate);
				// File folder = new File(rootPath + uploadFilePath);
				java.nio.file.Path path = Paths
						.get("D:\\upload\\" + dateString + "_" + fileName);
				// if (!folder.exists()) {
				// folder.mkdirs();
				// }

				// upload file on server
				File uploadFile = new File(path.toString());
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(uploadFile));
				bos.write(fileBytes);
				bos.close();
				return true;

			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	// this function using for user download files from Server based on File
	// Name
	@Override
	public Boolean downloadFile(HttpServletResponse response, String fileId) {
		int fileSize = getFileSize(fileId);
		String fileName = getFileName(fileId);
		String rootPath = getContext().getRealPath("") + uploadFilePath + fileName;

		// Refine file name
		String regexPattern = "(\\D+)";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(fileName);
		String refinedFileName = matcher.group(0).substring(0, matcher.group(0).length() - 1);

		File downloadFile = new File(rootPath);
		if (downloadFile.exists() && !downloadFile.isDirectory()) {
			try {
				OutputStream os = response.getOutputStream();
				// set content type and header for response
				response.setHeader("Content-Disposition", "attachment; filename=" + refinedFileName);
				response.setContentType("text/html");
				response.setContentType("APPLICATION/OCTET-STREAM");

				// start to write file to Stream
				FileInputStream fis = new FileInputStream(downloadFile);
				byte[] buffer = new byte[fileSize];
				int length;
				while ((length = fis.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				fis.close();
				os.flush();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	private String getFileName(String fileId) {
		// implement DAO to get file name from DB base on fileID
		return "";
	}

	private int getFileSize(String fileId) {
		// implement DAO to get file size from DM based on fileID
		return 0;
	}

	@Override
	public Boolean addUploadFile(FileDTO file) {
		if (!file.getRootPath().isEmpty()) {

			boolean susscess = fileDao.AddUploadFile(file);
			fileDao.autoUpRank(file);
			if (susscess) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public List<FileDTO> getListFile() {
		return fileDao.getListFile();
	}

	@Override
	public boolean addDownloadFile(DownloadDTO download) {
		boolean susscess = downloadFileDAO.AddDownloadFile(download);
		if (susscess) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<DownloadDTO> getListDownloadFile() {
		return downloadFileDAO.getListDownload();
	}

	// update file
	@Override
	public boolean UpdateFile(int fileID) {
		return fileDao.UpdateFile(fileID);

	}
	// @Override
	// public void autoUpRank(int userID) {
	// fileDao.autoUpRank(userID);
	// }

	@Override
	public boolean checkFileSizeUpload(int userID, double currentFileSize) {
		return fileDao.checkFileSizeUpload(userID, currentFileSize);
	}

	@Override
	public boolean checkSizeFilesDownload(int userID, double currentFileSize) {
		return downloadFileDAO.checkSizeFilesDownload(userID, currentFileSize);
	}


	@Override
	public FileDTO getFileByID(int fileID) {
		return fileDao.getFileByID(fileID);

	}

	// delete file
	@Override
	public boolean DeleteFile(int fileID) {
		return fileDao.DeleteFile(fileID);
	}

	@Override
	public List<Object[]> countFileInCategoty() {
		// TODO Auto-generated method stub
		return fileDao.countFileInCategoty();
	}

	@Override
	public void downloadFile(int fileID, HttpServletResponse response) {
		// TODO Auto-generated method stub
		FileDTO file = fileDao.getFileByID(fileID);
		System.out.println("Downloading file :- " + file.getRootPath());
		String downloadFolder = context.getRealPath("/WEB-INF/downloads/");
		String pa = "D:\\upload\\";
		Path pathFile = Paths.get(pa, file.getRootPath());
		System.out.println(pathFile);
		if (Files.exists(pathFile)) {

			File downloadFile = new File(pathFile.toString());
			FileInputStream inputStream;
			try {
				inputStream = new FileInputStream(downloadFile);
				// set content type
				String mimeType = context.getMimeType(pathFile.toString());
				if (mimeType == null) {
					// set to binary type if MIME mapping not found
					mimeType = "application/octet-stream";
				}
				System.out.println("MIME type: " + mimeType);

				// set content attributes for the response
				response.setContentType(mimeType);
				response.setContentLength((int) downloadFile.length());

				// set headers for the response
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
				response.setHeader(headerKey, headerValue);

				// get output stream of the response
				OutputStream outStream;
				try {
					outStream = response.getOutputStream();

					byte[] buffer = new byte[4096];
					int bytesRead = -1;

					// write bytes read from the input stream into the output stream
					while ((bytesRead = inputStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inputStream.close();
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Sorry File not found!!!!");
		}

	}

	@Override
	public List<FileDTO> searchFile() {
		return fileDao.searchFile();
	}

	@Override
	public List<FileDTO> getFileByUserID(int userID) {
		// TODO Auto-generated method stub
		return fileDao.getFileByUserID(userID);
	}

	@Override
	public List<FileDTO> searchFiles(String searchByRootPath, String searchByEmail, int searchByCategoryID){
		return fileDao.searchFiles(searchByRootPath, searchByEmail, searchByCategoryID);
	}

	@Override
	public boolean updateDescription(FileDTO fileID) {
		// TODO Auto-generated method stub
		return fileDao.updateFileDescription(fileID);
	}
}
