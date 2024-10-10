package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecordListActivity extends AppCompatActivity {

    private ListView listView;
    private RecordAdapter adapter;
    private ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        listView = findViewById(R.id.listView);
        DatabaseHelper db = new DatabaseHelper(this);
        records = db.getAllRecords();
        adapter = new RecordAdapter(this, records);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
            intent.putExtra("recordId", records.get(position).getId());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            db.deleteRecord(records.get(position).getId());
            records.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã xóa bản ghi", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}
