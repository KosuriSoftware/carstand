package com.zoro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zoro.dao.CabDAO;

@Service
public class CabSearchService {
	
	@Autowired
	CabDAO cabDAO;

}
