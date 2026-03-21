package com.trade.app.upstock.lib.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.trade.app.upstock.lib.service.UpstockUrlBuilder;

@Service
public class UpstockUrlBuilderImpl implements UpstockUrlBuilder {
	
	@Value("${upstock.code.url}")
	private String url;

	@Value("${upstock.code.value}")
	private String code;

	@Value("${upstock.code.client_id}")
	private String clientId;

	@Value("${upstock.code.redirection_uri}")
	private String redirectionUri;

	@Override
	public String buildAccessCodeUrl(String urlType) {

		StringBuilder br = new StringBuilder("");

		br.append(url).append("?response_type=").append(code).append("&client_id=").append(clientId)
				.append("&redirect_uri=").append(redirectionUri).append("&state=123");

		return br.toString();
	}

}
