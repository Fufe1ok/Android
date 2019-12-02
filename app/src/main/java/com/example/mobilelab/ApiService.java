package com.example.mobilelab;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("sensors")   //http get буде йти по шляху solar
    Call<List<Sensor>> getSensors();
}