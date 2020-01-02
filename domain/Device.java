package com.joen.domain;

import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("model")
    private String model;

    @SerializedName("token")
    private String fcmToken;

    public Device(String model, String fcmToken){
        this.setModel(model);
        this.setFcmToken(fcmToken);
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
