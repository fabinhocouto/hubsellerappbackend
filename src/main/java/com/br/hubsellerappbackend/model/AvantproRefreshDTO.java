package com.br.hubsellerappbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AvantproRefreshDTO {
	
	 @JsonProperty("access_token")
	    private String accessToken;

	    @JsonProperty("expires_in")
	    private Integer expiresIn;

	    @JsonProperty("id_token")
	    private String idToken;

	    @JsonProperty("refresh_token")
	    private String refreshToken;

	    @JsonProperty("scope")
	    private String scope;

	    @JsonProperty("token_type")
	    private String tokenType;

}
