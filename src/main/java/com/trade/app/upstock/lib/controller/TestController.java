package com.trade.app.upstock.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
