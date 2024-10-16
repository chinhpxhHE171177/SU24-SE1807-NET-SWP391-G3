package com.example.qrcode;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private ArrayList<Device_History> deviceList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_history);

        recyclerView = findViewById(R.id.listView);
        deviceList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(this, deviceList);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        deviceList.clear();
        deviceList.addAll(databaseHelper.getHistoryData());
        adapter.notifyDataSetChanged();
        if (deviceList.isEmpty()) {
            Toast.makeText(this, "No history records found", Toast.LENGTH_SHORT).show();
        }
    }
}

