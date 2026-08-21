package com.trade.app.upstock.lib.listener;

import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trade.app.upstock.lib.dto.MarketTickDTO;
import com.trade.app.upstock.lib.websocket.UiWebSocketHandler;
import com.upstox.marketdatafeederv3udapi.rpc.proto.FeedResponse;
import com.upstox.marketdatafeederv3udapi.rpc.proto.Type;

@Component
public class UpstockWebSocketListener implements WebSocket.Listener {

	private UiWebSocketHandler uiHandler = new UiWebSocketHandler();

	private final ObjectMapper mapper = new ObjectMapper();
	
	private final Set<String> subscribed =ConcurrentHashMap.newKeySet();       

	private volatile WebSocket socket;
	
	private String[] instrumentKeys;
	

	public UpstockWebSocketListener(UiWebSocketHandler uiHandler) {

		this.uiHandler = uiHandler;
	}

	@Override
	public void onOpen(WebSocket webSocket) {
		this.socket = webSocket;
		System.out.println("Connected");

		webSocket.request(1);

		try {
			subscribe(new ArrayList<String>());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {

		try {

			byte[] bytes = new byte[data.remaining()];

			data.get(bytes);

			processFeed(bytes);

		} catch (Exception e) {

			e.printStackTrace();
		}

		webSocket.request(1);

		return CompletableFuture.completedFuture(null);
	}

	public void subscribe(List<String> instrumentKey) throws JsonProcessingException {
		List<String> newKeys= instrumentKey.stream()
						.filter(key->!subscribed.contains(key))
						.collect(Collectors.toList());
		if(newKeys.isEmpty() && !instrumentKey.isEmpty())
			return;
		subscribed.addAll(newKeys);
		
		if (subscribed.isEmpty()) {
			subscribed.add("NSE_EQ|INE002A01018");
			//subscribed.add("NSE_EQ|INE467B01029");
			
		}
			
		String[] instKeys = subscribed.toArray(String[]::new);
		this.instrumentKeys=instKeys;
		ObjectMapper mapper = new ObjectMapper();

		String instrumentKeysJson = mapper.writeValueAsString(instKeys);

		String request = """
				{
				  "guid":"some-guid",
				  "method":"sub",
				  "data":{
				     "mode":"full",
				     "instrumentKeys": %s
				  }
				}
				""".formatted(instrumentKeysJson);
		
		this.socket.sendBinary(ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8)), true);
	}

	private void processFeed(byte[] bytes) throws Exception {

		FeedResponse response = FeedResponse.parseFrom(bytes);

		if (response.getType() == Type.market_info) {

			System.out.println("Market Status Tick");

			return;
		}

		response.getFeedsMap().forEach((instrumentKey, feed) -> {

			try {

				if (!feed.hasFullFeed()) {
					return;
				}

				MarketTickDTO tick = MarketTickDTO.map(instrumentKey, response, feed);

				String json = mapper.writeValueAsString(tick);

				uiHandler.broadcast(json);

			} catch (Exception e) {

				e.printStackTrace();
			}
		});
	}

}