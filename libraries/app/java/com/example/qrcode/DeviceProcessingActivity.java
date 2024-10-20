package com.example.qrcode;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class DeviceProcessingActivity extends AppCompatActivity {
    private ListView listView;
    private DeviceProcessAdapter adapter;
    private ArrayList<DeviceProcess> processingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_processing);

        listView = findViewById(R.id.listViewProcessing);
        processingList = new ArrayList<>(MainActivity.processingDevices.values());

        adapter = new DeviceProcessAdapter(this, processingList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật danh sách khi trở lại activity
        processingList.clear();
        processingList.addAll(MainActivity.processingDevices.values());
        adapter.notifyDataSetChanged();
    }
}
