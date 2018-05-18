package com.dxc.msf.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dxc.msf.model.DownloadDTO;
import com.dxc.msf.model.UserDTO;

@Repository
public class DownloadFileDAOImpl implements DownloadFileDAO {

	@Autowired
	SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}

	@Override
	public Boolean AddDownloadFile(DownloadDTO download) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction = session.beginTransaction();
			download.setDownloadDate(new Date());
			session.save(download);
			transaction.commit();
			session.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<DownloadDTO> getListDownload() {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			List<DownloadDTO> list = (List<DownloadDTO>) session.createQuery("from Downloads").list();
			transaction.commit();
			session.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



	@Override//Vu Ngo
	public Double sumSizeFilesDownload(int userID) {
		// TODO Auto-generated method stub
		double total = 0;
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		SimpleDateFormat formatDate = new SimpleDateFormat ("yyyy/MM/dd");
		Date dateNow = new Date();
		
		String query = "from Downloads where userID =" + userID;
		List<DownloadDTO> listDownload = (List<DownloadDTO>) session.createQuery(query).list();
		
		for (int i = 0; i < listDownload.size(); i++) {
			if(formatDate.format(dateNow).equals(formatDate.format(listDownload.get(i).getDownloadDate())))
					total += listDownload.get(i).getDownloadFilePk().getFileSize();
		}
		return total;
		
	}

	@Override //Vu Ngo
	public boolean checkSizeFilesDownload(int userID, double currentFileSize) {
		try {
			Session session = sessionfactory.openSession();
			Transaction transaction =session.beginTransaction();
			
			UserDTO user =  (UserDTO) session.get(UserDTO.class, userID);
			
			String rankUser = user.getUserRank();
			boolean isCheck = false;
			double totalSizeDownload = sumSizeFilesDownload(userID) + currentFileSize;
			
			if(rankUser.equals("Bronze") && totalSizeDownload < (50*1024*1024) )
				isCheck = true;
			else if( rankUser.equals("Silver") && totalSizeDownload <  (70*1024*1024))
					isCheck = true;
			else if(rankUser.equals("Gold"))
				isCheck = true;
			
			transaction.commit();
			session.close();
			return isCheck;
		} catch (Exception e) {		
			return false;
		}
	}

}
