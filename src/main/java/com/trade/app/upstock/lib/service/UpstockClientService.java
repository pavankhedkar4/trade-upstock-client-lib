package com.trade.app.upstock.lib.service;

import org.springframework.stereotype.Service;

@Service
public interface UpstockClientService {
	String getAuthToken(String code);
}
