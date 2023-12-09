package com.zoro.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.zoro.dto.CabHomeSearch;
import com.zoro.model.SearchCab;
import com.zoro.service.SearchCabService;

@RestController
public class SearchCabController {
	
	@Autowired
	SearchCabService searchCabService;
	
	@GetMapping("/searchCab")
	public ResponseEntity<List<CabHomeSearch>> searchCabRequests(@RequestBody SearchCab searchCab) {
		List<CabHomeSearch> regList = searchCabService.searchCab(searchCab);
		return ResponseEntity.ok(regList);
	}

}
