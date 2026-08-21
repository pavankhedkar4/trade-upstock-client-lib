package com.trade.app.upstock.lib.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public interface UpstockClientService {
	String getAuthToken(String code);
	String getPortfolioHoldings();
	String getUpstockToken();
	String getMarketDataFeedUrl();
	void subscribeToUpstockLiveFeedData(String stockCode) throws JsonProcessingException;
}
