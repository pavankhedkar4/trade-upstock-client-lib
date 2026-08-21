package com.trade.app.upstock.lib.gateway;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.app.upstock.lib.config.UpstockAuthConfigs;
import com.trade.app.upstock.lib.config.UpstockClientApiConfigs;
import com.trade.app.upstock.lib.config.UpstockWebsocketApiConfigs;
import com.trade.app.upstock.lib.dto.UpstockFeedApiAuthResponseDto;
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

	private void callMarketDataFeedApi() {
		HttpClient client = HttpClient.newHttpClient();
		String wsUrl = callMarketDataFeedAuthApi();
		WebSocket webSocket = client.newWebSocketBuilder().buildAsync(URI.create(wsUrl), new WebSocket.Listener() {

			@Override
			public void onOpen(WebSocket webSocket) {
				System.out.println("Connected");

				String subscribeMessage = """
						{
						  "guid":"123",
						  "method":"sub",
						  "data":{
						    "mode":"ltpc",
						    "instrumentKeys":[
						      "NSE_EQ|INE002A01018"
						    ]
						  }
						}
						""";

				webSocket.sendText(subscribeMessage, true);
				WebSocket.Listener.super.onOpen(webSocket);
			}

			@Override
			public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {

				System.out.println("Received: " + data);
				return WebSocket.Listener.super.onText(webSocket, data, last);
			}

			@Override
			public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {

				System.out.println("Binary message received");
				return WebSocket.Listener.super.onBinary(webSocket, data, last);
			}
		}).join();

	}

	public String callMarketDataFeedAuthApi() {
		HttpClient client = HttpClient.newHttpClient();
		String dataFeedResponse = "";
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(upstockWebsocketApiConfigs.getMarket_data_feed_auth_url()))
				.header("Authorization", getUpstockToken()).GET().build();
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		try {
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			UpstockFeedApiAuthResponseDto resDto = mapper.readValue(response.body(),
					UpstockFeedApiAuthResponseDto.class);
			dataFeedResponse = resDto.data().authorizedRedirectUri();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataFeedResponse;

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
