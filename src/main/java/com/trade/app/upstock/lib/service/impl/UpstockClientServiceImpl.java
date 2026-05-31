package com.trade.app.upstock.lib.service.impl;

import java.time.Duration;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.app.upstock.lib.config.UpstockAuthConfigs;
import com.trade.app.upstock.lib.dto.AuthTokenRequest;
import com.trade.app.upstock.lib.dto.AuthTokenResponseDto;
import com.trade.app.upstock.lib.gateway.UpstockApiGateway;
import com.trade.app.upstock.lib.gateway.UpstockClientGateway;
import com.trade.app.upstock.lib.service.UpstockClientService;

@Service
public class UpstockClientServiceImpl implements UpstockClientService{

	@Autowired 
	UpstockAuthConfigs configs;
	
	@Autowired 
	UpstockApiGateway gateway;
	
	@Autowired 
	UpstockClientGateway upstockClientGateway;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public String getAuthToken(String code) {
		// TODO Auto-generated method stub
		
		AuthTokenRequest request=new AuthTokenRequest(code, configs.getClient_id(), 
				configs.getClient_secret(), configs.getRedirection_uri(),
				configs.getGrant_type());
		String response= gateway.callUpstockGetTokenApi(request);
		saveUpstockToken(response);
		return response;
	}

	private void saveUpstockToken(String Response) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		try {
			AuthTokenResponseDto res= mapper.readValue(Response, AuthTokenResponseDto.class);
			redisTemplate.opsForValue().set(configs.getAuthCacheKey(), res.access_token(), Duration.ofMinutes(420));
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public String getPortfolioHoldings() {
		// TODO Auto-generated method stub
		String holdings= upstockClientGateway.callUpstockGetPortfolioApi();
		return holdings;
	}
}
