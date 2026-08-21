package com.trade.app.upstock.lib.websocket.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.app.upstock.lib.gateway.UpstockClientGateway;
import com.trade.app.upstock.lib.listener.UpstockWebSocketListener;
import com.trade.app.upstock.lib.websocket.UiWebSocketHandler;

import jakarta.annotation.PostConstruct;

@Service
public class UpstoxMarketFeedClient {

	@Autowired
	UpstockClientGateway gateway;
	
	@Autowired
	UpstockWebSocketListener listener;

	private final Set<String> subscribed = new HashSet<>();
	private final UiWebSocketHandler uiHandler;
	
	
	private WebSocket socket;
	

	public UpstoxMarketFeedClient(UiWebSocketHandler uiHandler) {

		this.uiHandler = uiHandler;
	}

	@PostConstruct
	public void connect() {

		try {

			connectToUpstox();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void connectToUpstox() throws Exception {

		HttpClient client = HttpClient.newHttpClient();

	    socket = client.newWebSocketBuilder().buildAsync(URI.create(getWssUrl()), listener).join();
		
	}
	


	private String getWssUrl() {
		return gateway.callMarketDataFeedAuthApi();
	}
}