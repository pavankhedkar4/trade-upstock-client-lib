package com.trade.app.upstock.lib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "upstock.portfolio")
public class UpstockClientApiConfigs {

	private String holdings_url;

	public String getHoldings_url() {
		return holdings_url;
	}

	public void setHoldings_url(String holdings_url) {
		this.holdings_url = holdings_url;
	}

}
