package com.example.qrcode;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListData extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<Device> deviceList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        deviceList = new ArrayList<>();
        adapter = new Adapter(this, deviceList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load existing devices from database to display
        loadDevicesFromDatabase();

        // Setup Retrofit and OkHttpClient
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:8080/ssmqrcode/")
                .client(client) // Use the custom client with logging
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DeviceApi deviceApi = retrofit.create(DeviceApi.class);
        loadData(deviceApi);
    }

    private void loadData(DeviceApi deviceApi) {
        deviceApi.getDevices().enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Device> devices = response.body();
                    int validCount = 0;

                    for (Device device : devices) {
                        // Check if the device is valid
                        if (isDeviceValid(device)) {
                            // Allow devices with existing idCode to be displayed
                            //deviceList.add(device); // Add device to display list
                            // Only insert into database if the idCode does not already exist
                            if (!databaseHelper.isIdExists(device.getIdCode())) {
                                deviceList.add(device);
                                if (databaseHelper.insertDevice(device)) {
                                    validCount++; // Increment valid count if insertion is successful
                                }
                            }
                        }
                    }

                    adapter.notifyDataSetChanged(); // Notify adapter to refresh the view

                    // Provide user feedback
                    if (validCount > 0) {
                        Toast.makeText(ListData.this, validCount + "Devices added to database.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ListData.this, "No new valid devices added to database.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListData.this, "Failed to load data: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Toast.makeText(ListData.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace(); // Print stack trace for additional details
            }
        });
    }

    private void loadDevicesFromDatabase() {
        List<Device> devices = databaseHelper.getAllData(); // Retrieve existing devices from the database
        deviceList.clear();
        deviceList.addAll(devices); // Load existing devices into the list
        adapter.notifyDataSetChanged(); // Notify adapter to refresh the view
    }

    private boolean isDeviceValid(Device device) {
        return device.getIdCode() != null && !device.getIdCode().isEmpty() &&
                device.getCode() != null && !device.getCode().isEmpty() &&
                device.getName() != null && !device.getName().isEmpty() &&
                device.getStageName() != null && !device.getStageName().isEmpty();
    }
}


//import android.os.Bundle;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//public class ListData extends AppCompatActivity {
//    private RecyclerView recyclerView;
//    private Adapter adapter;
//    private ArrayList<Device> deviceList;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_data);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        deviceList = new ArrayList<>();
//        adapter = new Adapter(this, deviceList);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//
//
//        // Add logging interceptor
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(logging)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.5:8080/ssmqrcode/")
//                .client(client) // Use the custom client with logging
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        DeviceApi deviceApi = retrofit.create(DeviceApi.class);
//        loadData(deviceApi);
//    }
//
//    private void loadData(DeviceApi deviceApi) {
//        deviceApi.getDevices().enqueue(new Callback<List<Device>>() {
//            @Override
//            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    deviceList.clear();
//                    deviceList.addAll(response.body());
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(ListData.this, "Failed to load data: " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Device>> call, Throwable t) {
//                Toast.makeText(ListData.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                t.printStackTrace();  // Print stack trace for additional details
//            }
//        });
//    }
//}




//import android.os.Bundle;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.ArrayList;
//
//public class ListData extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private Adapter adapter;
//    private ArrayList<Device> deviceList;
//    private DatabaseHelper databaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_data);
//
//        recyclerView = findViewById(R.id.recyclerView);
//        deviceList = new ArrayList<>();
//        databaseHelper = new DatabaseHelper(this);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new Adapter(this, deviceList);
//        recyclerView.setAdapter(adapter);
//        loadData();
//    }
//
//    private void loadData() {
//        deviceList.clear();
//        deviceList.addAll(databaseHelper.getAllData());
//        adapter.notifyDataSetChanged();
//        if (deviceList.isEmpty()) {
//            Toast.makeText(this, "No devices found", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
