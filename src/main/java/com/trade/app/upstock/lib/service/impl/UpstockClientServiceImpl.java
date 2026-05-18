package com.trade.app.upstock.lib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.app.upstock.lib.config.UpstockAuthConfigs;
import com.trade.app.upstock.lib.dto.AuthTokenRequest;
import com.trade.app.upstock.lib.gateway.UpstockApiGateway;
import com.trade.app.upstock.lib.service.UpstockClientService;

@Service
public class UpstockClientServiceImpl implements UpstockClientService{

	@Autowired 
	UpstockAuthConfigs configs;
	
	@Autowired 
	UpstockApiGateway gateway;
	
	@Override
	public String getAuthToken(String code) {
		// TODO Auto-generated method stub
		
		AuthTokenRequest request=new AuthTokenRequest(code, configs.getClient_id(), 
				configs.getClient_secret(), configs.getRedirection_uri(),
				configs.getGrant_type());
		String response= gateway.callUpstockGetTokenApi(request);
		
		return response;
	}

}
