package com.trade.app.upstock.lib.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthTokenResponseDto(@JsonIgnoreProperties String access_token) {

}
