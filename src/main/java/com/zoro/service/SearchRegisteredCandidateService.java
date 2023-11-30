package com.zoro.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoro.dao.AdminDAO;
import com.zoro.dto.RegistrationBean;
import com.zoro.pagination.AdminPagination;

@Service
public class SearchRegisteredCandidateService {
	
	@Autowired
	AdminDAO adminDao;
	
	public List<RegistrationBean> searchRegisteredCandidate() {
		
		String userId=null;
		String userType="C";
		List<RegistrationBean> regList=new AdminPagination().getRegDetails(userType,userId);
		return regList;
		
	}
	

}
