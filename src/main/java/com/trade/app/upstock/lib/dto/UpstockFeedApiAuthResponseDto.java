package com.trade.app.upstock.lib.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpstockFeedApiAuthResponseDto(String status, FeedData data) {

	public record FeedData(String authorizedRedirectUri,

			@JsonProperty("authorized_redirect_uri") String authorizedRedirectUriSnakeCase) {
	}
}