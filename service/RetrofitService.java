package com.joen.service;

import com.joen.domain.Device;
import com.joen.domain.Result;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {

    String joeun_server_url = "http://www.joeunjunggi.co.kr/";

    @POST("api/v1.0/token")
    Call<Device> postToken(@Body Device device);

}
