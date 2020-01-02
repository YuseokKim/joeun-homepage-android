package com.joen.domain;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result(String message) {
        this.message = message;
    }
}
