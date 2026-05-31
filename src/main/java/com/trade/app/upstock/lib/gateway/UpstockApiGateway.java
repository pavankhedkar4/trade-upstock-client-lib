package com.trade.app.upstock.lib.gateway;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trade.app.upstock.lib.config.UpstockAuthConfigs;
import com.trade.app.upstock.lib.dto.AuthTokenRequest;

@Component
public class UpstockApiGateway {
	
	@Autowired
	UpstockAuthConfigs configs;

	public String callUpstockGetTokenApi(AuthTokenRequest request) {
		String response="";
		try {
			 String requestBody =
	                    "code=" + request.getCode() +
	                    "&client_id=" + request.getClient_id() +
	                    "&client_secret=" + request.getClient_secret() +
	                    "&redirect_uri=" + request.getRedirect_uri() +
	                    "&grant_type=authorization_code";
			
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(configs.getUrl()))
					.header("accept", "application/json").header("Content-Type", "application/x-www-form-urlencoded")
					.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString())).build();

			HttpClient client = HttpClient.newHttpClient();

			HttpResponse<String> res = client.send(httpRequest, BodyHandlers.ofString());
			// .send(request, HttpResponse.BodyHandlers.ofString());
			return res.body();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}
	
}
