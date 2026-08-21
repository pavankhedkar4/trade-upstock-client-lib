package com.trade.app.upstock.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trade.app.upstock.lib.service.UpstockClientService;

@RestController
public class TestController {

	@Autowired
	UpstockClientService service;

	@PostMapping("/test-authapi/{code}")
	public ResponseEntity<String> getTokenResponse(@PathVariable String code) {
		String res = service.getAuthToken(code);
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	

	@GetMapping("/test-authapi/token")
	public ResponseEntity<String> getTokenFromCache() {
		String res = service.getUpstockToken();
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@GetMapping("/test-authapi/test-market-data-auth")
	public ResponseEntity<String> getMarketDataAuthUrl() {
		String res = service.getMarketDataFeedUrl();
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@PostMapping("/test-authapi/subscribe")
	public void getLiveFeed(@RequestBody String stockCode) throws JsonProcessingException{
		service.subscribeToUpstockLiveFeedData(stockCode);
		
	}
	
	
}
