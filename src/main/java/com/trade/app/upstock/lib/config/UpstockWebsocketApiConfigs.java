package com.trade.app.upstock.lib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "upstock.websocket")
public class UpstockWebsocketApiConfigs {
	private String market_data_feed_auth_url;
	private String client_id;

	public String getMarket_data_feed_auth_url() {
		return market_data_feed_auth_url;
	}

	public void setMarket_data_feed_auth_url(String market_data_feed_auth_url) {
		this.market_data_feed_auth_url = market_data_feed_auth_url;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

}
