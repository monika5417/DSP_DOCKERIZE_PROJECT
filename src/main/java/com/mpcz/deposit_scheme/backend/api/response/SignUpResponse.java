package com.mpcz.deposit_scheme.backend.api.response;

import com.mpcz.deposit_scheme.backend.api.domain.Consumer;

public class SignUpResponse {
    private Consumer consumer;
    private String token;

    
    // Constructor
    public SignUpResponse(Consumer consumer, String token) {
        this.consumer = consumer;
        this.token = token;
    }

    // Getters and Setters
    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
