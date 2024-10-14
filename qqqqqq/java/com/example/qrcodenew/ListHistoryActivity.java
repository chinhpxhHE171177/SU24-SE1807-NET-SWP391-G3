package com.example.qrcodenew;

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

        // Set up RecyclerView
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



//package com.example.qrcodenew;
//
//import android.os.Bundle;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class ListHistoryActivity extends AppCompatActivity {
//
//    private DatabaseHelper databaseHelper;
//    private ListView listView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_history);
//
//        listView = findViewById(R.id.listView);
//        databaseHelper = new DatabaseHelper(this);
//
//        // Load the data and set it to the ListView
//        loadHistoryData();
//    }
//
//    private void loadHistoryData() {
//        ArrayList<Device_History> deviceList = databaseHelper.getHistoryData();
//
//        // Kiểm tra danh sách Device_History
//        if (deviceList.isEmpty()) {
//            Toast.makeText(this, "No history records found", Toast.LENGTH_SHORT).show();
//            return; // Dừng phương thức nếu không có dữ liệu
//        }
//
//        ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
//
//        for (Device_History device : deviceList) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("idCode", device.getIdCode());
//            map.put("code", device.getCode());
//            map.put("name", device.getName());
//            map.put("issue", device.getIssue());
//            map.put("startTime", device.getStartTime());
//            map.put("endTime", device.getEndTime());
//            map.put("totalTime", device.getTotalTime());
//
//            // Lấy số lỗi cho mỗi thiết bị
//            int errorCount = databaseHelper.getErrorCount(device.getIdCode());
//            map.put("errorCount", String.valueOf(errorCount));
//
//            listItems.add(map);
//        }
//
//        SimpleAdapter adapter = new SimpleAdapter(
//                this,
//                listItems,
//                R.layout.row_history,
//                new String[]{"code", "name", "issue", "startTime", "endTime", "totalTime", "errorCount"},
//                new int[]{R.id.txt_Code, R.id.txt_Name, R.id.txt_Issue, R.id.txt_Start_Time, R.id.txt_End_Time, R.id.txt_Duration, R.id.txt_Error_Count}
//        );
//
//        listView.setAdapter(adapter);
//    }
//}



//    private void loadHistoryData() {
//        ArrayList<Device> deviceList = databaseHelper.getAllData();
//        ArrayList<HashMap<String, String>> listItems = new ArrayList<>();
//
//        for (Device device : deviceList) {
//            HashMap<String, String> map = new HashMap<>();
//            map.put("idCode", device.getIdCode());
//            map.put("code", device.getCode());
//            map.put("name", device.getName());
//            map.put("issue", device.getIssue());
//            map.put("startTime", device.getStartTime());
//            map.put("endTime", device.getEndTime());
//            map.put("totalTime", device.getTotalTime());
//            listItems.add(map);
//        }
//
//        SimpleAdapter adapter = new SimpleAdapter(
//                this,
//                listItems,
//                R.layout.row_history,
//                new String[]{"code", "name", "issue", "startTime", "endTime", "totalTime"},
//                new int[]{R.id.txt_Code, R.id.txt_Name, R.id.txt_Issue, R.id.txt_Start_Time, R.id.txt_End_Time, R.id.txt_Duration}
//        );
//
//        listView.setAdapter(adapter);
//    }