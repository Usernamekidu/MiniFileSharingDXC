package com.dxc.msf.dao;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.DateUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.msf.model.CategoryDTO;
import com.dxc.msf.model.FileDTO;
import com.dxc.msf.model.UserDTO;

import net.sourceforge.jtds.jdbc.DateTime;

@Repository
public class FileDAOImpl implements FileDAO {

	@Autowired
	SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	@Override
	public boolean AddUploadFile(FileDTO file) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYYhhmmss");
			Date getDate = new Date();
			String dateString = sdf.format(getDate);
			file.setRootPath(dateString + "_" + file.getRootPath());
			file.setUploadDate(new Date());
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(file);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Nhut Lam update file
	@Override
	public boolean UpdateFile(int fileID) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			FileDTO file = getFileByID(fileID);
			session.update(file);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Nhut Lam delete file
	@Override
	public boolean DeleteFile(int fileID) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			String query = "DELETE Downloads WHERE Downloads.fileID=" + fileID;
			SQLQuery sqlQuery = session.createSQLQuery(query);
			sqlQuery.executeUpdate();
			
			FileDTO file = getFileByID(fileID);
			session.delete(file);
			
			String path ="D:\\upload\\"+file.getRootPath();
			System.out.println(path);
			File filepath = new File(path);
			if(filepath.delete()){
    			System.out.println(filepath.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
			
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public List<FileDTO> getListFile() {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<FileDTO> list = (List<FileDTO>) session.createQuery("from Files").list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	// vungo
	public Double sumSizeFileUploaded(FileDTO file) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		int userID = file.getUserPk().getUserID();
		Date changeRankDate = file.getUserPk().getChangeRankDate();

		UserDTO user = (UserDTO) session.get(UserDTO.class, file.getUserPk().getUserID());

		String query = "";
		// User
		if (user.getChangeRankDate() != null) {
			query = "select sum(fileSize) from Files  INNER JOIN Users " + "ON Files.userID = Users.userID "
					+ "where Users.userID=" + userID + " AND Files.uploadDate > '" + user.getChangeRankDate() + "'";
		} else {
			query = "select sum(fileSize) from Files  INNER JOIN Users " + "ON Files.userID = Users.userID "
					+ "where Users.userID=" + userID;
		}
		List<Double> resultSumSizeUploadFile = (List<Double>) session.createSQLQuery(query).list();
		Double result = resultSumSizeUploadFile.get(0);

		transaction.commit();
		session.close();
		return result;
	}

	@Override
	// vungo
	public void autoUpRank(FileDTO file) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();

		UserDTO user = (UserDTO) session.get(UserDTO.class, file.getUserPk().getUserID());
		double totalSizeUploaded = sumSizeFileUploaded(file);
		System.out.println(totalSizeUploaded);

		if (totalSizeUploaded >= (50 * 1024 * 1024) && totalSizeUploaded <= (100 * 1024 * 1024))
			user.setUserRank("Silver");
		else if (totalSizeUploaded > (100 * 1024 * 1024))
			user.setUserRank("Gold");
		else
			user.setUserRank("Bronze");

		session.update(user);
		transaction.commit();
		session.close();
	}

	@Override
	// vungo
	public boolean checkFileSizeUpload(int userID, double currentFileSize) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();

			UserDTO user = (UserDTO) session.get(UserDTO.class, userID);

			String rankUser = user.getUserRank();
			boolean isCheck = false;
			if (rankUser.equals("Bronze") && currentFileSize <= 5)
				isCheck = true;
			else if (rankUser.equals("Silver") && currentFileSize <= 10)
				isCheck = true;
			else if (rankUser.equals("Gold") && currentFileSize <= 20)
				isCheck = true;
			transaction.commit();
			session.close();
			return isCheck;
		} catch (Exception e) {
			return false;
		}
	}

	

	@Override
	public FileDTO getFileByID(int fileID) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			FileDTO file = (FileDTO) session.get(FileDTO.class, new Integer(fileID));
			transaction.commit();
			session.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean DeleteFileTest(int fileID) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			FileDTO file = getFileByID(fileID);
			session.delete(file);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Object[]> countFileInCategoty() {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String query = "SELECT Categories.categoryName,COUNT(Files.fileID)"
					+ " FROM Files Inner JOIN Categories ON Files.categoryID=Categories.categoryID "
					+ "GROUP BY Categories.categoryName";
			List<Object[]> list = (List<Object[]>) session.createSQLQuery(query).list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<FileDTO> searchFile() {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String query = "";
			System.out.println("+++++++++++");
			query = "Select Files.* From Files" + " INNER JOIN Categories On Files.categoryID = Categories.categoryID";
			System.out.println(query);
			System.out.println("+++++++++++");

			List<FileDTO> list = (List<FileDTO>) session.createSQLQuery(query).addEntity(FileDTO.class).list();
			System.out.println("+++++++++++");

			System.out.println(list.size());
			System.out.println("+++++++++++");

			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<FileDTO> getFileByUserID(int userID) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String query = "SELECT * FROM Files WHERE Files.userID="+userID;
			System.out.println(query);

			List<FileDTO> list = (List<FileDTO>) session.createSQLQuery(query).addEntity(FileDTO.class).list();
			System.out.println(list.size());
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	@Override
	public boolean updateFileDescription(FileDTO fileID) {
		Session session = sessionfactory.openSession();
		Transaction transaction =session.beginTransaction();
		try {
			 String hql = "UPDATE Files set description = ? WHERE fileID = ?";
		     Query query = session.createQuery(hql);
		     query.setParameter(0, fileID.getDescription());
		     query.setParameter(1, fileID.getFileID());
		     query.executeUpdate();	     
		     transaction.commit();
		 	session.close();
			return true;
		} catch (Exception e) {
			return false;
			}
	}
	
	
	@Override
	// vungo
	public List<FileDTO> searchFiles(String searchByRootPath, String searchByEmail, int searchByCategoryID) {
		// TODO Auto-generated method stub
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			String query = "";

			if (searchByRootPath.equals("") && searchByEmail.equals(""))
				query = "Select Files.* From Files  INNER JOIN Users ON Files.userID = Users.userID "
						+ "INNER JOIN Categories On Files.categoryID = Categories.categoryID "
						+ "WHERE" + " Categories.categoryID="
						+ searchByCategoryID  ;
	
			else if (searchByEmail.equals(""))
				query = "Select Files.* From Files  INNER JOIN Users ON Files.userID = Users.userID "
						+ "INNER JOIN Categories On Files.categoryID = Categories.categoryID "
						+ "WHERE (Files.rootPath LIKE '%" + searchByRootPath + "%')" + "AND Categories.categoryID="
						+ searchByCategoryID  ;
			else if(searchByRootPath.equals(""))
				query = "Select Files.* From Files  INNER JOIN Users ON Files.userID = Users.userID "
						+ "INNER JOIN Categories On Files.categoryID = Categories.categoryID "
						+ "WHERE (Users.email LIKE '%" + searchByEmail + "%')" + "AND Categories.categoryID="
						+ searchByCategoryID  ;
			else
				query = "Select Files.* From Files  INNER JOIN Users ON Files.userID = Users.userID "
						+ "INNER JOIN Categories On Files.categoryID = Categories.categoryID "
						+ "WHERE (Users.email LIKE '%" + searchByEmail + "%' OR Files.rootPath LIKE '%"
						+ searchByRootPath + "%') AND Categories.categoryID=" + searchByCategoryID;

			System.out.println(query);
			List<FileDTO> list = (List<FileDTO>) session.createSQLQuery(query).addEntity(FileDTO.class).list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
