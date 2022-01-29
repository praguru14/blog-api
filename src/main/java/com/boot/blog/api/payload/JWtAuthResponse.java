package com.boot.blog.api.payload;

public class JWtAuthResponse {
    private String accessToken;
    private String tokenType="Bearer";

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public JWtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
