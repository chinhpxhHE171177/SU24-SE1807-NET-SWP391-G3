package com.example.qrcode;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface DeviceApi {
    @GET("api/devices")
    Call<List<Device>> getDevices();
}

