package com.example.qrcodenew;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ProcessingListActivity extends AppCompatActivity {
    private ListView listViewPending;
    private ArrayList<DeviceProcessing> processingDevices;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_processing_list);
//
//        listViewPending = findViewById(R.id.listViewPending);
//        processingDevices = (ArrayList<DeviceProcessing>) getIntent().getSerializableExtra("processingDevices");
//
//        ArrayAdapter<DeviceProcessing> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, processingDevices);
//        listViewPending.setAdapter(adapter);
//    }
}
