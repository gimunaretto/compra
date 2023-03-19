package com.fiap.autorizadora.utils;

public class HTTPMessageResponse {
    private String message;

    public HTTPMessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
