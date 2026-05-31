package com.trade.app.upstock.lib.gateway;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import com.trade.app.upstock.lib.config.UpstockAuthConfigs;
import com.trade.app.upstock.lib.config.UpstockClientApiConfigs;
import com.trade.app.upstock.lib.config.UpstockWebsocketApiConfigs;
import com.trade.app.upstock.lib.service.UpstockClientService;

@Component
public class UpstockClientGateway {
	@Autowired
	UpstockClientApiConfigs apiConfigs;

	@Autowired
	UpstockAuthConfigs authConfigs;

	@Autowired
	UpstockWebsocketApiConfigs upstockWebsocketApiConfigs;

	@Autowired
	UpstockApiGateway gateway;

	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	public String callUpstockGetPortfolioApi() {
		String response = "";
		try {
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(apiConfigs.getHoldings_url()))
					.header("accept", "application/json").header("Content-Type", "application/json")
					.header("Authorization", getUpstockToken()).GET().build();

			HttpClient client = HttpClient.newHttpClient();

			HttpResponse<String> res = client.send(httpRequest, BodyHandlers.ofString());
			// .send(request, HttpResponse.BodyHandlers.ofString());
			response = res.body();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public String getUpstockToken() {
		StringBuilder token = new StringBuilder("Bearer ");
		try {
			token.append(redisTemplate.opsForValue().get(authConfigs.getAuthCacheKey()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token.toString();
	}
}
