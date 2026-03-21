package com.trade.app.upstock.lib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix ="upstock.auth")
public class UpstockAuthConfigs {
	private String secretCode;
	private String url;
	private String redirection_uri;
	private String client_id;
	private String client_secret; 
	private String grant_type;
	
	public String getSecretCode() {
		return secretCode;
	}
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRedirection_uri() {
		return redirection_uri;
	}
	public void setRedirection_uri(String redirection_uri) {
		this.redirection_uri = redirection_uri;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	
}
