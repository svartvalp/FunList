package com.kasyan313.FunList.MessageEntities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenEntity {
    @JsonProperty("token")
    private String token;

    public TokenEntity() {
    }

    public TokenEntity(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
