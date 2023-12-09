package com.zoro.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zoro.dao.CabDAO;
import com.zoro.dto.CabHomeSearch;
import com.zoro.model.SearchCab;

@Service
public class SearchCabService {
	
	@Autowired
	CabDAO cabDAO;

	public List<CabHomeSearch> searchCab(SearchCab searchCab) {

		return cabDAO.searchHCab(searchCab);
	}

}
