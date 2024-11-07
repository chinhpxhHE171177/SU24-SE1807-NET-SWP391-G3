package com.example.qrcode;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListHistoryActivity extends AppCompatActivity implements UploadDataTask.OnUploadCompleteListener {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private ArrayList<Device_History> deviceList;
    private DatabaseHelper databaseHelper;
    private Handler handler;
    private Runnable uploadRunnable; // Declare the uploadRunnable here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);

        recyclerView = findViewById(R.id.listView);
        deviceList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        Button btnUploadData = findViewById(R.id.btnUploadData);

        btnUploadData.setOnClickListener(v -> {
            uploadData();
            resetUploadTimer(); // Reset the timer when the button is clicked
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(this, deviceList);
        recyclerView.setAdapter(adapter);
        loadData();

        // Start the 5-minute timer
        //startUploadTimer();

        // Start the upload timer, but only if there are devices to upload
        if (!deviceList.isEmpty()) {
            startUploadTimer();
        }
    }

    // Method to start the 5-minute timer
    private void startUploadTimer() {
        handler = new Handler();
        uploadRunnable = new Runnable() {
            @Override
            public void run() {
                // Automatically trigger the upload and re-trigger after 5 minutes
                //uploadData();

                // Check if there is data to upload
                if (!deviceList.isEmpty()) { // them
                    uploadData(); // Automatically trigger the upload
                }

                // Schedule the next upload after 5 minutes
                handler.postDelayed(this, 10000); // 300,000 milliseconds = 5 minutes
            }
        };
        handler.postDelayed(uploadRunnable, 10000); // First trigger after 5 minutes
    }

    // Method to reset the timer if the user manually clicks the button
    private void resetUploadTimer() {
        if (handler != null && uploadRunnable != null) {
            handler.removeCallbacks(uploadRunnable); // Remove any existing callbacks
           // handler.postDelayed(uploadRunnable, 30000); // Restart the 5-minute timer

            // Only restart the timer if there's data in the list
            if (!deviceList.isEmpty()) { // them
                handler.postDelayed(uploadRunnable, 10000); // Restart the 5-minute timer
            }
        }
    }

    private void loadData() {
        deviceList.clear();
        deviceList.addAll(databaseHelper.getHistoryData());
        adapter.notifyDataSetChanged();
        if (deviceList.isEmpty()) {
            Toast.makeText(this, "No history records found", Toast.LENGTH_SHORT).show();
        }  else { // them
            // If we have data, we can start the timer
            startUploadTimer();
        }
    }

    private void uploadData() {
        if (deviceList.isEmpty()) {
            Toast.makeText(this, "No data to upload!", Toast.LENGTH_SHORT).show();
            return;
        }
        new UploadDataTask(this, this).execute(deviceList);
    }

    @Override
    public void onUploadComplete(Boolean success) {
        if (success) {
            // Clear the history list after a successful upload
            databaseHelper.clearHistoryRecords(); // Assuming you have a method to clear records
            deviceList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "History records cleared.", Toast.LENGTH_SHORT).show();
        }
    }
}
