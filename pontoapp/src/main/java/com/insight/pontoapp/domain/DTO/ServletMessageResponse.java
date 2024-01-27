package com.insight.pontoapp.domain.DTO;

public class ServletMessageResponse {
    private String message;

    public ServletMessageResponse() {
    }

    public ServletMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

