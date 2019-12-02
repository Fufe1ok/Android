package com.example.mobilelab;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("sensors")
    Call<List<Sensor>> getSensors();
}