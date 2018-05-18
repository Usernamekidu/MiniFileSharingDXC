package com.dxc.msf.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dxc.msf.dao.DownloadFileDAO;
import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;
import com.dxc.msf.service.CategoryService;
import com.dxc.msf.service.FileService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class FileController {

	@Autowired
	FileService fileService;
	@Autowired
	DownloadFileDAO downloadService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	 ServletContext context;

	// download file

	@RequestMapping(value = "/file/download", method = RequestMethod.POST)
	public @ResponseBody String downloadFile(HttpServletResponse response,
			@RequestBody String fileId) {
		boolean downloadSuccess = fileService.downloadFile(response, fileId);
		if (downloadSuccess) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	@RequestMapping(value = "/file/adddownloadfile", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String downloadFile(@RequestBody DownloadDTO download) {
		try {
			FileDTO file = fileService.getFileByID(download.getDownloadFilePk().getFileID());
			System.out.println("++++++--------");
			System.out.println(file.getFileSize());
			System.out.println("+++++aaaaaaaaaaaa+");
			System.out.println(download.getDownloadUserPk().getUserID());
			System.out.println("++++++");
			System.out.println(downloadService.checkSizeFilesDownload(download.getDownloadUserPk().getUserID(), file.getFileSize()));
			
			boolean downloadSuccess=downloadService.checkSizeFilesDownload(download.getDownloadUserPk().getUserID(), file.getFileSize());
		
			if (downloadSuccess) {
				downloadSuccess=fileService.addDownloadFile(download);
				if(downloadSuccess)
					return "{\"status\": \"OK\"}";
			}

		} catch (Exception e) {
			return "{\"status\": \"Failed\"}";
		}
		return "{\"status\": \"Failed\"}";
	}
//List Download 
	@RequestMapping(value = "/file/listDownload", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getListDownloadFile() {
		List<DownloadDTO> list = fileService.getListDownloadFile();
		String json = new Gson().toJson(list);
		return json;
	}

	// upload file

	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		MultipartHttpServletRequest mRequest;
		try {
			mRequest = (MultipartHttpServletRequest) request;
			mRequest.getParameterMap();
			Iterator<String> iterator = mRequest.getFileNames();
			while (iterator.hasNext()) {
				MultipartFile mFile = mRequest.getFile(iterator.next());
				boolean uploadSuccess = fileService.uploadFile(mFile);
				if (uploadSuccess) {
					return "{\"status\": \"OK\"}";
				}
			}
		} catch (Exception e) {
			return "{\"status\": \"Failed\"}";
		}
		return "{\"status\": \"Failed\"}";
	}

	@RequestMapping(value = "/file/adduploadfile", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String uploadFile(@RequestBody FileDTO file) {
		try {
			boolean uploadSuccess = fileService.addUploadFile(file);
			if (uploadSuccess) {
				return "{\"status\": \"OK\"}";
			}

		} catch (Exception e) {
			return "{\"status\": \"Failed\"}";
		}
		return "{\"status\": \"Failed\"}";
	}

	// Nhut Lam update file
	
	@RequestMapping(value = "/file/update", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventUpdate(@RequestBody int fileID) {
		boolean success = fileService.UpdateFile(fileID);
		System.out.println(success);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}

	}
	
	// Update description
	@RequestMapping(value = "/file/editDescription", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventEditPassword(@RequestBody FileDTO fileDTO) {
		boolean success = fileService.updateDescription(fileDTO);
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}
	
	

	// Nhut Lam delete file

	@RequestMapping(value = "/file/delete", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String eventDelete(@RequestBody FileDTO file) {
		boolean success = fileService.DeleteFile(file.getFileID());
		if (success) {
			return "{\"status\": \"OK\"}";
		} else {
			return "{\"status\": \"Failed\"}";
		}
	}

	@RequestMapping(value = "/file/list", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getListFile() {
		List<FileDTO> list = fileService.getListFile();
		String json = new Gson().toJson(list);
		return json;
	}


	@RequestMapping(value = "/file/getFile/{fileID}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getFileByID(@PathVariable("fileID") int fileID) {
		FileDTO file = fileService.getFileByID(fileID);
		String json = new Gson().toJson(file);
		return json;
	}
	
	@RequestMapping(value = "/file/countFile", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String countFile() {
		List<Object[]> list = fileService.countFileInCategoty();
		
		JsonArray arrayJson = new JsonArray();
		for(int i = 0 ; i<list.size();i++)
		{
			JsonObject newObject = new JsonObject() ;
			newObject.addProperty("label",list.get(i)[0].toString());
			newObject.addProperty("value",list.get(i)[1].toString());
			arrayJson.add(newObject);
		}
		String json = new Gson().toJson(arrayJson);
		return json;
	}
	
	@RequestMapping(value = "/file/downloadTest", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String down(HttpServletRequest request, HttpServletResponse response,@RequestBody int fileID) throws IOException {
		System.out.println("aaaaaaaaaaaaaa");
		System.out.println(fileID);
		fileService.downloadFile(fileID, response);
		return "";
	}
	//download
	@RequestMapping(value = "/file/downloadTest2/{id}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String downaa(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id) throws IOException {
		
		System.out.println(id);
		fileService.downloadFile(id, response);
		return "";
	}
	
	@RequestMapping(value = "file/search", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String searchFile() {
		List<FileDTO> listFile = fileService.searchFile();
		List<CategoryDTO> listCategory = categoryService.getListCategory();

		JsonArray arrayJsonCategory = new JsonArray();
		
		for(int i = 0 ; i<listCategory.size();i++)
		{
			JsonArray arrayJsonInOneCategory = new JsonArray();
			JsonObject listJson = new JsonObject() ;
			for(int j = 0 ; j<listFile.size();j++)
			{
				if(listCategory.get(i).getCategoryID() == listFile.get(j).getCategoryPk().getCategoryID())
				{
				
					JsonObject newObject = new JsonObject() ;
//					newObject.addProperty("filename",listFile.get(j).getFileName());
					newObject.addProperty("category",listFile.get(j).getCategoryPk().getCategoryName());
					newObject.addProperty("size",listFile.get(j).getFileSize());
					newObject.addProperty("dateupload",listFile.get(j).getUploadDate().toString());
					newObject.addProperty("uploadby",listFile.get(j).getUserPk().getEmail());
					newObject.addProperty("download","100");
					newObject.addProperty("comment",listFile.get(j).getDescription());
					newObject.addProperty("rootPath",listFile.get(j).getRootPath());
					arrayJsonInOneCategory.add(newObject);
				}
			}
			listJson.addProperty("category", listCategory.get(i).getCategoryName());
			listJson.add("item", arrayJsonInOneCategory);
			arrayJsonCategory.add(listJson);
		}
	
		
		String json = new Gson().toJson(arrayJsonCategory);
		return json;
	
	}
	
	@RequestMapping(value = "/file/getFileByUserID", method = RequestMethod.POST, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody String getFileByUserID(@RequestBody UserDTO UserDTO) {
		System.out.println(UserDTO.getUserID());
		List<FileDTO> file = fileService.getFileByUserID(UserDTO.getUserID());
		System.out.println(file.size());
		String json = new Gson().toJson(file);
		return json;
	}
	
	@RequestMapping(value = "/file/searchFile", method = RequestMethod.POST, produces ={ "application/json ; charset=UTF-8 ; text/plain" })
	public @ResponseBody String searchFiles(@RequestBody FileDTO file) {
	
		String fileName = file.getRootPath();
		int categoryID = file.getCategoryPk().getCategoryID();
		String userEmail = file.getUserPk().getEmail();
		if(fileName==null)
			fileName="";
		if(userEmail==null)
			userEmail="";
		List<FileDTO> list = fileService.searchFiles(fileName, userEmail, categoryID);
		String json = new Gson().toJson(list);
		return json;
	}	
}
